package org.gismarzf.jevernote;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		StageWrapper noteListOverviewStage =
			new StageWrapper("view/NoteListOverview.fxml", "JEvernote");
		stage = noteListOverviewStage.getStage();
		stage.show();

	}
}

// FIXME massive refactoring