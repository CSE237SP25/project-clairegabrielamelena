package bankapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;


public class Bank {

	private int NUM_WELCOME_MENU_ITEMS = 2;
	Scanner keyboardInput;
	private HashMap<BankCustomer, String> allBankCustomers;
	private AdminMenu mainAdminMenu;
	private CustomerMenu mainBankCustomerMenu;

	/**
	 * Constructs a new Bank instance, initializing the menus and customer database.
	 */
	public Bank() {
		allBankCustomers = new HashMap<BankCustomer, String>();
		mainAdminMenu = new AdminMenu();
		mainBankCustomerMenu = new CustomerMenu();
		keyboardInput = new Scanner(System.in);
	}

	/**
	 * Launches the main loop of the application.
	 * Switches between welcome, customer, and admin modes based on global state.
	 */
	public void run() {
		GlobalState.getInstance().setUserMode(0); //user_mode = 0, meaning welcome menu

		while(true) {
			while(GlobalState.getInstance().getUserMode() == 0) {
				displayWelcomeMenu();
			}

			if(GlobalState.getInstance().getUserMode() == 1) {
				enterBankCustomerSelectionView();
				while(GlobalState.getInstance().getUserMode() == 1) {
					mainBankCustomerMenu.displayOptions();
				}
			}
			
			if(GlobalState.getInstance().getUserMode() == 2) {
				mainAdminMenu.displayAdminWelcome();
				while(GlobalState.getInstance().getUserMode() == 2) {
					mainAdminMenu.displayOptions();
				}
			}
		}
	}

	/**
	 * Displays the welcome menu with options for logging in as customer or admin.
	 * Updates the global user mode based on selection.
	 */
	public void displayWelcomeMenu() {
		System.out.println("Welcome to the Bank!");
		System.out.println("\nEnter 1 to log into a Bank Customer Account. \nEnter 2 to log into the Bank Admin Account");
		int userSelection = getUserMenuInput(NUM_WELCOME_MENU_ITEMS);

		if (userSelection == 1) {
			GlobalState.getInstance().setUserMode(1);
		} else if (userSelection == 2) {
			GlobalState.getInstance().setUserMode(2);
		}
	}

	/**
	 * Directs user to create a new account or log in, depending on whether customers exist.
	 */
	public void enterBankCustomerSelectionView() {
		if(allBankCustomers.size() == 0) {
			System.out.println("No bank customer profiles currently exist.");
			mainBankCustomerMenu.createNewBankCustomerUserDisplay();
		} else {
			logInAsCustomer();
		}
	}

	/**
	 * Prompts the user to log in to an existing customer account or create a new one.
	 * Sets the current customer on successful login.
	 */
	public void logInAsCustomer() {
		System.out.println("Please select which bank customer profile you would like to log in to.");
		mainAdminMenu.displayListOfBankCustomers();
		ArrayList<BankCustomer> customerList = mainAdminMenu.getAllCustomerListFromBank();
		int maxListItemNumber = customerList.size() + 1;
		System.out.println(maxListItemNumber + ". Make a new account.");

		System.out.println("Enter a number between 1 and " + maxListItemNumber + " to make a selection:");
		int customerSelection = getUserMenuInput(maxListItemNumber);

		if (customerSelection == maxListItemNumber) {
			mainBankCustomerMenu.createNewBankCustomerUserDisplay();
		} else {
			BankCustomer selectedCustomer = customerList.get(customerSelection - 1);
			checkUserPassword(selectedCustomer);
			mainBankCustomerMenu.setCurrentCustomer(selectedCustomer);
		}
	}

	/**
	 * Repeatedly prompts the user to enter a password until the correct one is entered.
	 * @param selectedCustomer the customer attempting to log in
	 */
	public void checkUserPassword(BankCustomer selectedCustomer) {
		String correctPassword = allBankCustomers.get(selectedCustomer);
		boolean passwordCorrect = false;

		while (!passwordCorrect) {
			System.out.println("Enter password for " + selectedCustomer.getUsername() + ":");
			String passwordAttempt = getUserStringInput();

			if (passwordAttempt.equals(correctPassword)) {
				System.out.println("Login successful. Welcome, " + selectedCustomer.getUsername() + "!");
				passwordCorrect = true;
			} else {
				System.out.println("Incorrect password. Please try again.");
			}
		}
	}

	/**
	 * Compares a given password attempt to the correct password for the customer.
	 * @param customer the customer to validate against
	 * @param attempt the attempted password
	 * @return true if the attempt matches the stored password
	 */
	public boolean validatePassword(BankCustomer customer, String attempt) {
		String correctPassword = allBankCustomers.get(customer);
		return attempt.equals(correctPassword);
	}

	/**
	 * Overloaded method for testing: logs in using an index and password string.
	 * @param customerIndex index (1-based) of the customer
	 * @param passwordAttempt the password entered
	 * @return true if login is successful, false otherwise
	 */
	public boolean logInAsCustomer(int customerIndex, String passwordAttempt) {
		ArrayList<BankCustomer> customerList = mainAdminMenu.getAllCustomerListFromBank();
		if (customerIndex < 1 || customerIndex > customerList.size()) {
			return false;
		}

		BankCustomer selectedCustomer = customerList.get(customerIndex - 1);
		if (validatePassword(selectedCustomer, passwordAttempt)) {
			mainBankCustomerMenu.setCurrentCustomer(selectedCustomer);
			return true;
		}
		return false;
	}

	/**
	 * Gets a sanitized string input from the user.
	 * Skips any empty lines or whitespace-only entries.
	 * @return the validated string input
	 */
	public String getUserStringInput() {
		if (keyboardInput.hasNextLine()) {
			String input = keyboardInput.nextLine();
			while (input.trim().isEmpty() && keyboardInput.hasNextLine()) {
				input = keyboardInput.nextLine();
			}
			return input;
		} else {
			return "";
		}
	}

	/**
	 * Prompts user to select a menu option within the valid range.
	 * Handles invalid inputs and retries until a valid number is entered.
	 * @param numMenuItems total number of valid menu items
	 * @return the selected menu option number
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
	 * Adds a new bank customer to the system with the associated password.
	 * @param customerToAdd the customer to add
	 * @param customerPassword their chosen password
	 */
	public void addBankCustomer(BankCustomer customerToAdd, String customerPassword) {
		allBankCustomers.put(customerToAdd, customerPassword);
	}

	/**
	 * Returns a list of all registered bank customers.
	 * @return a list of BankCustomer objects
	 */
	public ArrayList<BankCustomer> getAllBankCustomers() {
		Set<BankCustomer> keySet = allBankCustomers.keySet();
		return new ArrayList<>(keySet);
	}

	public AdminMenu getMainAdminMenu() {
		return mainAdminMenu;
	}

	public void setMainAdminMenu(AdminMenu mainAdminMenu) {
		this.mainAdminMenu = mainAdminMenu;
	}


	public CustomerMenu getMainBankCustomerMenu() {
		return mainBankCustomerMenu;
	}

	public void setMainBankCustomerMenu(CustomerMenu mainBankCustomerMenu) {
		this.mainBankCustomerMenu = mainBankCustomerMenu;
	}
}
