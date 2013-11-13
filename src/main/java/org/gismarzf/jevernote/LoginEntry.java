package org.gismarzf.jevernote;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginEntry extends Application {

	private Stage stage;
	private BorderPane rootPane;

	@Override
	public void start(Stage loginStage) {
		this.stage = loginStage;
		this.stage.setTitle("NoteList Overview");

		try {

			FXMLLoader loader =
					new FXMLLoader(
							LoginEntry.class
									.getResource("view/NoteListOverview.fxml"));

			rootPane = (BorderPane) loader.load();
			Scene scene = new Scene(rootPane);

			loginStage.setScene(scene);
			loginStage.setResizable(false);
			loginStage.show();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}

	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}