package bankapp;

public class BankAccount {

	private double balance;
	private String accountName;
	
	public BankAccount(String accountName) {
		this.balance = 0;
		this.accountName = accountName;
	}
	
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}
	
	//Claire
	public void withdraw(double amount) {
		//TODO withdraw
	}
	
	public double getCurrentBalance() {
		return this.balance;
	}
}
