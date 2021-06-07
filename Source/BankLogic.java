package source;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * The main banking system, responsible for handling the accounts
 */
public class BankLogic implements java.io.Serializable{
	/**
	 * List of registered customers
	 */
	public ArrayList<Customer> Customers = new ArrayList<Customer>();//new ArrayList<Customer>();
	//------------------------------------------------------------------------------
	//Instantiator functions
	//------------------------------------------------------------------------------
	/**
	 * Creates a new customer if no other user with that social security number exists
	 * @param name The users first name
	 * @param surname The users last name
	 * @param pNo the users social security number
	 * @return true if success false if not
	 */
	public boolean createCustomer(String name, String surname, String pNo) {
		if(this.assertUnique(pNo)) {
			this.Customers.add(new Customer(name,surname,pNo));
			return true;
		}
		else return false;
	}
	/**
	 * Creates a new savings account for the given user
	 * @param pNo the social security number of the user
	 * @return-1 if not successful else the index of the account
	 */
	public int createSavingsAccount(String pNo) {
		int index = this.findIndex(pNo);
		return index !=-1 ?this.Customers.get(index).addAccount(0):-1;
	}
	
	
	/**
	 * Creates a new credit account for the given user
	 * @param pNo the social security number of the user
	 * @return-1 if not successful else the index of the account
	 */
	public int createCreditAccount(String pNo) {
		int index = this.findIndex(pNo);
		return index !=-1 ?this.Customers.get(index).addAccount(1):-1;
	}
	
	
	//------------------------------------------------------------------------------
	//Modifier functions
	//------------------------------------------------------------------------------
	//------------------------------------------------------------------------------
	//		Balance modifier functions
	//------------------------------------------------------------------------------
	/**
	 * Adds a value to the account balance if the value is valid
	 * @param pNo The social security number of the user
	 * @param accountId The account number for the account that is to be modified
	 * @param amount The amount of money that is to be added to the balance
	 * @return false if the user does not exist or the value is invalid else returns true
	 */
	public boolean deposit(String pNo, int accountId, double amount) {

		int index = this.findIndex(pNo);
		if(index == -1)
			return false;
		return this.Customers.get(index).deposit(accountId, amount);
	}
	/**
	 * Changes the users name
	 * @param name The users new first name
	 * @param surname The users new last name
	 * @param pNo The users social security number
	 * @return true if the user exists else returns false
	 */
	public boolean changeCustomerName(String name, String surname, String pNo) {
		int index = this.findIndex(pNo);
		if(index == -1)
			return false;
		Customers.get(index).changeName(name, surname);
		return true;
	}
	/**
	 * Withdraws a certain amount of money from the given account if the balance suffices
	 * @param pNo The users social security number
	 * @param accountId The account number of the account that is to be withdrawn from
	 * @param amount The amount of money that is to be withdrawn
	 * @return True if the money sufficed and the user exists else false
	 */
	public boolean withdraw(String pNo, int accountId, double amount) {
		int index = this.findIndex(pNo);
		if(index == -1)
			return false;
		return this.Customers.get(index).withdraw(accountId, amount);
	}
	//------------------------------------------------------------------------------
	//		User modifier functions
	//------------------------------------------------------------------------------
	/**
	 * Closes a specific account that is held by a specific user
	 * @param pNo The social security number for the user
	 * @param accountId The account number for the account that is to be deleted
	 * @return null that user does not exist else the string representation of the account that was removed
	 */
	public String closeAccount(String pNo, int accountId)
	{
		int index = this.findIndex(pNo);
		if(index == -1)
			return null;
		return this.Customers.get(index).deleteAccount(accountId);
	}
	/**
	 * Deletes a user and all of it's accounts
	 * @param pNo The social security number for the user that is to be deleted
	 * @return null if the user does not exist else a string representation of the user
	 */
	public ArrayList<String> deleteCustomer(String pNo){
		int index = this.findIndex(pNo);
		if(index == -1)
			return null;
		ArrayList<String> ret = new ArrayList<String>();
		ret.add(this.Customers.get(index).toString());
		ret.addAll(this.Customers.get(index).deleteAllAccounts());
		this.Customers.remove(index);
		return  ret; 
	}
	//------------------------------------------------------------------------------
	//Fetch functions
	//------------------------------------------------------------------------------
	//------------------------------------------------------------------------------
	//		Account get function
	//------------------------------------------------------------------------------
	/**
	 * Gets a string representation of a user specific account 
	 * @param pNo The social security number for the user
	 * @param accountId the account Id that we want to get the representation of
	 * @return "" if the account or the user does not exist else the string representation of the account
	 */
	public String getAccount(String pNo, int accountId) {
		int index = this.findIndex(pNo);
		return index !=-1 ?this.Customers.get(index).getAccount(accountId):"";
	}
	/**
	 * Gets the users transactions
	 * @param pNr Social security nmbr for the account holder
	 * @param accountId The account id
	 * @return A list of transactions for the accounts transactions 
	 */
	public ObservableList<Transaction> getTransactions(String pNr, int accountId){

		int index = this.findIndex(pNr);
		if(index==-1)
			return null;
		return this.Customers.get(index).getAccountClass(accountId)!=null? this.Customers.get(index).getAccountClass(accountId).getTansactionList():null;
		
	}
	/**
	 * Gets a list of the registered users
	 * @return A list of customers
	 */
	public ObservableList<Customer> getCustomers(){
		return  FXCollections.observableArrayList(this.Customers);
	}
	
