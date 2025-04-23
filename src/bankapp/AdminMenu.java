package bankapp;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu {
	private BankCustomer currentCustomer;
	Scanner keyboardInput;
	
	private int NUM_ADMIN_MENU_ITEMS = 3;
	private String PASS = "admin";
	
	
    /**
     * Constructs an AdminMenu with a new Scanner for user input.
     */
	public AdminMenu() {
		keyboardInput = new Scanner(System.in);
	}
	
    /**
     * Returns a list of all bank customers from the global bank instance.
     * @return list of all BankCustomer objects
     */
	public ArrayList<BankCustomer> getAllCustomerListFromBank(){
		return Main.mainBank.getAllBankCustomers();
	}
	
    /**
     * Displays a numbered list of all bank customers in the bank.
     * If no customers exist, an appropriate message is printed.
     */
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
	
	
    /**
     * Finds a BankCustomer by username from a provided list.
     * @param username the username to search for
     * @param customerList the list to search within
     * @return the BankCustomer matching the username
     * @throws IllegalArgumentException if no customer with the given username is found
     */
	public BankCustomer selectCurrentCustomer(String username, ArrayList<BankCustomer> customerList) {
		for(BankCustomer customer : customerList) {
			if(customer.getUsername().equals(username)) {
				return customer;
			}
		}
		throw new IllegalArgumentException("No bank customer account was found with the username " + username);
	}
	
	
    /**
     * Prompts the user to enter the admin password until the correct one is provided.
     * Password is compared to a hardcoded string "admin".
     */
	public void displayAdminWelcome() {
		boolean passwordCorrect = false;
		
		while(!passwordCorrect) {
			System.out.println("Please enter the admin password (available in the README): ");
			String password = getUserStringInput(); 
			
			if(password.equals(PASS)) {
				System.out.println("Login successful. Welcome Admin!");
				passwordCorrect = true;
			} else {
				System.out.println("Incorrect password. Please try again.");
			}
		}
	}
	
    /**
     * Displays the main admin menu options and processes the user's selection.
     */
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

    /**
     * Executes the logic corresponding to the selected admin menu option.
     * @param userSelection the menu option selected
     */
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

    /**
     * Prompts the admin to select a customer and view their associated accounts.
     * @throws IllegalArgumentException if currentCustomer is null
     */
	public void viewBankCustomerAccounts() {
		ArrayList<BankCustomer> customerList = this.getAllCustomerListFromBank();
		displayListOfBankCustomers();
		System.out.println("Enter a number between 1 and "
				+ customerList.size() + " to select which bank customer you would like to view accounts for:");
		int customerSelection = getUserMenuInput(customerList.size());
		String selectedCustomerUsername = customerList.get(customerSelection-1).getUsername();
		currentCustomer = selectCurrentCustomer(selectedCustomerUsername, getAllCustomerListFromBank());
		displayBankAccountList(getCurrentBankCustomerBankAccounts());
	}
	
	
    /**
     * Returns the account list of the currently selected bank customer.
     * @return a list of BankAccount objects
     * @throws IllegalArgumentException if no customer is currently selected
     */
	public ArrayList<BankAccount> getCurrentBankCustomerBankAccounts() {
		if(currentCustomer == null) {
			throw new IllegalArgumentException("No bank customer user selected");
		}
		else {
			return currentCustomer.getAccountList();
		}
	}
	
	
    /**
     * Displays a list of bank accounts for the current customer.
     * @param bankAccountList the list of accounts to display
     */
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
	
	
    /**
     * Prompts the user to select a menu option within a given range.
     * Handles input mismatches and invalid ranges through console prompts.
     * @param numMenuItems the number of available menu options
     * @return a valid menu selection between 1 and menuItems
     */
	public int getUserMenuInput(int numMenuItems) {
		int userInput = -1;
		boolean valid = false;

		while (!valid) {
			try {
				userInput = keyboardInput.nextInt();
				if (userInput >= 1 && userInput <= numMenuItems) {
					valid = true;
				} else {
					System.out.println("Not a valid input. Please select an option 1 through " + numMenuItems + " on your keyboard.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				keyboardInput.next(); 
			}
		}
		return userInput;
	}
	
	
    /**
     * Gets the next line of input from the user via the keyboard.
     * @return the user's input as a String
     */
	public String getUserStringInput() {
		String userInput = keyboardInput.nextLine();
		return userInput;
	}
	
	
	public BankCustomer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(BankCustomer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public boolean deleteBankAccount() {
		return false;
	}
	
	public boolean deleteBankCustomer() {
		return false;
	}
	
	
}