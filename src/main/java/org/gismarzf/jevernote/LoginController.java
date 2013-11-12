package org.gismarzf.jevernote;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginController {

	@FXML
	private ImageView jEvernoteLogo;
	@FXML
	private TextField loginUserName;
	@FXML
	private PasswordField loginPassword;
	@FXML
	private Button loginButton;

	// Reference to the main application
	private Main mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public LoginController() {
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
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public Main getMainApp() {
		return mainApp;
	}

	@FXML
	private void testButton() {
		loginUserName.setText("Hello, world.");
	}

}
