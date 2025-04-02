package bankapp;


import java.util.ArrayList;

public class Bank {
	private ArrayList<BankCustomer> allBankCustomers;
	
	public Bank() {
		allBankCustomers = new ArrayList<BankCustomer>();
	}
	
	public void addBankCustomer(BankCustomer customerToAdd) {
		allBankCustomers.add(customerToAdd);
	}
	
	public ArrayList<BankCustomer> getAllBankCustomers() {
		return allBankCustomers;
	}

}
