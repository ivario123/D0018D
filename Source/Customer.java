package source;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * The customer class contains personal identifiers and a list of contained accounts
 *
 */
public class Customer implements java.io.Serializable{
	

	//------------------------------------------------------------------------------
	//Variable allocations
	//------------------------------------------------------------------------------

	/**Private list for user accounts accessible with get and set*/
	public ArrayList<Account> accounts = new ArrayList<Account>();
	
	/**Private variable for first name accessible with get and set*/
	private String firstName = "";
	
	/**Private variable for last name accessible with get and set*/
	private String lastName = "";
	
	/**Private variable for accessible with get and set*/
	private String socialSecurity;
	
	//Public access functions to get and set private variables
	/**@return the full name of the user, separated with \s*/
	public String getName() { return this.firstName+" "+this.lastName; }
	
	/**Changes the name of the user
	 * @param FirstName New First name
	 * @param LastName New Last name*/
	public void changeName(String FirstName,String LastName) { this.firstName = FirstName; this.lastName = LastName; }
	
	/**Public access to the Customer social security number*/
	public String getSocialSecurity() { return this.socialSecurity; }
	
	
	/**Get the number of accounts registered under the user*/
	public String getNmbrOfAccounts() { return Integer.toString(this.accounts.size());}
	
	
	/**Adds a new account to users account list
	 * @return The index of the new account used for representation in the ui*/
	public int addAccount(int type) { 
		switch(type) {
			case 0:
				this.accounts.add(new SavingsAccount());
				break;
			case 1:
				this.accounts.add(new CreditAccount());
				break;
			}
		return this.accounts.get(this.accounts.size()-1).getAccNum(); 
	}
	
	/** Public access to the Customer account list*/
	public ObservableList<Account> getAccountList() { return  FXCollections.observableArrayList( this.accounts); }
	
	/** Finds a specific account in the list of accounts
	 * @param AccountNumber the identifier of the specific account
	 * @return returns the string representation of the account if it exists else returns null*/
	public String getAccount(int AccountNumber) {
		for(Account a : this.accounts)
			if(a.getAccNum()==AccountNumber)
				return a.toString();
		return null;
	}
	
	/** Finds a specific account in the list of accounts
	 * @param AccountNumber the identifier of the specific account
	 * @return returns the string representation of the account if it exists else returns null*/
	public Account getAccountClass(int AccountNumber) {
		for(Account a : this.accounts)
			if(a.getAccNum()==AccountNumber)
				return a;
		return null;
	}
	
	//------------------------------------------------------------------------------
	//Logic
	//------------------------------------------------------------------------------
	/** Increases the balance of a specific account
	 * @param AccountNumber the identifier for the specific account
	 * @param amount the balance that is to be added
	 * @return if the account exists returns true else returns false*/
	public boolean deposit(int AccountNumber,double amount ) {
		for(Account a : this.accounts)
			if(a.getAccNum()==AccountNumber)
			{
				double oldBalance = a.getAccountBalance();
				a.deposit(amount);
				return oldBalance != a.getAccountBalance();
			}
		return false;
	}
	/**
	 * withdraws a certain amount of money from a certain account
	 * @param AccountNumber the account that the money should be removed from
	 * @param amount the amount of money that is to be removed
	 * @return if the account exists and sufficient funds are present returns true else returns false
	 */
	public boolean withdraw(int AccountNumber,double amount) {
		for(Account a : this.accounts)
			if(a.getAccNum()==AccountNumber)
			{
				double oldBalance = a.getAccountBalance();
				a.Withdraw(amount);
				return oldBalance != a.getAccountBalance();
			}
		return false;
	}
	/** Deletes a specific account
	 * @param AccountNumber the identifier of the account that is to be removed
	 * @return Null or the representation of the removed account*/
	public String deleteAccount(int AccountNumber) {
		int i = 0;
		String repr = null;
		for(Account a : this.accounts)
		{
			if(a.getAccNum()==AccountNumber)
			{
				repr = a.toString(true);
				break;
			}
			i++;
		}
		this.accounts.remove(i);
		return repr;
	}
	/** Deletes all of the customers accounts.
	 * @return A string representation of all the accounts removed*/
	public ArrayList<String> deleteAllAccounts() {
		ArrayList<String> repr = new ArrayList<String>();
		for(int i = this.accounts.size()-1;i>=0;i--) {
			repr.add(this.accounts.get(i).toString(true));
			this.accounts.remove(i);
		}
		
		return reverse(repr);
	}

	//------------------------------------------------------------------------------
	//Constructor and representation functions
	//------------------------------------------------------------------------------
	/**
	 * @return returns a string representing the customer
	 */
	public String toString() { return this.getSocialSecurity()+" "+this.getName(); }
	/**Constructor for User type
	 * @param FirstName First name of the user
	 * @param LastName Last name of the user
	 * @param SocialSecurityNumber Social security number of the user
	 */
	public Customer(String FirstName,String LastName,String SocialSecurityNumber) {
		this.firstName = FirstName;
		this.lastName = LastName;
		this.socialSecurity = SocialSecurityNumber;
	}
	//------------------------------------------------------------------------------
	// Quality of life functions
	//------------------------------------------------------------------------------
	/** Reverses an arraylist recursively 
	 * @param arr The array that is to be reversed*/
	public ArrayList<String> reverse(ArrayList<String> arr){
		if(arr.size()<=1){return arr;}
		String s = arr.remove(0);
		arr = reverse(arr);
		arr.add(s);
		return arr;
	}
}
