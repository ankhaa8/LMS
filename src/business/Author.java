package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private  String authorID;
	private String bio;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String credentials;
	 public String getCredentials() {
	        return credentials;
	    }

	    public void setCredentials(String credentials) {
	        this.credentials = credentials;
	    }
	
	public void setBio(String bio) {
		this.bio = bio;
	}


	public String getBio() {
		return bio;
	}
	public void setAuthorID(String id) {
		this.authorID = id;
	}


	public String getAuthorID() {
		return this.authorID;
	}

	public Author(String ID, String f, String l, String t, Address a, String bio, String c) {
		super(f, l, t, a);
		this.authorID = ID;
		this.bio = bio;
		this.credentials = c;
	}
	
	@Override
	public String toString() {
		return "(" + this.getFirstName() + ", " + this.getLastName() + ", " + this.getTelephone() + ", "+ this.getBio()+")";
		
	}
	private static final long serialVersionUID = 7508481940058530471L;

}
