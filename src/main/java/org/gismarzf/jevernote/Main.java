package org.gismarzf.jevernote;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		// start with the login screen
		StageWrapper loginStage =
			new StageWrapper("view/Login.fxml", "Login to Evernote");
		this.stage = loginStage.getStage();
		this.stage.show();

	}
}