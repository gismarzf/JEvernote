package org.gismarzf.jevernote;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class NoteListStage {

	private MainStage mainApp;
	private NoteListController controller;

	public NoteListStage(MainStage mainApp) {
		this.mainApp = mainApp;
	}

	public void loadStage() {

		FXMLLoader loader =
			new FXMLLoader(this.getClass().getResource(
				"view/NoteListOverview.fxml"));

		BorderPane borderPane = null;

		try {
			borderPane = (BorderPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		controller = loader.getController();
		controller.setMainApp(mainApp);

		Scene scene = new Scene(borderPane);
		mainApp.getStage().setScene(scene);
		mainApp.getStage().centerOnScreen();
		mainApp.getStage().setTitle("notes overview");

		mainApp.getStage().show();
	}

	public NoteListController getController() {
		return controller;
	}

}
