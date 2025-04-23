package bankapp;

import java.util.ArrayList;
import java.util.Optional;


public class BankCustomer {

	private String username;
	private ArrayList<BankAccount> accountList;

	/**
	 * Constructs a new BankCustomer with the given username.
	 *
	 * @param username the username of the customer (must be non-null and non-blank)
	 * @throws IllegalArgumentException if the username is null, empty, or only whitespace
	 */
	public BankCustomer(String username) {
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty or whitespace.");
		}
		this.username = username;
		accountList = new ArrayList<>();
	}

	/**
	 * Opens a new bank account with the given name for the customer.
	 *
	 * @param accountName the desired name of the new account
	 * @return the newly created BankAccount
	 * @throws IllegalArgumentException if the customer already has an account with the given name
	 */
	public BankAccount openAccount(String accountName) {
		Optional<BankAccount> sameName = this.accountList.stream()
				.filter(a -> a.getAccountName().equals(accountName))
				.findAny();
		if (sameName.isPresent()) {
			throw new IllegalArgumentException();
		} else {
			BankAccount account = new BankAccount(accountName);
			accountList.add(account);
			return account;
		}
	}

	/**
	 * Transfers a specified amount of money from one account to another within the customer's accounts.
	 *
	 * @param sourceAccount the account to withdraw from
	 * @param destinationAccount the account to deposit into
	 * @param amount the amount of money to transfer
	 * @throws IllegalArgumentException if the source and destination accounts are the same,
	 *                                  or if withdrawal/deposit fails due to invalid amount
	 */
	public void transferMoney(BankAccount sourceAccount, BankAccount destinationAccount, double amount) {
		if (sourceAccount == destinationAccount) {
			throw new IllegalArgumentException("Source and destination accounts must be different.");
		}

		sourceAccount.withdraw(amount);
		destinationAccount.deposit(amount);
		System.out.println("Transfer successfully completed.\n");
	}

	/**
	 * Renames the customer's username to a new value, provided it does not match any existing account name.
	 *
	 * @param newAccountName the new desired username
	 * @return the updated username
	 * @throws IllegalArgumentException if an account with the new name already exists
	 */
	public String renameAccount(String newAccountName) {
		Optional<BankAccount> sameName = this.accountList.stream()
				.filter(a -> a.getAccountName().equals(newAccountName))
				.findAny();
		if (sameName.isPresent()) {
			throw new IllegalArgumentException();
		} else {
			this.username = newAccountName;
			return this.getUsername();
		}
	}
	
	public ArrayList<BankAccount> getAccountList() {
		return accountList;
	}

	public String getUsername() {
		return username;
	}

}
