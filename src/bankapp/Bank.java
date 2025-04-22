package bankapp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bank {

	private int NUM_WELCOME_MENU_ITEMS = 2;
	Scanner keyboardInput;
	private HashMap<BankCustomer, String> allBankCustomers;
	private AdminMenu mainAdminMenu;
	private Menu mainBankCustomerMenu;
	private boolean adminStart = false; 

	public Bank() {
		allBankCustomers = new HashMap<BankCustomer, String>();
		mainAdminMenu = new AdminMenu();
		mainBankCustomerMenu = new Menu();
		keyboardInput = new Scanner(System.in);
	}

	public void run() {
		GlobalState.getInstance().setUserMode(0); //user_mode = 0, meaning welcome menu


		while(true) {
			while(GlobalState.getInstance().getUserMode() == 0) { //Welcome Menu
				displayWelcomeMenu();
			}

			if(GlobalState.getInstance().getUserMode() == 1) { //Bank Customer Mode
				enterBankCustomerSelectionView();
				while(GlobalState.getInstance().getUserMode() == 1) {
					mainBankCustomerMenu.displayOptions();
				}
			}

			while(GlobalState.getInstance().getUserMode() == 2) { //Bank Admin Mode
				if (!adminStart) {
					mainAdminMenu.displayAdminWelcome();
					adminStart = true; 
				}
				else {
					mainAdminMenu.displayOptions();
				}
			
			}

		}
	}


	public void displayWelcomeMenu() {
		System.out.println("Welcome to the Bank!");
		System.out.println("\nEnter 1 to log into a Bank Customer Account. \nEnter 2 to log into the Bank Admin Account");
		int userSelection = getUserMenuInput();

		if (userSelection == 1) { //If they select log into a Bank Customer Account
			GlobalState.getInstance().setUserMode(1);
		}
		else if (userSelection == 2) { //If they select log into Bank Admin Account
			GlobalState.getInstance().setUserMode(2);
		}

	}


	public void enterBankCustomerSelectionView() {
		if(allBankCustomers.size() == 0) { //if no customers exist
			System.out.println("No bank customer profiles currently exist.");
			mainBankCustomerMenu.createNewBankCustomerUserDisplay();
		}else { //log in
			logInAsCustomer();
		}
	}

	public void logInAsCustomer() {
		System.out.println("Please select which bank customer profile you would like to log in to.");
		mainAdminMenu.displayListOfBankCustomers();
		ArrayList<BankCustomer> customerList = mainAdminMenu.getAllCustomerListFromBank();
		int maxListItemNumber = customerList.size()+1;
		System.out.println(maxListItemNumber + ". Make a new account.");

		System.out.println("Enter a number between 1 and "
				+ maxListItemNumber + " to make a selection:");
		int customerSelection = getUserMenuInput(maxListItemNumber);
		if(customerSelection == maxListItemNumber) {
			mainBankCustomerMenu.createNewBankCustomerUserDisplay();
			return;
		}
		else {
			BankCustomer selectedCustomer = customerList.get(customerSelection-1);
			checkUserPassword(selectedCustomer);
			mainBankCustomerMenu.setCurrentCustomer(selectedCustomer);
		}
	}

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

	public String getUserStringInput() {
		if (keyboardInput.hasNextLine()) {
			String input = keyboardInput.nextLine();
			while (input.trim().isEmpty() && keyboardInput.hasNextLine()) {
				// Skip empty lines caused by leftover newlines
				input = keyboardInput.nextLine();
			}
			return input;
		} else {
			return "";
		}
	}


	public int getUserMenuInput() {
		int userInput = -1;
		boolean valid = false;

		while (!valid) {

			try {
				userInput = keyboardInput.nextInt();
				if (userInput >= 1 && userInput <= NUM_WELCOME_MENU_ITEMS) {
					valid = true;
				} else {
					System.out.println("Not a valid input. Please select 1 or 2.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				keyboardInput.next(); 
			}
		}

		return userInput;
	}

	public int getUserMenuInput(int numMenuItems) { //ADD TRY CATCH FOR IF THEY ENTER A STRING
		int userInput = keyboardInput.nextInt(); 
		while(userInput < 1 || userInput > numMenuItems) {
			System.out.println("Not a valid input. Please select an option 1 through " + numMenuItems + " on your keyboard.");
			userInput = keyboardInput.nextInt();
		}
		return userInput;
	}



	public void addBankCustomer(BankCustomer customerToAdd, String customerPassword) {
		allBankCustomers.put(customerToAdd, customerPassword);
	}

	public ArrayList<BankCustomer> getAllBankCustomers() {
		Set<BankCustomer> keySet = allBankCustomers.keySet();
		ArrayList<BankCustomer> customerList = new ArrayList<>(keySet);
		return customerList;
	}


}