package org.gismarzf.jevernote;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainStage extends Application {

	private Stage stage;
	private LoginStage loginStage;
	private NoteListStage noteListStage;

	@Override
	public void start(Stage stage) {

		// necessary for javafx magic
		this.stage = stage;

		// initial screen
		LoginStage loginStage = new LoginStage(this);
		loginStage.loadStage();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public LoginStage getLoginStage() {
		return loginStage;
	}

	public void setLoginStage(LoginStage loginStage) {
		this.loginStage = loginStage;
	}

	public NoteListStage getNoteListStage() {
		return noteListStage;
	}

	public void setNoteListStage(NoteListStage noteListStage) {
		this.noteListStage = noteListStage;
	}
}