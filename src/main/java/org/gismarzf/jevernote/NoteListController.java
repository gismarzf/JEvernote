package org.gismarzf.jevernote;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

import com.evernote.edam.type.Note;

public class NoteListController implements Hookable {

	private final EvernoteAPI evernote = new EvernoteAPI();
	private ObservableList<Note> noteList;

	@FXML
	private WebView noteContentView;
	@FXML
	private TableView<Note> noteListView;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private TextField searchTextField;
	@FXML
	private Button setFilterButton;
	@FXML
	private Label statusMsg;
	@FXML
	private TableColumn<Note, String> titleColumn;
	@FXML
	private CheckMenuItem showLeftPanelCheck;

	@FXML
	public void exitApplication() {
		Platform.exit();
	}

	public void setNoteList(List<Note> notes) {
		noteList = FXCollections.observableArrayList(notes);
		noteListView.setItems(noteList);

		// make the tableview respond to changes in the note object
		titleColumn
			.setCellValueFactory(new PropertyValueFactory<Note, String>(
				"title"));
	}

	@FXML
	private void initialize() {

		// make the tableview respond to changes in the note object
		titleColumn
			.setCellValueFactory(new PropertyValueFactory<Note, String>(
				"title"));

		// on selection change, show note-html-file in webview
		noteListView.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<Note>() {

				@Override
				public void changed(
					ObservableValue<? extends Note> observable,
					Note oldValue, Note newValue) {

					// this calls with newValue=null when i make a new search
					// maybe b/c the selection points to a new non-existing row??
					if (newValue != null) {
						noteContentView.getEngine().load(
							evernote.getNoteHTMLContentPath(newValue)
								.toString());
					}
				}
			});

		// initial load notes
		loadNotes();

	}

	@FXML
	private void loadNotes() {

		try {
			// login, etc.
			evernote.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}

		final Task<List<Note>> task = new Task<List<Note>>() {
			@Override
			protected List<Note> call() throws Exception {

				updateMessage("getting note information...");
				evernote.downloadNotes(searchTextField.getText(), 3);

				updateProgress(0, 1);
				for (Note note : evernote.getNotes()) {

					int thisNote = evernote.getNotes().indexOf(note) + 1;
					int totalNotes = evernote.getNotes().size();

					updateProgress(thisNote, totalNotes);
					updateMessage("fetching notes: " + thisNote + " of "
						+ totalNotes);

					evernote.saveNotesToDisk(note);
				}

				return evernote.getNotes();
			}
		};

		// status message = task update message
		statusMsg.textProperty().bind(task.messageProperty());

		// progress = task progress
		progressIndicator.progressProperty().bind(
			task.progressProperty());

		// stateProperty for Task:
		task.stateProperty().addListener(
			new ChangeListener<Worker.State>() {

				@Override
				public void changed(
					ObservableValue<? extends State> observable,
					State oldValue, Worker.State newState) {
					if (newState == Worker.State.SUCCEEDED) {
						setNoteList(task.getValue());
					}
				}
			});

		new Thread(task).start();

	}

	@FXML
	public void showLeftPanelClick() {
		if (showLeftPanelCheck.isSelected()) {
			noteListView.setVisible(true);
			noteListView.setManaged(true);
		} else {
			noteListView.setVisible(false);
			noteListView.setManaged(false);
		}
	}

	@Override
	public void setHook(Object o) {
		// TODO Auto-generated method stub

	}

}
