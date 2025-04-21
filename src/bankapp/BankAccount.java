package bankapp;

public class BankAccount {

	private double balance;
	private String accountName;
	
	public BankAccount(String name) {
		this.balance = 0;
		accountName = name;
	}

	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}

	//Claire
	public void withdraw(double amount) {
		if(amount > balance || amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance -= amount;
	}

	public double getCurrentBalance() {
		return this.balance;
	}

	public void displayBankAccount() {
		System.out.println("Bank Account Name: " + this.accountName);
		System.out.println("Balance: $" + this.balance);
	}

	
	public String getAccountName() {
		return this.accountName;
	}

}