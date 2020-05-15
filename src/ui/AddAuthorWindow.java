package ui;

import java.util.UUID;

import business.Address;
import business.Author;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddAuthorWindow extends Stage implements LibWindow {
	public static final AddAuthorWindow INSTANCE = new AddAuthorWindow();
	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtTelephone;
	@FXML
	private TextField txtCredentials;
	@FXML
	private TextField txtBio;
	@FXML
	private TextField txtStreet;
	@FXML
	private TextField txtState;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtZip;
	@FXML
	private Button buttonAdd;
	@FXML
	private Button buttonClear;
	ControllerInterface controller = new SystemController();
	public void init() {
		try {

			FXMLLoader fxmloader = new FXMLLoader();
			fxmloader.setLocation(getClass().getResource("/ui/AddAuthorWindow.fxml"));
			Scene scene = new Scene(fxmloader.load());
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			AddAuthorWindow.INSTANCE.setScene(scene);
			AddAuthorWindow.INSTANCE.setTitle("Add Author");
			AddAuthorWindow.INSTANCE.setResizable(false);
			AddAuthorWindow.INSTANCE.show();

		} catch (Exception ea) {
			ea.printStackTrace();
		}
	}

	public void addAuthor(ActionEvent event) {

		if (txtFirstName.getText().length() == 0 || txtLastName.getText().length() == 0
				|| txtStreet.getText().length() == 0 || txtCity.getText().length() == 0
				|| txtTelephone.getText().length() == 0 || txtState.getText().length() == 0
				|| txtZip.getText().length() == 0 || txtCredentials.getText().length() == 0
				|| txtBio.getText().length() == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setContentText("fill all fields");
			alert.showAndWait();
		} else {
			Address address = new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText());
			DataAccessFacade data = new DataAccessFacade();
			Author author = new Author(UUID.randomUUID().toString(), txtFirstName.getText(), txtLastName.getText(),
					txtTelephone.getText(), address, txtBio.getText(), txtCredentials.getText());
			data.saveAuthor(author);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setContentText("Author added");
			alert.showAndWait();
			AddAuthorWindow.INSTANCE.hide();
		}

	}

	public void clear(ActionEvent event) {
		txtFirstName.setText("");
		txtLastName.setText("");
		txtStreet.setText("");
		txtCity.setText("");
		txtTelephone.setText("");
		txtState.setText("");
		txtZip.setText("");
		txtCredentials.setText("");
		txtBio.setText("");
	}

}
