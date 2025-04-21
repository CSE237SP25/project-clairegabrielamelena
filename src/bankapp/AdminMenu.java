package bankapp;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu {
	
	private BankCustomer currentCustomer;
	Scanner keyboardInput;
	private int NUM_ADMIN_MENU_ITEMS = 3;
	
	
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
			int i = 1;
			for(BankCustomer customer : customerList) {
				System.out.println(i+ ". "+ customer.getUsername());
				i++;
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
	
	
	public void displayOptions() {
		System.out.println("\nAdmin Menu Options:"
				+ "\n1. View a list of Bank Customers"
				+ "\n2. View a Bank Customer's Account List"
				+ "\n3. Exit bank admin profile");
		System.out.println("\nPlease press a number key to indicate your selection.");
		System.out.println();
		int userSelection = getUserMenuInput(NUM_ADMIN_MENU_ITEMS); 

		processMenuSelection(userSelection);
	}

	//Claire
	public void processMenuSelection(int userSelection) {
		if(userSelection == 1) { //View a list of Bank Customers
			System.out.println("Bank Customers: ");
			displayListOfBankCustomers();
		}
		else if(userSelection == 2) { //View a bank customer's account list
			ArrayList<BankCustomer> customerList = this.getAllCustomerListFromBank();
			if(customerList.size() == 0) {
				System.out.println("There are no bank customer accounts on file.");
				System.out.println();
				return;
			}
			viewBankCustomerAccounts();
		}
		else if(userSelection == 3) { //leave
			GlobalState.getInstance().setUserMode(0);
			return;
		}
		else {
			throw new IllegalArgumentException("Error: not a valid menu selection.");
		}
	}

	private void viewBankCustomerAccounts() {
		ArrayList<BankCustomer> customerList = this.getAllCustomerListFromBank();
		displayListOfBankCustomers();
		System.out.println("Enter a number between 1 and "
				+ customerList.size() + " to select which bank customer you would like to view accounts for:");
		int customerSelection = getUserMenuInput(customerList.size());
		String selectedCustomerUsername = customerList.get(customerSelection-1).getUsername();
		currentCustomer = selectCurrentCustomer(selectedCustomerUsername, getAllCustomerListFromBank());
		displayBankAccountList(getCurrentBankCustomerBankAccounts());
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
			System.out.println("Bank Accounts for Bank Customer " + currentCustomer.getUsername() + " :");
			for(BankAccount account: bankAccountList) {
				account.displayBankAccount();
				System.out.println();
			}
		}
		
	}
	
	public int getUserMenuInput(int numMenuItems) { //ADD TRY CATCH FOR IF THEY ENTER A STRING
		int userInput = keyboardInput.nextInt(); 
		while(userInput < 1 || userInput > numMenuItems) {
			System.out.println("Not a valid input. Please select an option 1 through " + numMenuItems + " on your keyboard.");
			userInput = keyboardInput.nextInt();
		}
		return userInput;
	}
	
	public String getUserStringInput() {
		String userInput = keyboardInput.nextLine();
		return userInput;

	}
	
	
	public boolean deleteBankAccount() {
		return false;
	}
	
	public boolean deleteBankCustomer() {
		return false;
	}
	
	
}