	/**
	 * Gets a list of accounts for the given user
	 */
	public ObservableList<Account> getAccounts(String pNr){
		int index = this.findIndex(pNr);
		return index !=-1 ?this.Customers.get(index).getAccountList():null;
		
	}
	
	//------------------------------------------------------------------------------
	//		Customer get functions
	//------------------------------------------------------------------------------	
	/**
	 * Get function for customer, represents the user and it's accounts as an arraylist of strings
	 * @param pNo The users social security number
	 * @return null if the user does not exist else an arraylist consisting of the string 
	 * representation of the user and it's accounts
	 */
	public ArrayList<String> getCustomer(String pNo){
		ArrayList<String> ret =  new ArrayList<String>();
		int index = this.findIndex(pNo);
		if(index ==-1)
			return null;
		Customer c = Customers.get(index);
		ret.add(c.toString());
		for(Account a : c.getAccountList())
			ret.add(a.toString());
		return ret;
	}
	
	
	/** get all of a users accounts*/
	
	public ArrayList<Account> getCustomerAccounts(String pNo){
		ArrayList<Account> ret =  new ArrayList<Account>();
		int index = this.findIndex(pNo);
		if(index ==-1)
			return null;
		Customer c = Customers.get(index);
		for(Account a : c.getAccountList())
			ret.add(a);
		return ret;
	}
	/**
	 * returns an ArrayList with the string representation of every registered user
	 */
	public ArrayList<String> getAllCustomers(){
		ArrayList<String> ret = new ArrayList<String>();
		for( Customer c : this.Customers)
			ret.add(c.toString());
		return ret;
	}
	/** returns an arraylist with containing all the customers*/
	public ArrayList<Customer> getAllCustomersList(){
		ArrayList<Customer> ret = new ArrayList<Customer>();
		for( Customer c : this.Customers)
			ret.add(c);
		return ret;
	}
	
	
	
	//------------------------------------------------------------------------------
	//Quality of life improvements
	//------------------------------------------------------------------------------
	/**
	 * Returns the index of a specific user\n
	 * just to save rows
	 * @param SocialSecurity The users social security number
	 * @return -1 if that user does not exist else the index of the user in the list Customers
	 */
	public int findIndex(String SocialSecurity) {
		int i = 0;
		for(Customer c : this.Customers) {
			if( c.getSocialSecurity().equals(SocialSecurity))
				return i;
			i++;
		}
		return -1;
	}
	/**
	 * Asserts that no user exists with that social security number
	 * @param SocialSecurity The number we want to look up
	 * @return true if no such user exists false if that social security number exists
	 */
	private boolean assertUnique(String SocialSecurity) { return this.findIndex(SocialSecurity)==-1; }
}
