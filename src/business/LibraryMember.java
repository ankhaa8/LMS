package business;

import java.io.Serializable;


final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;	
		checkoutRecord = new CheckoutRecord();
	}
	
	
	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}
	
	public void setCheckoutRecord(CheckoutRecord cr) {
		checkoutRecord = cr;
	}
	
	public String getMemberId() {
		return memberId;
	}
	
	public void setMemberId(String ID) {
		 memberId = ID;
	}
	
	public void clear() {
		this.setAddress(null);
		this.setMemberId("");
		this.setFirstName("");
		this.setLastName("");
		this.setTelephone("");
	}
	@Override
	public String toString() {
		return "\nMember Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress()+ "\n"+this.checkoutRecord.toString();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
