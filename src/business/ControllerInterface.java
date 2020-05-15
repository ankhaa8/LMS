package business;

import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<Book> allBooks();
	public List<LibraryMember> allMembers();
	public List<String> allBookIds();
	public void addMember(LibraryMember member);
	public LibraryMember searchMember(String memberID);
	public void editMember(LibraryMember member);
	public Book searchBook(String ISBN);
	public BookCopy searchBookCopy(String ISBN);
	public void makeCheckout(LibraryMember member, Book book);
}
