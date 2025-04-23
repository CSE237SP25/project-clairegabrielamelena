package bankapp;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerMenu {
	private BankCustomer currentCustomer;
	private ArrayList<BankAccount> bankAccountList; 

	BankAccount currentAccount; 
	Scanner keyboardInput;


	private int NUM_PRIMARY_MENU_ITEMS = 5;
	private int NUM_MODIFICATION_SUBMENU_ITEMS = 4;

	/**
     * Constructs a new Menu with an empty account list
     */
	public CustomerMenu() {
		bankAccountList = new ArrayList<BankAccount>();

		keyboardInput = new Scanner(System.in);
		currentCustomer = null;
	}

	/**
     * Creates a new BankCustomer, adds them to the global bank instance, and sets them as the current user.
     * @param username the desired username
     * @param password the user's password
     * @return the created BankCustomer object
     * @throws IllegalArgumentException if the username already exists
     */
	public BankCustomer createCustomerUserInterface(String username, String password) {
		currentCustomer = new BankCustomer(username);
		bankAccountList = currentCustomer.getAccountList(); 
		System.out.println("Welcome, " + username +". Your bank customer profile has been created succesfully.");
		System.out.println();
		if (isNameInUse(username)) {
		    throw new IllegalArgumentException("Username already exists.");
		}

		Main.mainBank.addBankCustomer(currentCustomer, password);

		return currentCustomer;
	}

	 /**
     * Initiates the creation of a new BankCustomer
     */
	public void createNewBankCustomerUserDisplay() {
		System.out.println("To create a customer user profile, enter a username: ");
		String usernameInput = this.getUsernameInput();
		System.out.println("Please set your password: ");
		String passwordInput = getUserPasswordInput();
		this.createCustomerUserInterface(usernameInput, passwordInput);
	}
	
	 /**
     * Prompts the user to enter a username until a valid and non-duplicate one is provided
     * @return a valid, available username
     */
	public String getUsernameInput() {
		ArrayList<BankCustomer> allCustomerList = Main.mainBank.getAllBankCustomers();
		if(allCustomerList.size()>0) {
			keyboardInput.nextLine();
		}
		String usernameInput = getUserStringInput();
		while(this.isNameInUse(usernameInput) || usernameInput == null){
			if(this.isNameInUse(usernameInput)) {
				System.out.println("Sorry, that username is already in use. Please enter another username.");
			} else{
				System.out.println("Sorry, a username can only include characters and numbers 0-9. Please enter another username.");
			}
			usernameInput = getUserStringInput();
		}
		System.out.println(usernameInput + " is an available username.");
		return usernameInput;
	}
	
	 /**
     * Checks whether a given username is already in use in the bank's customer list.
     * @param testUsername the username to test
     * @return true if the username is already in use, false otherwise
     */
	public boolean isNameInUse(String testUsername) {
		ArrayList<BankCustomer> allCustomerList = Main.mainBank.getAllBankCustomers();
		boolean nameAlreadyInUse = false;
		for(BankCustomer customer : allCustomerList) {
		
			if(customer.getUsername().equals(testUsername)) {
				nameAlreadyInUse = true;	
			}
		}
		return nameAlreadyInUse;
	}
	

    /**
     * Prompts the user to create a new bank account and adds it to the current customer's account list.
     */
	public void createBankAccountInterface() {
		boolean success = false;
		while (!success) {
			System.out.println("A bank account name must not be the same name as "
					+ "an existing bank account under your user account and can only include alphabetical letters and numbers."
					+ "\nEnter a name for your new bank account:");
			//keyboardInput.nextLine();
			String bankAccountNameInput = getUserStringInput();
			if (bankAccountNameInput != null) {		
				try {
					currentCustomer.openAccount(bankAccountNameInput);
					System.out.println("Bank account " + bankAccountNameInput + " created succesfully.");
					success = true;
					
				} catch (IllegalArgumentException e) {
					System.out.println("Account opening failed. An invalid bank account name was entered.");
					System.out.println();
				}
			}
		}
	}

	 /**
     * Displays a numbered list of all bank accounts associated with the current customer, or prints that there are no accounts on file 
     */
	public void displayAccountList() {
		if(bankAccountList.isEmpty()) {
			System.out.println("There are no bank accounts on file.");
			System.out.println();
			return;
		}
		int i = 1;
		System.out.println("Available accounts for " + currentCustomer.getUsername() + ":");
		for(BankAccount account : bankAccountList) {
			System.out.print(i + ". "); 
			account.displayBankAccount();
			System.out.println();
			i++;
		}
	}

	 /**
     * Displays the main menu options and processes the user's selection.
     */
	public void displayOptions() {
		System.out.println("Menu Options:"
				+ "\n1. Create a new bank account"
				+ "\n2. View a list of your bank accounts"
				+ "\n3. Modify a bank account (deposit, withdraw, or rename)"
				+ "\n4. Transfer money between bank accounts"
				+ "\n5. Exit bank customer profile");
		System.out.println("\nPlease press a number key to indicate your selection.");
		System.out.println();
		int userSelection = getUserMenuInput(NUM_PRIMARY_MENU_ITEMS); //made a parameter for how many menu options there are. Not sure how to not hard code it or if there's a better way

		processMenuSelection(userSelection);
	}

    /**
     * Handles logic for processing a menu selection from the main menu.
     * @param userSelection the menu option number selected
     */
	public void processMenuSelection(int userSelection) {
		if(userSelection == 1) { //Create account
			createBankAccountInterface();
		}
		else if(userSelection == 2) { //View account list
			displayAccountList();
		}
		else if(userSelection == 3) { //Modify an account (deposit, withdraw, or rename)
			displayAccountModificationOptions();
		}
		else if(userSelection == 4) { //Transfer money between bank accounts
			processTransfer();
		}
		else if(userSelection == 5) { //Leave
			GlobalState.getInstance().setUserMode(0);
			return;
		}
		else {
			throw new IllegalArgumentException("Error: not a valid menu selection.");
		}
	}

    /**
     * Handles the fund transfer process between two bank accounts under the current user.
     * Prints messages if the operation fails or succeeds.
     */
	public void processTransfer() {
		if (bankAccountList.size() < 2) {
			System.out.println("Must have at least 2 bank accounts to complete a transfer. Cannot transfer funds.");
			return;
		}
		System.out.println("Transfer initiated.");
		displayAccountList();

		BankAccount sourceAccount = getSourceAccountSelection();
		BankAccount destinationAccount = getDestinationAccountSelection(sourceAccount);

		double transferAmount = getTransferAmount(sourceAccount, destinationAccount);

		try {
			currentCustomer.transferMoney(sourceAccount, destinationAccount, transferAmount);
		} catch (IllegalArgumentException e) {
			System.out.println("Insufficient funds. Unable to complete transfer.");
		}
	}
	
    /**
     * Prompts the user to select a source account for the transfer.
     * @return the selected source BankAccount
     */
	public BankAccount getSourceAccountSelection() {
		System.out.println("Enter a number between 1 and " + bankAccountList.size() + " to select the source account.");
		int sourceSelection = getUserMenuInput(bankAccountList.size());
		return bankAccountList.get(sourceSelection - 1);
	}
	
    /**
     * Prompts the user to select a destination account different from the source.
     * @param sourceAccount the source account previously selected
     * @return the selected destination BankAccount
     */
	public BankAccount getDestinationAccountSelection(BankAccount sourceAccount) {
		int destinationSelection;
		do {
			System.out.println("Enter a number between 1 and " + bankAccountList.size() + " to select the destination account.");
			destinationSelection = getUserMenuInput(bankAccountList.size());

			if (destinationSelection == bankAccountList.indexOf(sourceAccount) + 1) {
				System.out.println("The destination account cannot be the same as the source account.\nPlease choose a different account.");
			}
		} while (destinationSelection == bankAccountList.indexOf(sourceAccount) + 1);

		return bankAccountList.get(destinationSelection - 1);
	}
	
    /**
     * Prompts the user for the amount to transfer between accounts.
     * @param sourceAccount the source account
     * @param destinationAccount the destination account
     * @return the valid transfer amount entered by the user
     */
	public double getTransferAmount(BankAccount sourceAccount, BankAccount destinationAccount) {
		System.out.println("\nEnter the amount to transfer from " + sourceAccount.getAccountName() + " to " + destinationAccount.getAccountName() + ":");
		return getUserInputDouble();
	}

	
    /**
     * Prompts the user to select an account from their list to modify.
     */
	public void selectAccount() {
		displayAccountList();
		System.out.println("Enter a number between 1 and "+ bankAccountList.size() + " to select which account to modify:"); 
		int accountSelection = getUserMenuInput(bankAccountList.size()) ;// has to be greater than 1 which is not true T^T
		this.currentAccount = bankAccountList.get(accountSelection-1); 
	}

    /**
     * Displays options for modifying an existing account (deposit, withdraw, rename).
     */
	public void displayAccountModificationOptions() {
		if (this.currentCustomer.getAccountList().isEmpty()) {
			System.out.println("You must create an account to make any modifications.");
			return;			
		}

		System.out.println("Bank Account Modification Menu Options:"
				+ "\n1. Make a deposit"
				+ "\n2. Make a withdrawal"
				+ "\n3. Rename bank account"
				+ "\n4. Go back");
		System.out.println("\nPlease press a number key to indicate your selection.");
		System.out.println();
		int userSelection = getUserMenuInput(NUM_MODIFICATION_SUBMENU_ITEMS); 
		processAccountModification(userSelection); 
	}	

	
    /**
     * Executes the appropriate modification based on user selection.
     * @param userSelection the chosen modification action
     */
	public void processAccountModification(int userSelection) {
		if(userSelection == 1) { //deposit
			selectAccount(); 
			performDeposit();
		}
		else if (userSelection == 2) { //withdraw
			selectAccount(); 
			performWithdrawal();
		}

		else if (userSelection == 3) { // Rename
			selectAccount(); 
			performRenaming();
		}
		
		else if (userSelection == 4) { 
			return;
		}

		else {
			throw new IllegalArgumentException("Error: not a valid menu selection.");
		}
	}

    /**
     * Handles renaming of an account through user input, ensuring uniqueness.
     */
	private void performRenaming() {
		boolean success = false;
		while (!success) {
			System.out.println("Enter a new account name (must be a unique name):");
			String rename = getUserStringInput();


			if (rename.length() < 1) {
				System.out.println("Account name must be at least 1 character long.");
				continue;
			}

			try {
				currentCustomer.renameAccount(rename);
				System.out.println("Rename Successful");
				success = true;  // Exit loop once successful
			} catch (IllegalArgumentException e) {
				System.out.println("That account name is already taken. Please choose a different one.");
			}
		}
	}

    /**
     * Handles withdrawal from the current account, validating user input.
     */
	private void performWithdrawal() {
		boolean success = false;
		while (!success) {
			System.out.println("Enter amount to withdraw (must be greater than 0 and not more than your balance):");
			double withdrawalAmount = getUserInputDouble();
			try {
				currentAccount.withdraw(withdrawalAmount);
				System.out.println("Withdrawal Successful");
				success = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid withdrawal amount. Please try again.");
			}
		}
	}

    /**
     * Handles deposit into the current account, validating user input.
     */
	private void performDeposit() {
		boolean success = false;
		while(!success) {
			System.out.println("Enter amount to deposit (must be greater than 0):");
			double depositAmount = getUserInputDouble(); 
			try {
				currentAccount.deposit(depositAmount); 
				System.out.println("Deposit Successful");
				success = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid deposit amount. Please try again.");
			}
		}
	}


    /**
     * Prompts the user for a valid menu selection within the given range.
     * @param numMenuItems the number of valid options
     * @return a valid menu selection
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
     * Prompts the user to enter a valid double value (must be > 0).
     * @return a valid positive double
     */
	public double getUserInputDouble() {
		double userInput = -1;

		while (userInput <= 0) {
			if (keyboardInput.hasNextDouble()) {
				userInput = keyboardInput.nextDouble();
				if (userInput <= 0) {
					System.out.println("Amount must be greater than 0.");
				}
			} else {
				System.out.println("Not a valid input. Please enter a number.");
				keyboardInput.next(); // important: clear the invalid token
			}
		}
		return userInput;
	}

    /**
     * Prompts the user for their password input. Can have special characters.
     * @return the password string
     */
	public String getUserPasswordInput() {
		if (keyboardInput.hasNextLine()) {
			String input = keyboardInput.nextLine();
			while ((input.trim().isEmpty() || input.contains(" ")) && keyboardInput.hasNextLine()) {
				System.out.println("Passwords cannot be blank or contain spaces. Please enter a valid password:");
				input = keyboardInput.nextLine();
			}
			if (!input.trim().isEmpty() && !input.contains(" ")) {
				return input;
			}
		}
		return null;
	}


    /**
     * Prompts the user for a string input that is alphanumeric and non-blank.
     * @return a validated user string or null if invalid
     */
	public String getUserStringInput() {
		String userInput = keyboardInput.nextLine();
		if(userInput.isEmpty()) {
			userInput = keyboardInput.nextLine();
		}
		if (userInput.chars().allMatch(Character::isLetterOrDigit) && !userInput.isBlank() && !userInput.isEmpty()) {
			return userInput; 
		}
		return null;
	}


	public BankAccount getBankAccount() {
		return currentAccount;
	}

	public ArrayList<BankAccount> getBankAccountList(){
		return bankAccountList;
	}

	public BankCustomer getCurrentCustomer() {
		return currentCustomer;
	}
	
	public void setCurrentCustomer(BankCustomer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}


}