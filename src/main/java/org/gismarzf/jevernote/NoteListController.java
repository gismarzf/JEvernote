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
	@FXML
	private WebView noteContentView;
	private ObservableList<Note> noteList;
	@FXML
	private TableView<Note> noteListView;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private TextField searchTextField;
	@FXML
	private Button setFilterButton;
	@FXML
	private CheckMenuItem showLeftPanelCheck;
	@FXML
	private Label statusMsg;
	@FXML
	private TableColumn<Note, String> titleColumn;

	@FXML
	public void exitApplication() {
		Platform.exit();
	}

	@Override
	public void setHook(Object o) {
		// TODO hook isn't implemented here

	}

	public void setNoteList(List<Note> notes) {
		noteList = FXCollections.observableArrayList(notes);
		noteListView.setItems(noteList);
	}

	@FXML
	public void showLeftPanelClick() {
		if (showLeftPanelCheck.isSelected()) {
			noteListView.setVisible(true);
		} else {
			noteListView.setVisible(false);
		}
	}

	@FXML
	private void initialize() {

		// make the table view respond to changes in the note list
		// the property factory looks for a get"Title" method in the Note object
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

					if (newValue != null) {
						noteContentView.getEngine().load(
							evernote.getNoteHTMLContentPath(newValue)
								.toString());
					}
				}
			});

		// if the node is set to invisible, it won't require space in the gui
		// neither
		noteListView.visibleProperty().bindBidirectional(
			noteListView.managedProperty());

		// load notes on start
		loadNotes();

	}

	@FXML
	private void loadNotes() {

		evernote.setUp();

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

					evernote.saveNoteToDisk(note);
				}

				updateMessage("");
				return evernote.getNotes();
			}
		};

		// status message = task update message
		statusMsg.textProperty().bind(task.messageProperty());

		// progress = task progress
		progressIndicator.progressProperty().bind(
			task.progressProperty());

		// this adds a listener that fires when the task has finished
		// in this case the result of the task (list of notes) is given to the
		// tableview
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

}
