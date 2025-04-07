package bankapp;


import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

	private int NUM_WELCOME_MENU_ITEMS = 2;
	Scanner keyboardInput;
	private ArrayList<BankCustomer> allBankCustomers;
	private AdminMenu mainAdminMenu;
	private Menu mainBankCustomerMenu;

	public Bank() {
		allBankCustomers = new ArrayList<BankCustomer>();
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
			
			while(GlobalState.getInstance().getUserMode() == 1) { //Bank Customer Mode
				if(mainBankCustomerMenu.getCurrentCustomer() == null) {
					mainBankCustomerMenu.createNewBankCustomerUserDisplay();
				}
				else {
					mainBankCustomerMenu.displayOptions();
				}
			}
			
			while(GlobalState.getInstance().getUserMode() == 2) { //Bank Admin Mode
				mainAdminMenu.displayOptions();
			}

		}
	}


	public void displayWelcomeMenu() {
		System.out.println("Welcome to the Bank!");
		System.out.println("\nEnter 1 to log into a Bank Customer Account. \nEnter 2 to log into a Bank Admin Account");
		int userSelection = getUserMenuInput();
		
		if (userSelection == 1) { //If they select log into a Bank Customer Account
			GlobalState.getInstance().setUserMode(1);
		}
		else if (userSelection == 2) { //If they select log into Bank Admin Account
			GlobalState.getInstance().setUserMode(2);
		}

	}
	public int getUserMenuInput() { //ADD TRY CATCH FOR IF THEY ENTER A STRING
		int userInput = keyboardInput.nextInt();
		while(userInput < 1 || userInput > NUM_WELCOME_MENU_ITEMS) {
			System.out.println("Not a valid input. Please select 1 or 2 on your keyboard.");
			userInput = keyboardInput.nextInt();
		}
		return userInput;
	}



	public void addBankCustomer(BankCustomer customerToAdd) {
		allBankCustomers.add(customerToAdd);
	}

	public ArrayList<BankCustomer> getAllBankCustomers() {
		return allBankCustomers;
	}


}
