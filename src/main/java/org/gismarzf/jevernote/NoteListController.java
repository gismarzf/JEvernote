package org.gismarzf.jevernote;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.gismarzf.jevernote.model.Note;

public class NoteListController {

	private ObservableList<Note> noteList;

	@FXML
	private TableView<Note> noteListView;
	@FXML
	private TableColumn<Note, String> titleColumn;
	@FXML
	private TableColumn<Note, String> tagsColumn;
	@FXML
	private TableColumn<Note, String> updatedColumn;
	@FXML
	private TableColumn<Note, String> createdColumn;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Button setFilterButton;
	@FXML
	private Label statusMsg;

	private MainStage mainApp;

	@FXML
	private void initialize() {

		titleColumn
			.setCellValueFactory(new PropertyValueFactory<Note, String>(
				"title"));
		tagsColumn
			.setCellValueFactory(new PropertyValueFactory<Note, String>(
				"tags"));
		updatedColumn
			.setCellValueFactory(new PropertyValueFactory<Note, String>(
				"updated"));
		createdColumn
			.setCellValueFactory(new PropertyValueFactory<Note, String>(
				"created"));

		loadNotes();

	}

	private void loadNotes() {

		progressIndicator.setVisible(true);
		progressIndicator.setProgress(-1);
		statusMsg.setText("Loading notes...");

		// login, etc.
		final Evernote en = new Evernote();
		try {
			en.setUp();
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
					setNoteList(en.listNotes());
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
		tr.start();

	}

	public void setNoteList(List<Note> notes) {
		noteList = FXCollections.observableArrayList(notes);
		noteListView.setItems(noteList);
	}

	public void setMainApp(MainStage mainApp) {
		this.mainApp = mainApp;
	}

	public MainStage getMainApp() {
		return mainApp;
	}

}
