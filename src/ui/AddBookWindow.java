package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddBookWindow extends Stage implements LibWindow {
	public static final AddBookWindow INSTANCE = new AddBookWindow();
	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@FXML
	private TextField txtTitle;
	@FXML
	private TextField txtIsbn;
	@FXML
	private TextField txtChkDays;
	@FXML
	private TextField txtCopies;
	@FXML
	private Label listAuthors;
	@FXML
	private Button btnPlus;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnShow;

	List<Author> authorList = new ArrayList<>();
	DataAccessFacade data = new DataAccessFacade();
	ControllerInterface controller = new SystemController();

	public void init() {
		try {

			data.clearAuthorFile();
			FXMLLoader fxmloader = new FXMLLoader();
			fxmloader.setLocation(getClass().getResource("/ui/AddBookWindow.fxml"));
			Scene scene = new Scene(fxmloader.load());
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());

			AddBookWindow.INSTANCE.setScene(scene);
			AddBookWindow.INSTANCE.setTitle("Add book");
			AddBookWindow.INSTANCE.setResizable(false);
			AddBookWindow.INSTANCE.show();

		} catch (Exception ea) {
			ea.printStackTrace();
		}
	}

	public void show(ActionEvent event) {
		authorList.clear();
		DataAccessFacade data = new DataAccessFacade();
		HashMap<String, Author> h = data.readAuthorMap();
		if (!h.isEmpty() || h != null) {
			authorList.addAll(h.values());
			if (authorList != null || !authorList.isEmpty()) {
				System.out.println(authorList);
				StringBuilder sb = new StringBuilder();
				for (Author a : authorList)
					sb.append(a.getLastName() + " " + a.getFirstName() + "\n");
				listAuthors.setText(sb.toString());
			}
		}
	}

	public void save(ActionEvent event) {
		try {

			if (txtIsbn.getText().isEmpty() || txtTitle.getText().isEmpty() || txtChkDays.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setContentText("fill all fields");
				alert.showAndWait();
			} else {
				try {

					if (Pattern.matches("[0-9]+", txtChkDays.getText())
							&& Pattern.matches("[0-9]+", txtCopies.getText())) {
						int days = Integer.parseInt(txtChkDays.getText());
						int copies = Integer.parseInt(txtCopies.getText());

						if (days > 0 && copies > 0) {
							if (controller.searchBook(txtIsbn.getText()) != null) {
								AddBookWindow.INSTANCE.hide();
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Warning");
								alert.setContentText("Book with this ISBN is already in the collection");
								alert.showAndWait();
							} else {
								if (authorList.isEmpty()) {
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setTitle("Information");
									alert.setContentText("Please add authors");
									alert.showAndWait();
								} else {
									Book bok = new Book(txtIsbn.getText(), txtTitle.getText(), days, authorList);

									for (int i = 0; i < copies - 1; i++)
										bok.addCopy();

									data.saveBook(bok);
									AddBookWindow.INSTANCE.hide();
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setTitle("Information");
									alert.setContentText("Book added");
									System.out.println(bok);
									alert.showAndWait();
								}
							}
						}
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Warning");
						alert.setContentText("Please enter numbers in fields of Max checkout days and # Copies");
						alert.showAndWait();
					}
				} catch (NumberFormatException ea) {
					ea.printStackTrace();
				}
			}
		} catch (Exception ea) {
			ea.printStackTrace();
		}
	}

	public void back(ActionEvent event) {
		txtIsbn.setText("");
		txtTitle.setText("");
		txtChkDays.setText("");
		listAuthors.setText("");
		txtCopies.setText("");
		authorList.clear();

	}

	public void plus(ActionEvent event) {
		AddAuthorWindow.INSTANCE.init();
	}
}
