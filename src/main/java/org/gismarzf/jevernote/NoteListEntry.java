package org.gismarzf.jevernote;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NoteListEntry extends Application {

	private Stage loginStage;
	private AnchorPane loginLayout;

	@Override
	public void start(Stage loginStage) {
		this.loginStage = loginStage;
		this.loginStage.setTitle("Login");

		try {

			// Load the root layout from the fxml file
			FXMLLoader loader =
					new FXMLLoader(
							NoteListEntry.class
									.getResource("view/Login.fxml"));
			loginLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(loginLayout);

			loginStage.setScene(scene);
			loginStage.setResizable(false);

			loginStage.show();

			FXMLLoader loader2 =
					new FXMLLoader(
							NoteListEntry.class
									.getResource("view/NoteListOverview.fxml"));

			BorderPane noteListLayout = (BorderPane) loader2.load();
			Scene scene2 = new Scene(noteListLayout);
			loginStage.setScene(scene2);

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
		return loginStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}