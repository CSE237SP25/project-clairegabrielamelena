package bankapp;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu {
	
	private BankCustomer currentCustomer;
	Scanner keyboardInput;
	
	
	public AdminMenu() {
		//customerMenu = associatedCustomerMenu;
		keyboardInput = new Scanner(System.in);
	}
	
	public ArrayList<BankCustomer> getAllCustomerListFromBank(){
		return Main.mainBank.getAllBankCustomers();
	}
	
	public void displayListOfBankCustomers() {

		ArrayList<BankCustomer> customerList = this.getAllCustomerListFromBank();
		if(customerList.size() == 0) {
			System.out.println("There are no bank customer accounts on file.");
			System.out.println();
		}
		else {
			for(BankCustomer customer : customerList) {
				System.out.println(customer.getUsername());
			}
			System.out.println();
		}
		
	}
	
	public BankCustomer selectCurrentCustomer(String username, ArrayList<BankCustomer> customerList) {
		for(BankCustomer customer : customerList) {
			if(customer.getUsername().equals(username)) {
				return customer;
			}
			
		}
		throw new IllegalArgumentException("No bank customer account was found with the username " + username);
	}
	
	
	public ArrayList<BankAccount> getCurrentBankCustomerBankAccounts() {
		if(currentCustomer == null) {
			throw new IllegalArgumentException("No bank customer user selected");
		}
		else {
			return currentCustomer.getAccountList();
		}
	}
	
	
	public void displayBankAccountList(ArrayList<BankAccount> bankAccountList) {
		
		if(bankAccountList.size() == 0) {
			System.out.println("There are no accounts associated with the current customer");
		}
		else {
			for(BankAccount account: bankAccountList) {
				account.displayBankAccount();
				System.out.println();
			}
		}
		
	}
	
	
	
	
	public boolean deleteBankAccount() {
		return false;
	}
	
	public boolean deleteBankCustomer() {
		return false;
	}
	
	
}
