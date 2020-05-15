package ui;

import business.CheckoutEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrinterWindow extends Stage implements LibWindow {
	public static final PrinterWindow INSTANCE = new PrinterWindow();

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@FXML
	TextField txtId;
	@FXML
	Button btn;

	LibraryMember member;
	DataAccessFacade data = new DataAccessFacade();
	ControllerInterface controller = new SystemController();

	@Override
	public void init() {
		try {

			FXMLLoader fxmloader = new FXMLLoader();
			fxmloader.setLocation(getClass().getResource("/ui/PrintCheckoutWindow.fxml"));
			Scene scene = new Scene(fxmloader.load());
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			AddAuthorWindow.INSTANCE.setScene(scene);
			AddAuthorWindow.INSTANCE.setTitle("Print Member Checkout record");
			AddAuthorWindow.INSTANCE.setResizable(false);
			AddAuthorWindow.INSTANCE.show();

		} catch (Exception ea) {
			ea.printStackTrace();
		}

	}

	public void print(ActionEvent event) {
		if (txtId.getText().isEmpty() || txtId.getText().length() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Empty");
			alert.setHeaderText("enter member id");
			alert.setContentText("YOu need to enter member id\n");
			alert.showAndWait();
		} else {
			member = controller.searchMember(txtId.getText());
			if (member == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Not Found");
				alert.setHeaderText("member not Found");
				alert.setContentText("member with the given id wasn't found\n");
				alert.showAndWait();
			} else {
				System.out.println("ID: " + member.getMemberId());
				System.out.println("Firstname: " + member.getFirstName());
				System.out.println("Latname: " + member.getLastName());
				System.out.println("Checkout Record:\n");
				for (CheckoutEntry entry : member.getCheckoutRecord().getEntryList()) {
					System.out.println(entry);
				}
				System.out.println();
			}
		}
	}
}