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

	private MainStage mainApp;

	public void setMainApp(MainStage mainApp) {
		this.mainApp = mainApp;
	}

	public MainStage getMainApp() {
		return mainApp;
	}

	@FXML
	private void testButton() {
		mainApp.getStage().hide();
		mainApp
			.setNoteListStage(new NoteListStage(mainApp));
		mainApp.getNoteListStage().loadStage();
	}

}
