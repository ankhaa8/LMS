package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;


public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public boolean isMemberDuplicated(LibraryMember member);
	public void saveMember(LibraryMember member); 
	public HashMap<String, Author> readAuthorMap();
	public void EditMemberMap(List<LibraryMember> memberList);
	public void updateAvailabilityOfBook(String isbn, int copyNum, boolean availability);
	public CheckoutRecord checkoutRecords(String memberID);
	public void saveAuthor(Author author) ;
	
	
}
