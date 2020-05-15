package ui;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Start extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	private static Stage primStage = null;
	public static Stage primStage() {
		return primStage;
	}
		
	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}
	
	private static Stage[] allWindows = { 
		LoginWindow.INSTANCE,
		AddMemberWindow.INSTANCE,
		EditMemberInfoWindow.INSTANCE,
		AddBookCopyWindow.INSTANCE,
		AddBookWindow.INSTANCE,
		AddAuthorWindow.INSTANCE,
		CheckoutBookWindow.INSTANCE,
		AddAuthorWindow.INSTANCE,
		PrinterWindow.INSTANCE,
		OverdueWindow.INSTANCE
	};
	
	public static void hideAllWindows() {
		primStage.hide();
		for(Stage st: allWindows) {
			st.hide();
		}
	}
	

	@Override
	public void start(Stage primaryStage) {
		primStage = primaryStage;
		
		if(!LoginWindow.INSTANCE.isInitialized()) {
			LoginWindow.INSTANCE.init();
		}
		LoginWindow.INSTANCE.clear();
		LoginWindow.INSTANCE.show();								

	}
	
}
