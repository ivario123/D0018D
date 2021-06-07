/**
 * 
 */
package source;

import java.util.Locale;

/**
 * The CreditAccount class is a subclass of account, meant to represent an account that can borrow money from the bank
 * for a fee
 */
public class CreditAccount extends Account implements java.io.Serializable {
	//------------------------------------------------------------------------------
	// Variable allocations
	//------------------------------------------------------------------------------
	/** The lower bound of balance local to the Credit Account*/
	private double creditLimit = -5000;
	/** The balance limit where the balance is considered debt */
	private double creditBound = 0;
	/** Interest if the balance is larger than the creditBound*/
	private double savingsIntrest = 0.005;
	/** The interest if the balance is smaller than the creditBound*/
	private double debtIntrest = 0.07;

	//------------------------------------------------------------------------------
	// Access layer
	//------------------------------------------------------------------------------
	/** Formats a string to the proper format for display after deletion
	 * @param delete used to overload the normal toString function
	 * @return  the string representation of the account*/
	@Override
	public String toString(boolean delete) {
		return String.format(Locale.US,"%d %.2f kr %s %.2f kr",this.accountNumber,this.accountBalance, this.accountTypes.get(1),
				this.accountBalance*(this.accountBalance>=this.creditBound?this.savingsIntrest:this.debtIntrest));
	}
	//Override, checks that the balance will not be lower than the creditLimit after the transaction
	@Override
	protected double actualWithdraw(double amount) {
		return (amount > 0 && this.creditLimit<=this.accountBalance-amount)?amount:0;
	}

	//------------------------------------------------------------------------------
	// Constructor and representation function
	//------------------------------------------------------------------------------

	/**  Credit Account constructor calls the account constructor */
	public CreditAccount() {
		super(1);
		this.accountIntrest=(float)this.savingsIntrest*100;
	}
}
