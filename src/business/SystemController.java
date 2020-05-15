package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ui.MainWindow;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
		if(!MainWindow.INSTANCE.isInitialized()) {
			MainWindow.INSTANCE.init();
		}		
		MainWindow.INSTANCE.show();
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	
	
	
	@Override
	public void addMember(LibraryMember member){    
		DataAccess da = new DataAccessFacade();       
        if (da.isMemberDuplicated(member)) {
        	Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Duplicated ID");
			alert.setContentText("The member ID is already being used. Please specify a different ID");
			alert.showAndWait();
        }  
        else {         
        	da.saveMember(member);
        }
		
	}
	@Override
	public LibraryMember searchMember(String memberID) {
		DataAccess da = new DataAccessFacade();		
		HashMap<String, LibraryMember> h = da.readMemberMap();
		if(h.containsKey(memberID)) return h.get(memberID);	
		return null;
	}
	
	@Override
	public Book searchBook(String ISBN) {
		DataAccess da = new DataAccessFacade();		
		HashMap<String, Book> h = da.readBooksMap();
		if(h.containsKey(ISBN)) return h.get(ISBN);	
		return null;
	}
	

	
	@Override
	public void editMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();       
        if (da.isMemberDuplicated(member)) {
        	HashMap<String, LibraryMember> h = da.readMemberMap();
        	h.put(member.getMemberId(), member);
        	List<LibraryMember> l = new ArrayList<>();
        	l.addAll(h.values());
        	da.EditMemberMap(l);
        	System.out.println("Members were stored");
        }  
        else {         
        	System.out.println("Member not found in HashMap");
        }
		
	}
	@Override
	public void makeCheckout(LibraryMember member, Book book) {
		 BookCopy copy = book.getNextAvailableCopy(); 
		 LocalDate today = LocalDate.now();
		 LocalDate due = today.plusDays(book.getMaxCheckoutLength());
		 CheckoutEntry ce = new CheckoutEntry(copy,today,due);	
		 if(!member.getCheckoutRecord().getEntryList().contains(ce)) {
			 member.getCheckoutRecord().addCheckoutEntry(ce); 		 
			 DataAccessFacade data = new DataAccessFacade();         
			 data.updateAvailabilityOfBook(book.getIsbn(),copy.getCopyNum(), false);
			 for(CheckoutEntry entry: member.getCheckoutRecord().getEntryList()) {
				if(book.getIsbn() == entry.getBookcopy().getBook().getIsbn() 
						&& copy.getCopyNum() == entry.getBookcopy().getCopyNum()) 
					entry.getBookcopy().setAvailable(false);
			}				 
			 
			 data.saveMember(member);	
			 data.saveEntry(member);
		 }
	}
	@Override
	public BookCopy searchBookCopy(String ISBN) {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//	public BookCopy searchBookCopy(String ISBN) {
//		DataAccess da = new DataAccessFacade(); 
//		HashMap<Integer, BookCopy> h = da.readBookCopyMap();
//		for(BookCopy bc: h.values()) {
//			if(bc.getBook().getIsbn().equals(ISBN)) return bc;	
//		}
//		return null;
//	}
	@Override
	public List<Book> allBooks() {
		DataAccess da = new DataAccessFacade();
		List<Book> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().values());
		return retval;
	}
	@Override
	public List<LibraryMember> allMembers() {
		DataAccess da = new DataAccessFacade();
		List<LibraryMember> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().values());
		return retval;
	}
	
		
	
}
