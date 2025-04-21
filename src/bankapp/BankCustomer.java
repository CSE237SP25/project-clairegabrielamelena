package bankapp;

import java.util.ArrayList;
import java.util.Optional;

public class BankCustomer {

	private String username;
	ArrayList<BankAccount> accountList;


	public BankCustomer(String username) {

		if (username == null || username.isBlank() || username.isEmpty()) {
			throw new IllegalArgumentException();
		}
		else {
			this.username = username;
			accountList = new ArrayList<BankAccount>();
		}
	}

	//Gabriela
	public BankAccount openAccount(String accountName) {
		System.out.print(accountName);
		Optional<BankAccount> sameName = this.accountList.stream()
				.filter(a -> a.getAccountName().equals(accountName))
				.findAny();
		if (sameName.isPresent() || accountName.isBlank() || accountName.isEmpty() || accountName == null) {
			throw new IllegalArgumentException();
		}
		else {
			BankAccount account = new BankAccount(accountName);
			accountList.add(account);
			//System.out.print(account.getAccountName());
			return account;
		}
	}

	public void transferMoney(BankAccount sourceAccount, BankAccount destinationAccount, double amount) {
		if (sourceAccount == destinationAccount) {
			throw new IllegalArgumentException("Source and destination accounts must be different.");
		}
		try {
			sourceAccount.withdraw(amount); 
			destinationAccount.deposit(amount);
			System.out.println("Transfer successfully completed. \n");
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
		Optional<BankAccount> sameName = this.accountList.stream()
				.filter(a -> a.getAccountName().equals(newAccountName))
				.findAny();
		if (sameName.isPresent()) {
			throw new IllegalArgumentException();
		}
		else { 
			this.username = newAccountName; 
			return this.getUsername(); 
		}
	}

}
