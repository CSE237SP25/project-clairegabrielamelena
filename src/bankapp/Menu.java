package bankapp;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	private BankCustomer currentCustomer;
	ArrayList<BankAccount> accountList;
	BankAccount currentAccount;
	
	public Menu() {
		accountList = new ArrayList<BankAccount>();
	}
	
	//Gabriela
	public BankCustomer createCustomerUser(String username) {
		//set as currentCustomer
		//set accountList instance variable from currentCustomer
		return null;
	}
	
	//Gabriela
	public void createBankAccount() {
		// TODO take in a string and call bank account constructor
		// TODO print account successfully created
		
	}
	
	//Gabriela
	public void displayAccountList() {
		//prints out account names and balances
	}
	
	//Claire
	public void displayOptions() {
		// TODO print options like press 1
		// returns the number that the user inputed
		//handle invalid value case
		
		//create account, view account list, modify account (deposit, withdraw, rename)
		
		//also do if statements or switch statements that executes relevant methods according to the menuChoice passed in
	}

	//Melena
	public void displayAccountModificationOptions() {
		// TODO print options for account
		//  the number that the user inputed
		//handle invalid value case
		
		//set currentAccount instance variable to the account selected by the user
		
		
		//TODO print options for account modifications
		// deposit, withdraw, rename conditionals
		// TODO execute relevant methods according to the selection
	}
	
	
	
	
	//deal with later
	//methosd that req user inptu don't need to be tested
	public double getUserInput() {
		Scanner keyboardInput = new Scanner(System.in);
		double userInput = keyboardInput.nextDouble();
		return userInput;
	}
	
	//can and should test methods that process user input
	
	public void processUserInput(double amount) {
		//can handle deposits and withdrawls
	}
	
	public void processUserInput(String amount) {
		theAccount.deposit(amount);
	}
	
	public BankAccount getAccount() {
		return theAccount;
	}
	

}