package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord  implements Serializable {
    private static final long serialVersionUID = -6253689321298277755L;
    
    private List<CheckoutEntry> checkoutEntry = new ArrayList<>();
    
    public List<CheckoutEntry> getEntryList() {
        return checkoutEntry;
    }

    public void setEntryList(List<CheckoutEntry> entryList) {
        this.checkoutEntry = entryList;
    }
    
   
    public void addCheckoutEntry(BookCopy bk, LocalDate checkoutD, LocalDate dueD) {
    	CheckoutEntry entry = new CheckoutEntry(bk, checkoutD, dueD);
    	checkoutEntry.add(entry);
    }
    
    public void addCheckoutEntry(CheckoutEntry entry) {
    	checkoutEntry.add(entry);
    }
    
    @Override
	public String toString() {
		return "CheckoutRecord: " + this.getEntryList().toString();
	}
}
