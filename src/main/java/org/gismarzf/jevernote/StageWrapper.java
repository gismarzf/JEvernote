package org.gismarzf.jevernote;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StageWrapper {

	private FXMLLoader loader;
	private Pane pane;
	private Stage stage;

	public StageWrapper(String fxmlURI, String title) {

		this.loader =
			new FXMLLoader(Main.class.getResource(fxmlURI));

		try {
			this.pane = (Pane) loader.load();
		} catch (IOException e) {
			System.out.println("Couldn't load/initialize: " + fxmlURI);
			e.printStackTrace();
		}

		this.stage = new Stage();
		this.stage.setScene(new Scene(pane));
		this.stage.setTitle(title);

		// so that the controller knows which stage it's from
		((Hookable) loader.getController()).setHook(stage);

	}

	public Stage getStage() {
		return stage;
	}
}
