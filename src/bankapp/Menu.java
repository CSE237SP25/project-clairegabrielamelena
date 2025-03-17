package bankapp;

import java.util.Scanner;

public class Menu {
	private BankAccount theAccount;
	
	public Menu() {
		theAccount = new BankAccount();
	}
	
	public void displayOptions() {
		
	}
	
	//methosd that req user inptu don't need to be tested
	public double getUserInput() {
		Scanner keyboardInput = new Scanner(System.in);
		double userInput = keyboardInput.nextDouble();
		return userInput;
	}
	
	//can and should test methods that process user input
	
	public void processUserInput(double amount) {
		theAccount.deposit(amount);
	}
	
	public BankAccount getAccount() {
		return theAccount;
	}
	

}