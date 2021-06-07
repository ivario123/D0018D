package source;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Locale;
/**
 * The Transaction class holds the data about individual valid transactions, meant to represent an accounts transaction
 * history, but is also used to make sure the savings account transaction limit is updated
 */
public class Transaction implements java.io.Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//------------------------------------------------------------------------------
	// Variable allocations
	//------------------------------------------------------------------------------
	/** The format that dates should be displayed in */
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	/** The date time when the object was instantiated */
	public String now = Transaction.dtf.format(LocalDateTime.now());
	/** The amount that was moved */
	private double amount;
	/** The balance after the transaction */
	private double newBalance;

	//------------------------------------------------------------------------------
	// Access layer
	//------------------------------------------------------------------------------
	/** Formats a string that represents the transaction */
	public String toString() {
		return String.format(Locale.US,"%s %.2f kr Saldo: %.2f kr",this.now,this.amount,this.newBalance);
	}
	
	
	public String getAmount() {
		return String.format("%.2f kr", this.amount);
	}
	
	
	public String getDate() {
		return this.now;
	}
	
	public String getNewBalance() {
		
		return String.format("%.2f kr", this.newBalance);
	}

	//------------------------------------------------------------------------------
	// Constructor and representation function
	//------------------------------------------------------------------------------
	/** Instantiates a new transaction */
	public Transaction(double amount, double newBalance){ 
	this.amount = amount; this.newBalance = newBalance; }
}
