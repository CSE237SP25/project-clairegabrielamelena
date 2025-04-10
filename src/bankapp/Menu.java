package bankapp;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	private BankCustomer currentCustomer;
	
	private ArrayList<BankAccount> bankAccountList;

	BankAccount currentAccount; 
	Scanner keyboardInput;


	private int NUM_PRIMARY_MENU_ITEMS = 5;
	private int NUM_MODIFICATION_SUBMENU_ITEMS = 4;
	
	public Menu() {
		bankAccountList = new ArrayList<BankAccount>();
		
		keyboardInput = new Scanner(System.in);
		currentCustomer = null;
		//associatedAdminMenu = enteredAdminMenu;
		
		
	}

	//Gabriela
	public BankCustomer createCustomerUser(String username) {
		currentCustomer = new BankCustomer(username);
		System.out.println("Welcome, " + username +". Your bank customer profile has been created succesfully.");
		System.out.println();
		Main.mainBank.addBankCustomer(currentCustomer);
		
		return currentCustomer;
	}

	public void createNewBankCustomerUserDisplay() {
		System.out.println("To create a customer user profile, enter a username: ");
		String usernameInput = getUserStringInput();
		this.createCustomerUser(usernameInput);
	}

	//Gabriela
	public void createBankAccount(String accountName) {
		BankAccount account = currentCustomer.openAccount(accountName);
		currentAccount = account;
		bankAccountList = currentCustomer.getAccountList();
		System.out.println("Account " + accountName +" succesfully created.");
		System.out.println();

	}


	//Gabriela
	public void displayAccountList() {
		//prints out account names and balances
		//using method made in BankAccount class to print out name+balance of bank account
		if(bankAccountList.isEmpty()) {
			System.out.println("There are no bank accounts on file.");
			System.out.println();
			return;
		}
		int i = 1;
		for(BankAccount account : bankAccountList) {
			System.out.print(i + ". "); 
			account.displayBankAccount();
			System.out.println();
			i++;
		}
	}

	//Claire
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

	//Claire
	public void processMenuSelection(int userSelection) {
		if(userSelection == 1) { //Create account
			System.out.println("Enter bank account name:");
			keyboardInput.nextLine();
			String accountName = getUserStringInput();
			createBankAccount(accountName);
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
	
	public void processTransfer() {
		System.out.println("Transfer initiated. Available Accounts: ");
		displayAccountList();
		
		System.out.println("Enter a number between 1 and "+ bankAccountList.size() + " to select the source account.");
		int sourceSelection = getUserMenuInput(bankAccountList.size()) ;
		BankAccount sourceAccount = bankAccountList.get(sourceSelection-1); 
		
		System.out.println("Enter a number between 1 and "+ bankAccountList.size() + " to select the destination account.");
		int destinationSelection = getUserMenuInput(bankAccountList.size());
		while(destinationSelection == sourceSelection) {
			System.out.print("The destination account cannot be the same as the source account. ");
			System.out.println("Enter a number between 1 and "+ bankAccountList.size() + " to select the destination account.");
			destinationSelection = getUserMenuInput(bankAccountList.size());
		}
		BankAccount destinationAccount = bankAccountList.get(destinationSelection-1);
		
		System.out.println();
		System.out.println("Enter the amount to transfer from "+ sourceAccount.getAccountName() + " to " + destinationAccount.getAccountName() + ":");
		double transferAmount = getUserInputDouble();
		
		currentCustomer.transferMoney(sourceAccount, destinationAccount, transferAmount);
		//System.out.println("Transfer completed."); can't go here because then it prints even if the transfer was unsuccessful.
	}

	public void selectAccount() {
		displayAccountList();
		System.out.println("Enter a number between 1 and "+ bankAccountList.size() + " to select which account to modify"); 
		int accountSelection = getUserMenuInput(bankAccountList.size()) ;// has to be greater than 1 which is not true T^T
		this.currentAccount = bankAccountList.get(accountSelection-1); 
		}
	
	
	//Melena
	public void displayAccountModificationOptions() {
		
		System.out.println("Bank Account Modification Menu Options:"
				+ "\n1. Make a deposit"
				+ "\n2. Make a withdrawal"
				+ "\n3. Rename bank account"
				+ "\n4. Go back");
		System.out.println("\nPlease press a number key to indicate your selection.");
		System.out.println();
		int userSelection = getUserMenuInput(NUM_MODIFICATION_SUBMENU_ITEMS); 
		selectAccount(); 
		processAccountModification(userSelection); 
		

	}	
	
	
	public void processAccountModification(int userSelection) {
		if(userSelection == 1) { //deposit
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
		else if (userSelection == 2) { //withdraw
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

		else if (userSelection == 3) { // Rename
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
		else if (userSelection == 4) { //go back
			return;
		}
		else {
			throw new IllegalArgumentException("Error: not a valid menu selection.");
		}
	}
	


	
	public int getUserMenuInput(int numMenuItems) {
	    int userInput = -1;
	    boolean valid = false;

	    while (!valid) {

	        try {
	            userInput = keyboardInput.nextInt();
	            if (userInput >= 1 && userInput <= numMenuItems) {
	                valid = true;
	            } else {
	                System.out.println("Not a valid input. Please select an option 1 through" + numMenuItems + " on your keyboard.");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a number.");
	            keyboardInput.next(); 
	        }
	    }

	    return userInput;
	}
	


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

	public double getUserDoubleInput() {
		//Scanner keyboardInput = new Scanner(System.in);
		double userInput = keyboardInput.nextDouble();
		return userInput;
	}

	//can and should test methods that process user input

	public void processUserInput(double amount) {
		//can handle deposits and withdrawls
	}

	public String getUserStringInput() {
	    if (keyboardInput.hasNextLine()) {
	        return keyboardInput.nextLine();
	    } else {
	        return ""; // just in case
	    }
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
	



}