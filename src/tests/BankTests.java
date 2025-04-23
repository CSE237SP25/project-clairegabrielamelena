package tests;
import bankapp.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BankTests {
	
	@Test
	public void testNewBankHasEmptyCustomerList() {
		Bank testBank = new Bank();
		assertEquals(testBank.getAllBankCustomers().size(), 0);
		
	}
	
	@Test
	public void  testAddingCustomerUpdatesList() {
		Bank testBank = new Bank();
		BankCustomer testUser = new BankCustomer("testUser");
		testBank.addBankCustomer(testUser, "");
		assertEquals(testBank.getAllBankCustomers().size(), 1);
	}
	
	@Test
	public void testLogInWithCorrectPassword() {
		Main.mainBank = new Bank();
		BankCustomer customer = new BankCustomer("claire");
		Main.mainBank.addBankCustomer(customer, "pass123");

		
		Main.mainBank.setMainAdminMenu(new AdminMenu());
		Main.mainBank.setMainBankCustomerMenu(new CustomerMenu());

		boolean success = Main.mainBank.logInAsCustomer(1, "pass123");
		assertTrue(success);
		assertEquals(customer, Main.mainBank.getMainBankCustomerMenu().getCurrentCustomer());
	}
	
	@Test
	public void testLogInWithIncorrectPassword() {
		Main.mainBank = new Bank();
		BankCustomer customer = new BankCustomer("claire");
		Main.mainBank.addBankCustomer(customer, "pass123");

		Main.mainBank.setMainAdminMenu(new AdminMenu());
		Main.mainBank.setMainBankCustomerMenu(new CustomerMenu());

		boolean success = Main.mainBank.logInAsCustomer(1, "wrongpassword");

		assertFalse(success);
		assertNull(Main.mainBank.getMainBankCustomerMenu().getCurrentCustomer());
	}
	
	@Test
	public void testLogInWithInvalidCustomerIndex() {
	    Main.mainBank = new Bank();
	    BankCustomer customer = new BankCustomer("claire");
	    Main.mainBank.addBankCustomer(customer, "pass123");

	    Main.mainBank.setMainAdminMenu(new AdminMenu());
	    Main.mainBank.setMainBankCustomerMenu(new CustomerMenu());

	    boolean result = Main.mainBank.logInAsCustomer(0, "pass123");
	    assertFalse(result);

	    result = Main.mainBank.logInAsCustomer(5, "pass123");
	    assertFalse(result);
	}


	

	

}
