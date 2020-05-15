package dataaccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;


public class DataAccessFacade implements DataAccess {
	
	enum StorageType {
		BOOKS, MEMBERS, USERS, AUTHORS, CHECKOUTENTRY, CHECKOUTRECORD;
	}
	
	public static final String OUTPUT_DIR = System.getProperty("user.dir") 
			+ "\\src\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	
	
	
	//implement: other save operations
	public void saveMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);	
	}
	
	public void saveBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		if(books == null) books = new HashMap<String, Book>();
		books.put(book.getIsbn(), book);
		saveToStorage(StorageType.BOOKS, books);
	}
	
	public void clearAuthorFile() {
		HashMap<String, Author> authors = null;
		saveToStorage(StorageType.AUTHORS, authors);	
	}
	
	public void saveAuthor(Author author) {
		HashMap<String, Author> authors = readAuthorMap();
		if(authors == null) authors = new HashMap<String, Author>();
		authors.put(author.getAuthorID(), author);
		saveToStorage(StorageType.AUTHORS, authors);	
	}
	
	public void saveEntry(LibraryMember member) {
		saveToStorage(StorageType.CHECKOUTENTRY, member);
	}
	
	public LibraryMember readEntry() {
		return (LibraryMember) readFromStorage(StorageType.CHECKOUTENTRY);	
	}
		
	public void EditMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String,Book> readBooksMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		return (HashMap<String,Book>) readFromStorage(StorageType.BOOKS);
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		//Returns a Map with name/value pairs being
		//   memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}
	
		
	public boolean isMemberDuplicated(LibraryMember member) {
		HashMap<String, LibraryMember> h = readMemberMap();
		return h.containsKey(member.getMemberId());		
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Author> readAuthorMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, Author>)readFromStorage(StorageType.AUTHORS);
	}
	
	@Override
	public void updateAvailabilityOfBook(String isbn, int copyNum, boolean availability) {
        try {
            HashMap<String,Book> h = readBooksMap();
			List<Book> books = new ArrayList<>();
			books.addAll(h.values());
			
            for (Book b : books)
                if (b.getIsbn().equals(isbn))
                    b.getCopy(copyNum).setAvailable(availability);
            DataAccessFacade.loadBookMap(books);
            System.out.println();
            for (Book b : books) {
            	for(BookCopy bc: b.getCopies()) {
            		System.out.print(bc);
            	}
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
		
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}
		
	static void loadAuthorMap(List<Author> authorList) {
		HashMap<String, Author> authors = new HashMap<String, Author>();
		authorList.forEach(author -> authors.put(author.getAuthorID(), author));
		saveToStorage(StorageType.AUTHORS, authors);
	}
	
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}
 
	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}
	
	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}
	
	
	@Override
	public CheckoutRecord checkoutRecords(String memberID) {
		HashMap<String, LibraryMember> h = readMemberMap();
		if(h.containsKey(memberID)) return h.get(memberID).getCheckoutRecord();
		return null;
	}
	
	
	
	/*final static class Pair<S,T> implements Serializable{
		
		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override 
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}
		
		@Override 
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}*/
	
}
