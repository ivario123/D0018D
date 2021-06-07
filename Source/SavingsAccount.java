package source;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * The SavingsAccount class is a subclass of account, meant to represent an account where the value of the money increments
 * by a factor at account closure
 */
public class SavingsAccount extends Account implements  java.io.Serializable {

	//------------------------------------------------------------------------------
	// Variable allocations
	//------------------------------------------------------------------------------
	/** the first withdrawal from the account this year, used to ensure that only one transaction can be performed per
	 * year*/
	private Transaction limiting = null;
	private double withdrawalInterest = 1.02f;
	// Constructors
	/** Overridden constructor for the Savings account, calls the super constructor at index 0*/
	
	
	/** Formats a string to the proper format for representing the account upon deletion */
	@Override
	public String toString(boolean delete){ return String.format(Locale.US,"%d %.2f kr %s %.2f kr",this.accountNumber,this.accountBalance,this.accountType,this.interest());}
	/** Computes the interest of the balance, used when deleting the account */
	public double interest() { return this.accountIntrest*this.getAccountBalance()/100;}

	//------------------------------------------------------------------------------
	// Access layer
	//------------------------------------------------------------------------------

	/** the function that verifies and changes the amount that is to be withdrawn according to the instructions*/
	@Override
	protected double actualWithdraw(double amount) {
		if(amount <= 0)
			return 0;
		//checks if a transaction has been performed and is stored in limiting
		if(this.limiting!=null) {
			//if the transaction occurred last year or earlier reset the limiting value and recursively call actualWithdraw
			if(this.limiting.now.toCharArray()[3]!=Integer.toString(LocalDateTime.now().getYear()).toCharArray()[3]) {
				this.limiting = null;
				return this.actualWithdraw(amount);
			}
			//compute the new cost of the transaction including the withdrawal interest
			else if(this.accountBalance>=amount*this.withdrawalInterest)
					return amount*this.withdrawalInterest;

		}
		//Checks if the balance is sufficient
		else if(amount<=this.accountBalance) {
			this.limiting = new Transaction(amount,this.accountBalance-amount);
			return amount;
		}
		return 0;
	}

	//------------------------------------------------------------------------------
	// Constructor and representation function
	//------------------------------------------------------------------------------
	/**Instatiates a new SavingsAccount by calling the account constructor*/
	public SavingsAccount() {
		super(0);
	}

}
