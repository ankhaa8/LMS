package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class CheckoutEntry implements Serializable {
    private static final long serialVersionUID = -8381203775279850403L;
    
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BookCopy bookcopy;
   
    
    public CheckoutEntry(BookCopy bk, LocalDate checkoutD, LocalDate dueD) {
        this.bookcopy = bk;
        this.checkoutDate = checkoutD;
        this.dueDate = dueD;
       
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
	
	public String getISBN() {
		return bookcopy.getBook().getIsbn();
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BookCopy getBookcopy() {
		return bookcopy;
	}

	public void setBookcopy(BookCopy bookcopy) {
		this.bookcopy = bookcopy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    public String toString() {
        return this.getBookcopy().getBook().getIsbn()+" "+ this.bookcopy.getCopyNum()+" "+ '\t' + checkoutDate + '\t' + dueDate + '\t';
    }
	
	@Override
    public int hashCode() 
    {  
        return Objects.hash(checkoutDate, dueDate, bookcopy); 
    } 
	
}
