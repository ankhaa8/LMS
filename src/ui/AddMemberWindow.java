package ui;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMemberWindow extends Stage {
	public static final AddMemberWindow INSTANCE = new AddMemberWindow();

	@FXML
	private TextField txtID;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtStreet;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtState;
	@FXML
	private TextField txtZip;
	@FXML
	private Button btnAddMember;
	@FXML
	private Button btnCancelAddMember;

	public void init() {
		try {
			FXMLLoader fxmloader = new FXMLLoader();
			fxmloader.setLocation(getClass().getResource("/ui/AddMember.fxml"));
			Scene scene = new Scene(fxmloader.load());
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());

			AddMemberWindow.INSTANCE.setScene(scene);
			AddMemberWindow.INSTANCE.setTitle("Add Member");
			AddMemberWindow.INSTANCE.setResizable(false);
			AddMemberWindow.INSTANCE.show();

		} catch (Exception ea) {
			ea.printStackTrace();
		}
	}

	public void addMember(ActionEvent e) {
		try {
			ControllerInterface c = new SystemController();

			if (txtCity.getText().isEmpty() || txtState.getText().isEmpty() || txtStreet.getText().isEmpty()
					|| txtZip.getText().isEmpty() || txtID.getText().isEmpty() || txtFirstName.getText().isEmpty()
					|| txtLastName.getText().isEmpty() || txtPhone.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Fill out all fields");
				alert.showAndWait();
			} else {
				Address address = new Address(txtCity.getText(), txtState.getText(), txtStreet.getText(),
						txtZip.getText());

				LibraryMember member = new LibraryMember(txtID.getText(), txtFirstName.getText(), txtLastName.getText(),
						txtPhone.getText(), address);
				c.addMember(member);
				
				DataAccess da = new DataAccessFacade();
				System.out.println(da.readMemberMap());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setContentText("Member added");
				alert.showAndWait();
				AddMemberWindow.INSTANCE.hide();
				MainWindow.INSTANCE.show();
			}

		} catch (Exception ex) {
			System.out.println("Error! " + ex.getMessage());
		}

	}

	@FXML
	public void clear(ActionEvent event) {
		System.out.println("Clearing fields");
		txtID.setText("");
		txtFirstName.setText("");
		txtLastName.setText("");
		txtPhone.setText("");
		txtStreet.setText("");
		txtCity.setText("");
		txtState.setText("");
		txtZip.setText("");
	}

}
