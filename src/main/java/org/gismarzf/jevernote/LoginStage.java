package org.gismarzf.jevernote;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LoginStage {

	private MainStage mainApp;
	private LoginController controller;

	public LoginStage(MainStage mainApp) {
		this.mainApp = mainApp;
	}

	public void loadStage() {

		mainApp.getStage().setTitle("Login to Evernote");

		FXMLLoader loader =
			new FXMLLoader(this.getClass().getResource(
				"view/Login.fxml"));

		Pane anchorPane = null;

		try {
			anchorPane = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		controller = loader.getController();
		controller.setMainApp(mainApp);

		Scene scene = new Scene(anchorPane);
		mainApp.getStage().setScene(scene);
		mainApp.getStage().setResizable(false);
		mainApp.getStage().show();

	}

	public LoginController getController() {
		return controller;
	}
}
