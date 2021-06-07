package source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
/**
 * The class that contains the functions that are used to write and read the bank logic class to and from files
 *
 */
public class IO {
	OutputStream output;
	public IO() {
	}
	/**
	 * Reads the save bank logic. I would not do this if this was a production enviorment
	 * but since we don't have any sensitive information in the bank logic this is fine.
	 * @param logic The bank logic that is to be overwritten
	 * @param file The file from which the data should be read.
	 */
	public void read(BankLogic logic,File file) throws IOException, ClassNotFoundException {

		
        ObjectInputStream objectInputStream =
            new ObjectInputStream(new FileInputStream(file));

        BankLogic LogicRead = (BankLogic) objectInputStream.readObject();
        int Offset = offset(LogicRead.getAllCustomersList());
        logic.Customers = new ArrayList<Customer>();
        logic.Customers.addAll( LogicRead.Customers);
        Account.incrementAccount(Offset);
        System.out.println(Offset);
        objectInputStream.close();
	}
	
	/** Writes banklogic serialized to a file. I would not do this if this was a production enviorment
	 * but since we don't have any sensitive information in the bank logic this is fine.
	 * @param logic The bank logic we want to write to a file
	 * @param file The file which should be written to.
	 */
	public void write(BankLogic logic,File file) throws IOException, ClassNotFoundException {
		output = new FileOutputStream(file);
		try(ObjectOutputStream objectOutputStream =
			    new ObjectOutputStream(this.output)){
			objectOutputStream.writeObject(logic);
			this.output.close();
		
		}

	}
	/**
	 * 
	 * @param customers
	 * @return
	 */
	private int offset (ArrayList <Customer> customers) {
		int largest = 0;
		for(Customer c : customers) {
			for(Account a : c.accounts)
				if(a.getAccNum()>largest)
					largest = a.getAccNum();
		}
		return largest-1000;
	}
}
