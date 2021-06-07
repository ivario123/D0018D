package source;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The class that contains data for the account variable type, this includes
 * balance and so on
 *
 */
public abstract class Account implements java.io.Serializable {

	//------------------------------------------------------------------------------
	// Variable allocations
	//------------------------------------------------------------------------------
	/** Inter account shared account number ticker*/
	private static int latestNewAccountNumber = 1000;
	
	/** The account identifier*/
	protected int accountNumber = 0;
	
	/** The type of account, 0 represents savings*/
	protected int accountType = 0;
	
	/** The list of String representation for account types*/
	protected ArrayList<String> accountTypes = new ArrayList<String>();
	
	
	/** The list of transactions*/
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	/** The current balance of the account*/
	protected double accountBalance = 0;

	/** The interest rate of the account in percent*/
	protected float accountIntrest = 1.0f;
	
	//------------------------------------------------------------------------------
	// Access layer
	//------------------------------------------------------------------------------
	/** @return The account number of the account*/
	public int getAccNum() { return this.accountNumber; }
	
	/** get the type of account*/
	public String getAccountType() { return this.accountTypes.get(this.accountType); }
	
	/** @return The current account balance*/
	public double getAccountBalance() { return this.accountBalance; }
	/** Solves the issu where the new accounts will start at 1000 after loading a state */
	public static void  incrementAccount(int increment) { latestNewAccountNumber+=increment;}
	/** Withdraws an amount from the account if the balance is sufficient and the amount is positive otherwise withdraws 0
	 * @param amount the amount to withdraw*/
	public void Withdraw(double amount) {
		double actualAmount = this.actualWithdraw(amount);
		this.accountBalance -= actualAmount;
		if(actualAmount!=0)
			this.transactions.add(new Transaction(-amount,this.accountBalance));
	}

	/** Verifies that the account balance is sufficient and that the amount is valid
	 * @return  returns the amount that is to be subtracted from accountBalance*/
	protected abstract double actualWithdraw(double amount);
	
	/** Deposits a certain amount if the amount is positive and will not generate overflow
	 * @param amount the amount to deposit*/
	public void deposit(double amount) {
		double oldBalance = this.accountBalance;
		this.accountBalance += amount>0 && overflowProt(this.accountBalance,amount) ? amount : 0;
		if(oldBalance!=this.accountBalance)
			this.transactions.add(new Transaction(this.accountBalance-oldBalance,this.accountBalance));
	}

	//------------------------------------------------------------------------------
	// Constructor and representation function
	//------------------------------------------------------------------------------
	/** @return A string representation of the account */
	public String toString(){
		return this.accountNumber+" "+
			  this.accountBalance+" kr "+
			  this.accountTypes.get(this.accountType)+" "+
			  this.accountIntrest+" %";}
			  
	/** @return Override for string representation when deleting the account*/
	public abstract String toString(boolean delete);
	
	/** Public constructor function
	 * @param AccountType specifies the account type as an index in the list accountypes*/
	public Account(int AccountType){
		this.accountTypes.add("Sparkonto");
		this.accountTypes.add("kreditkonto");
		this.accountType = accountTypes.size()>=abs(AccountType)? AccountType : 0;
		latestNewAccountNumber++;
		this.accountNumber=latestNewAccountNumber;	
	}
	
	/**
	 * Gets the list of transactions
	 */
	public ObservableList<Transaction> getTansactionList(){ return FXCollections.observableArrayList(this.transactions); }
	
	//------------------------------------------------------------------------------
	// Helper functions
	//------------------------------------------------------------------------------
	/** @param i Integer that user wants the absolute value of
	 * @return the absolute value of i*/
	protected static int abs(int i) { return i<0 ? -i : i; }
	
	/** @param i Double that user wants the absolute value of
	 * @return The absolute value of i*/
	protected static double abs(double i) { return i<0 ? -i : i; }
	
	/** Checks if adding the value b to a will cause overflow*/
	protected static boolean overflowProt(Double a, Double b) { return abs(Double.MAX_VALUE-a)>b; }
}
