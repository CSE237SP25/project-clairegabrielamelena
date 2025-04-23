package bankapp;


public class BankAccount {

	private double balance;
	private String accountName;

	/**
	 * Constructs a new BankAccount with a given name and an initial balance of $0.
	 *
	 * @param name the name of the account
	 */
	public BankAccount(String name) {
		this.balance = 0;
		accountName = name;
	}

	/**
	 * Deposits the specified amount into the account.
	 *
	 * @param amount the amount to deposit (must be non-negative)
	 * @throws IllegalArgumentException if the amount is negative
	 */
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}

	/**
	 * Withdraws the specified amount from the account.
	 *
	 * @param amount the amount to withdraw (must be non-negative and not exceed current balance)
	 * @throws IllegalArgumentException if the amount is negative or greater than the current balance
	 */
	public void withdraw(double amount) {
		if(amount > balance || amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance -= amount;
	}

	/**
	 * Prints the account name and current balance to the console.
	 */
	public void displayBankAccount() {
		System.out.println("Bank Account Name: " + this.accountName);
		System.out.println("Balance: $" + this.balance);
	}

	public String getAccountName() {
		return this.accountName;
	}

	public double getCurrentBalance() {
		return this.balance;
	}
}
