package bankapp;

public class Main {
	
	public static Bank mainBank;

	public static void main(String[] args) {
		// TODO prompt the user to create a bank customer user
		
		// TODO prompt the user to create a bank account
		
		// TODO print the current status (user created and account created + account balance)
		
		// TODO prompt the user to create the first deposit
		
		mainBank = new Bank();
		AdminMenu mainAdminMenu = new AdminMenu();
		Menu mainBankCustomerMenu = new Menu();
		
		
		if(mainBankCustomerMenu.getCurrentCustomer() == null) {
			mainBankCustomerMenu.createNewBankCustomerUserDisplay();
		}
		
		
		
		while(true) {
			mainBankCustomerMenu.displayOptions();
		}
		

		
	}
}
