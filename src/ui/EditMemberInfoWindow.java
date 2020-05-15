package ui;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditMemberInfoWindow extends Stage implements LibWindow {
	public static final EditMemberInfoWindow INSTANCE = new EditMemberInfoWindow();
	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@FXML
	private TextField txtMemberID;
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
	private Button btnOk;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnSearch;
	@FXML
	private TextField txtSearchMemberID;
	private LibraryMember l;
	public void init() {
		try {

			FXMLLoader fxmloader = new FXMLLoader();
			fxmloader.setLocation(getClass().getResource("/ui/EditMember.fxml"));
			Scene scene = new Scene(fxmloader.load());
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			EditMemberInfoWindow.INSTANCE.setScene(scene);
			EditMemberInfoWindow.INSTANCE.setTitle("Edit Member");
			EditMemberInfoWindow.INSTANCE.setResizable(false);
			EditMemberInfoWindow.INSTANCE.show();

		} catch (Exception ea) {
			ea.printStackTrace();
		}
	}

	public void search(ActionEvent e) {
		try {
			ControllerInterface c = new SystemController();
			
			if (txtSearchMemberID.getText().length() != 0 
					|| txtSearchMemberID.getText() != null
					|| !txtSearchMemberID.getText().isEmpty()) {
				l = c.searchMember(txtSearchMemberID.getText());
				if (l != null) {
					txtMemberID.setText(l.getMemberId());
					txtFirstName.setText(l.getFirstName());
					txtLastName.setText(l.getLastName());
					txtPhone.setText(l.getTelephone());
					txtStreet.setText(l.getAddress().getStreet());
					txtCity.setText(l.getAddress().getCity());
					txtState.setText(l.getAddress().getState());
					txtZip.setText(l.getAddress().getZip());
					txtSearchMemberID.setText("");
				} 
				if(l == null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setContentText("Member didn't find");
					alert.showAndWait();
					txtMemberID.setText("");
					txtFirstName.setText("");
					txtLastName.setText("");
					txtPhone.setText("");
					txtStreet.setText("");
					txtCity.setText("");
					txtState.setText("");
					txtZip.setText("");
					l.clear();
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setContentText("insert member id to search");
				alert.showAndWait();
			}
			
		} catch (Exception ex) {
			System.out.println("Error! " + ex.getMessage());
		}
	}

	public void edit(ActionEvent e) {
		try {
			ControllerInterface c = new SystemController();
			l.setMemberId(txtMemberID.getText());
			l.setFirstName(txtFirstName.getText());
			l.setLastName(txtLastName.getText());
			l.setTelephone(txtPhone.getText());
			l.getAddress().setCity(txtCity.getText());
			l.getAddress().setState(txtState.getText());
			l.getAddress().setStreet(txtStreet.getText());
			l.getAddress().setZip(txtZip.getText());
			c.editMember(l);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setContentText("member infor edited");
			EditMemberInfoWindow.INSTANCE.hide();
			alert.showAndWait();

		} catch (Exception ex) {
			System.out.println("Error! " + ex.getMessage());
		}

	}

	public void cancel(ActionEvent e) {
		EditMemberInfoWindow.INSTANCE.hide();
	}
}
