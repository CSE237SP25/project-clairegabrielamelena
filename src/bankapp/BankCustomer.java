package bankapp;

import java.util.ArrayList;

public class BankCustomer {

	String username;
	ArrayList<BankAccount> accountList;
	
	
	public BankCustomer(String username) {
		this.username = username;
		accountList = new ArrayList<BankAccount>();
	}
	
	//Gabriela
	public BankAccount openAccount(String accountName) {
		BankAccount account = new BankAccount(accountName);
		accountList.add(account);
		return account;
	}
	
	public void transferMoney(BankAccount sourceAccount, BankAccount destinationAccount, double amount) {
	    if (sourceAccount == destinationAccount) {
	        throw new IllegalArgumentException("Source and destination accounts must be different.");
	    }
		try {
			sourceAccount.withdraw(amount); 
			destinationAccount.deposit(amount);
		}
		catch (IllegalArgumentException e) {
		    // Handle the exception
		    System.out.println("Insufficent funds. Unable to complete transfer.");
		}
		
	
	}
	
	public ArrayList<BankAccount> getAccountList(){
		return accountList;
	}
	
	public String getUsername() {
		return username;
	}
	
	//Melena
	public String renameAccount(String newAccountName) {
		this.username = newAccountName; 
		return this.getUsername(); 
	}
}