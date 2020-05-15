package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import business.Book;
import business.CheckoutEntry;
import business.ControllerInterface;
import business.Entry;
import business.LibraryMember;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OverdueWindow extends Stage implements LibWindow {

	public static final OverdueWindow INSTANCE = new OverdueWindow();

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@FXML
	private TextField txtISBN;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnAllBook;
	@FXML
	private Button btnOverdue;
	@FXML
	private Label lblMessage;
	@FXML
	private TableView<Entry> tblOverdue;
	@FXML
	private TableColumn<Entry, LocalDate> checkoutDateCol;
	@FXML
	private TableColumn<Entry, LocalDate> dueDateCol;
	@FXML
	private TableColumn<Entry, String> isbnCol;
	@FXML
	private TableColumn<Entry, Number> copyNumberCol;
	@FXML
	private TableColumn<Entry, String> memberIDCol;
	@FXML
	private TableColumn<Entry, Boolean> availableCol;
	@FXML
	private TableColumn<Entry, String> overdueCol;
	@FXML
	private Label lblISBN;

	private ControllerInterface controller = new SystemController();
	private List<LibraryMember> members = controller.allMembers();
	
	private Book book;
	private List<Entry> entryList = new ArrayList<>();

	@Override
	public void init() {
		try {

			FXMLLoader fxmloader = new FXMLLoader();
			fxmloader.setLocation(getClass().getResource("/ui/OverdueWindow.fxml"));
			Scene scene = new Scene(fxmloader.load());
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			OverdueWindow.INSTANCE.setScene(scene);
			OverdueWindow.INSTANCE.setTitle("Overdue books");
			OverdueWindow.INSTANCE.setResizable(false);
			OverdueWindow.INSTANCE.show();
		} catch (Exception ea) {
			ea.printStackTrace();
		}
	}

	public void search(ActionEvent e) {
		try {

			if (txtISBN.getText().isEmpty() || txtISBN.getText().length() == 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Empty");
				alert.setHeaderText("enter ISBN");
				alert.setContentText("You need to enter ISBN\n");
				alert.showAndWait();
			} else {
				book = controller.searchBook(txtISBN.getText());
				if (book == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("not found");
					alert.setHeaderText("not found");
					alert.setContentText("Book is not found\n");
					alert.showAndWait();
				} else {

					List<Entry> entryByISBN = new ArrayList<>();
					members = controller.allMembers();

					for (Entry entry : entryList) {
						if (entry.getISBN().equals(txtISBN.getText()))
							entryByISBN.add(entry);
					}

					checkoutDateCol.setCellValueFactory(new PropertyValueFactory<Entry, LocalDate>("checkoutDate"));
					dueDateCol.setCellValueFactory(new PropertyValueFactory<Entry, LocalDate>("dueDate"));
					isbnCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("ISBN"));
					copyNumberCol.setCellValueFactory(new PropertyValueFactory<Entry, Number>("copyNumber"));
					memberIDCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("memberId"));
					//availableCol.setCellValueFactory(new PropertyValueFactory<Entry, Boolean>("isAvailable"));
					overdueCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("overdue"));
					tblOverdue.setItems(FXCollections.observableArrayList(entryByISBN));
				}
				txtISBN.setText("");
			}

		} catch (Exception ex) {
			System.out.println("Error! " + ex.getMessage());
		}

	}

	public void showAllBook(ActionEvent e) {
		try {
			entryList.clear();
			//members = controller.allMembers();
			for (LibraryMember member : members) {
				for (CheckoutEntry entry : member.getCheckoutRecord().getEntryList()) {
					Entry entr = new Entry();
					entr.setMemberId(member.getMemberId());
					entr.setCheckoutDate(entry.getCheckoutDate());
					entr.setDueDate(entry.getDueDate());
					entr.setCopyNumber(entry.getBookcopy().getCopyNum());
					entr.setISBN(entry.getBookcopy().getBook().getIsbn());
					entr.setIsAvailable(entry.getBookcopy().getIsAvailable());
					entr.calcOverDue();
					entryList.add(entr);
					System.out.println(entr);
				}

			}

			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<Entry, LocalDate>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<Entry, LocalDate>("dueDate"));
			isbnCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("ISBN"));
			copyNumberCol.setCellValueFactory(new PropertyValueFactory<Entry, Number>("copyNumber"));
			memberIDCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("memberId"));
			availableCol.setCellValueFactory(new PropertyValueFactory<Entry, Boolean>("isAvailable"));
			overdueCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("overdue"));
			if(entryList !=null)
				tblOverdue.setItems(FXCollections.observableArrayList(entryList));
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Empty");
				alert.setHeaderText("EntryList is null");
				alert.setContentText("EntryList is null \n");
				alert.showAndWait();
			} 
				
		} catch (Exception ex) {
			System.out.println("Error! " + ex.getMessage());
		}

	}

	public void showOverdueBook(ActionEvent e) {
		try {
			List<Entry> entryOverdue = new ArrayList<>();
			
			for(Entry entry: entryList) {
				if(entry.getOverdue().equals("Overdue"))
					entryOverdue.add(entry);
			}
	

			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<Entry, LocalDate>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<Entry, LocalDate>("dueDate"));
			isbnCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("ISBN"));
			copyNumberCol.setCellValueFactory(new PropertyValueFactory<Entry, Number>("copyNumber"));
			memberIDCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("memberId"));
			availableCol.setCellValueFactory(new PropertyValueFactory<Entry, Boolean>("isAvailable"));
			overdueCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("overdue"));
			tblOverdue.setItems(FXCollections.observableArrayList(entryOverdue));
		} catch (Exception ex) {
			System.out.println("Error! " + ex.getMessage());
		}
	}

}
