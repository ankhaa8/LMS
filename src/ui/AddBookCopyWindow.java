package ui;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccessFacade;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddBookCopyWindow extends Stage implements LibWindow {
	public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	Book book;
	DataAccessFacade data = new DataAccessFacade();
	ControllerInterface controller = new SystemController();

	@FXML
	TextField txtISBN;
	@FXML
	TextField txtCopies;
	@FXML
	Label lblTitle;
	@FXML
	Label lblAuthors;
	@FXML
	Label lblAvailable;
	@FXML
	Button btnSearch;
	@FXML
	Button btnAdd;
	@FXML
	Button btnBack;

	public void init() {
		try {

			FXMLLoader fxmloader = new FXMLLoader();
			fxmloader.setLocation(getClass().getResource("/ui/AddCopyWindow.fxml"));
			Scene scene = new Scene(fxmloader.load());
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			AddBookCopyWindow.INSTANCE.setScene(scene);
			AddBookCopyWindow.INSTANCE.setTitle("Add Book Copy");
			AddBookCopyWindow.INSTANCE.setResizable(false);
			AddBookCopyWindow.INSTANCE.show();

		} catch (Exception ea) {
			ea.printStackTrace();
		}
	}

	public void searchBook(Event e) {
		if (txtISBN.getText().length() <= 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("ISBN Number empty");
			alert.setContentText("You must Enter a ISBN Number to search\n");
			alert.showAndWait();
		} else {

			Book book = controller.searchBook(txtISBN.getText());
			if (book == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Not Found");
				alert.setHeaderText("Book not Found");
				alert.setContentText("Book with the given ISBN wasn't found\n");
				alert.showAndWait();
			} else {
				lblTitle.setText(book.getTitle());
				String authors = "";
				for (Author author : book.getAuthors()) {
					authors = authors + author.getFirstName() + " " + author.getLastName() + ",";
				}
				lblAuthors.setText(authors);
				lblAvailable.setText(book.getNumCopies() + "");
				this.book = book;
			}
		}
	}

	public void addCopies(Event e) {
		if (book == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Not Found");
			alert.setHeaderText("Must Search first");
			alert.setContentText("YOu need to select a book first before adding copies\n");
			alert.showAndWait();
		} else {
			if (txtCopies.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Text Empty");
				alert.setHeaderText("# Copies field Empty");
				alert.setContentText("You must enter the number of copies to add\n");
				alert.showAndWait();
			} else {
				int n = Integer.parseInt(txtCopies.getText());
				for (int i = 0; i < n ; i++)
					book.addCopy();
				
				data.saveBook(book);

				AddBookCopyWindow.INSTANCE.hide();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setContentText("Book Copy added");
				System.out.println(book.getCopies().toString());
				alert.showAndWait();
			}
		}

	}

	public void back(Event e) {
		txtISBN.setText("");

		txtCopies.setText("");

		lblTitle.setText("");

		lblAuthors.setText("");

		lblAvailable.setText("");
	}

}
