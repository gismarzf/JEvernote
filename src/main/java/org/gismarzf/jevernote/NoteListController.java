package org.gismarzf.jevernote;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import com.evernote.edam.type.Note;

public class NoteListController implements Hookable {

	private Stage mainStage;
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
	public void exitApplication() {
		Platform.exit();
	}

	@Override
	public void setHook(Object o) {

		if (o instanceof Stage) {
			mainStage = (Stage) o;
		}

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

		progressIndicator.setVisible(false);
		statusMsg.setText("Done.");

		// Listen for selection changes, show note title in text area
		noteListView.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<Note>() {

				@Override
				public void changed(
					ObservableValue<? extends Note> observable, Note oldValue,
					Note newValue) {

					// this calls with newValue=null when i make a new search
					// maybe b/c the selection points to a new non-existing row??
					if (newValue != null) {
						noteContentView.getEngine().load(
							evernote.getNoteHTMLContentPath(newValue).toString());
					}
				}
			});

	}

	@FXML
	private void loadNotes() {

		progressIndicator.setVisible(true);
		progressIndicator.setProgress(-1);
		statusMsg.setText("Loading notes...");

		try {
			// login, etc.
			evernote.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// get notes in the background
		// use special platform thingy to remove progressbar (since this
		// otherwise couldn't be called by non-javafx app thread)
		Thread tr = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					evernote.downloadNotes(searchTextField.getText(), 10);
					evernote.saveNotesToDisk();

					setNoteList(evernote.getNotes());

				} catch (Exception e) {
					e.printStackTrace();
				}

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						progressIndicator.setProgress(0);
						progressIndicator.setVisible(false);
						statusMsg.setText("Done.");
					}
				});

			}
		});

		// start thread
		tr.start();

	}

}
