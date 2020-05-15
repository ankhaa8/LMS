package ui;


import business.SystemController;
import dataaccess.Auth;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainWindow extends Stage implements LibWindow {
	public static final MainWindow INSTANCE = new MainWindow();

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@Override
	public void init() {
		Stage primaryStage = Start.primStage();
		primaryStage.setTitle("Main Page");
		        
		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();
		Image image = new Image("ui/library.jpg", 400, 300, false, false);

		// simply displays in ImageView the image as is
		ImageView iv = new ImageView();
		iv.setImage(image);
		imageHolder.getChildren().add(iv);
		imageHolder.setAlignment(Pos.CENTER);
		HBox splashBox = new HBox();
		Label splashLabel = new Label("The Library System");
		splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
		splashBox.getChildren().add(splashLabel);
		splashBox.setAlignment(Pos.CENTER);

		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(splashBox);
		topContainer.getChildren().add(imageHolder);

		Menu menu = new Menu("Menu");

		MenuItem createMember = new MenuItem("Create Member");
		createMember.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				LoginWindow.INSTANCE.hide();
				AddMemberWindow.INSTANCE.init();				
				AddMemberWindow.INSTANCE.show();			
				
			}
		});

		MenuItem editMemberInfo = new MenuItem("Edit Member");
		editMemberInfo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!EditMemberInfoWindow.INSTANCE.isInitialized()) {
					EditMemberInfoWindow.INSTANCE.init();
				}
				EditMemberInfoWindow.INSTANCE.show();
			}
		});
		
		MenuItem addBook = new MenuItem("Add Book");
		addBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				Start.hideAllWindows();
				
				if (!AddBookWindow.INSTANCE.isInitialized()) {
					AddBookWindow.INSTANCE.init();
				}				
				AddBookWindow.INSTANCE.show();
			}
		});
		
		MenuItem addBookCopy = new MenuItem("Add BookCopy");
		addBookCopy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!AddBookCopyWindow.INSTANCE.isInitialized()) {
					AddBookCopyWindow.INSTANCE.init();
				}
				//ControllerInterface ci = new SystemController();				
				AddBookCopyWindow.INSTANCE.show();
			}
		});
		
		MenuItem checkout = new MenuItem("Checkout Book");
		checkout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!CheckoutBookWindow.INSTANCE.isInitialized()) {
					CheckoutBookWindow.INSTANCE.init();
				}
				//ControllerInterface ci = new SystemController();				
				CheckoutBookWindow.INSTANCE.show();
			}
		});
		
		MenuItem print = new MenuItem("Print");
		print.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!PrinterWindow.INSTANCE.isInitialized()) {
					PrinterWindow.INSTANCE.init();
				}				
				PrinterWindow.INSTANCE.init();				
			}
		});
		
		MenuItem overdue = new MenuItem("Overdue");
		overdue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!OverdueWindow.INSTANCE.isInitialized()) {
					OverdueWindow.INSTANCE.init();
				}				
				OverdueWindow.INSTANCE.init();				
			}
		});
		
		
		MenuItem logout = new MenuItem("Exit");
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				Platform.exit();				
			}
		});
		
			
		
		if(SystemController.currentAuth.equals(Auth.LIBRARIAN)) {
			menu.getItems().addAll(checkout, logout);
			System.out.println(SystemController.currentAuth);
			
		} else if(SystemController.currentAuth.equals(Auth.ADMIN)) {
			menu.getItems().addAll(createMember, editMemberInfo, addBook, addBookCopy, logout);
		} else if(SystemController.currentAuth.equals(Auth.BOTH)) {
			menu.getItems().addAll(createMember, editMemberInfo, addBook, addBookCopy, checkout, print,overdue, logout);
		}
		
		
		
		
		mainMenu.getMenus().addAll(menu);
		MainWindow.INSTANCE.setResizable(false);
		Scene scene = new Scene(topContainer, 420, 375);	
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);
	}

}
