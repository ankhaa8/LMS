package business;

import java.io.Serializable;
import java.time.LocalDate;

public class Entry  implements Serializable{
	private static final long serialVersionUID = -8381203775379150703L;
	private LocalDate checkoutDate;
    private LocalDate dueDate;
    private int copyNumber;
    private String ISBN;
    private Boolean isAvailable; 
    private String memberId;
    private String overdue;
    
	public String getOverdue() {
		return overdue;
	}
	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}
	
	public void calcOverDue() {
		if(!isAvailable && dueDate.compareTo(LocalDate.now())<0) this.setOverdue("Overdue");
		else this.setOverdue("not Overdue");
		
	}
	
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public int getCopyNumber() {
		return copyNumber;
	}
	public void setCopyNumber(int copyNumber) {
		this.copyNumber = copyNumber;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}    
	@Override
    public String toString() {
        return "Entry: " + ISBN+" "+ " "+copyNumber+" "+ '\t' + checkoutDate + '\t' + dueDate + '\t' + memberId +'\t' + isAvailable ;
    }
}