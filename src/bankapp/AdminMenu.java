package bankapp;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu {
	private ArrayList<BankCustomer> bankCustomerList;
	private BankCustomer currentCustomer;
	//private Menu customerMenu;
	Scanner keyboardInput;
	
	
	public AdminMenu() {
		//customerMenu = associatedCustomerMenu;
		keyboardInput = new Scanner(System.in);
	}
	
	
	public void displayListOfBankCustomers() {
		
	}
	
	public BankCustomer selectCurrentCustomer(String username) {
		return null;
	}
	
	public ArrayList<BankAccount> getCurrentBankCustomerBankAccounts() {
		return null;
	}
	
	public void displayCurrentCustomerBankAccounts() {
		
	}
	
	public boolean deleteBankAccount() {
		return false;
	}
	
	public boolean deleteBankCustomer() {
		return false;
	}
	
	public BankCustomer addBankCustomer(BankCustomer customer) {
		bankCustomerList.add(customer);
		return customer;
	}
	
	/*public void updateBankCustomerList() {
			bankCustomerList = customerMenu.getCustomerList();
	}*/
}
