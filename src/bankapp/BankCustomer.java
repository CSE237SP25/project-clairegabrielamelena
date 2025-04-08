package bankapp;

import java.util.ArrayList;
import java.util.Optional;

public class BankCustomer {

	String username;
	ArrayList<BankAccount> accountList;
	
	
	public BankCustomer(String username) {
		this.username = username;
		accountList = new ArrayList<BankAccount>();
	}
	
	//Gabriela
	public BankAccount openAccount(String accountName) {
		Optional<BankAccount> sameName = this.accountList.stream()
		        .filter(a -> a.accountName.equals(accountName))
		        .findAny();
		 if (sameName.isPresent()) {
			 throw new IllegalArgumentException();
		 }
		else {
		BankAccount account = new BankAccount(accountName);
		accountList.add(account);
		return account;
		}
	}
	
	public boolean transferMoney(BankAccount a, BankAccount b) {
		return false;
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
		        .filter(a -> a.accountName.equals(newAccountName))
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

