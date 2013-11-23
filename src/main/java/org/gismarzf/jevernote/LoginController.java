package org.gismarzf.jevernote;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController implements Hookable {

	@FXML
	private ImageView jEvernoteLogo;
	@FXML
	private Button loginButton;
	@FXML
	private PasswordField loginPassword;
	@FXML
	private TextField loginUserName;

	private Stage mainStage;

	@Override
	public void setHook(Object o) {

		if (o instanceof Stage) {
			mainStage = (Stage) o;
		}

	}

	@FXML
	private void testButton() {

		mainStage.hide();

		StageWrapper noteListOverviewStage =
			new StageWrapper(
				"view/NoteListOverview.fxml", "notes overview");
		mainStage = noteListOverviewStage.getStage();
		mainStage.show();
	}
}
