package org.gismarzf.jevernote;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class NoteListController {

	@FXML
	private TableView<String> jEvernoteLogo;

	// Reference to the main application
	private LoginEntry mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public NoteListController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(LoginEntry mainApp) {
		this.mainApp = mainApp;
	}

	public LoginEntry getMainApp() {
		return mainApp;
	}

}
