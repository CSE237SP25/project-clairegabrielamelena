package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import bankapp.*;


public class AdminMenuTests {
	


	//create test to test that if display bank account list is called without a currentCustomer selected
	//create test to update currentCustomer
	
	@Test
	public void testSelectCurrentCustomerOnEmptyList() {
		
		AdminMenu testAdminMenu = new AdminMenu();
		ArrayList<BankCustomer> testCustomerList = new ArrayList<BankCustomer>();
		try {
			BankCustomer test = testAdminMenu.selectCurrentCustomer("non_existing_account", testCustomerList);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
		
	}
	@Test
	public void testSelectCurrentCustomerOnNonExistentUsername() {
		
		AdminMenu testAdminMenu = new AdminMenu();
		ArrayList<BankCustomer> testCustomerList = new ArrayList<BankCustomer>();
		BankCustomer customerA = new BankCustomer("customerA");
		try {
			BankCustomer test = testAdminMenu.selectCurrentCustomer("non_existing_account", testCustomerList);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}

	}
	
	@Test
	public void testSelectCurrentCustomerOnValidUsername() {
		
		AdminMenu testAdminMenu = new AdminMenu();
		ArrayList<BankCustomer> testCustomerList = new ArrayList<BankCustomer>();
		BankCustomer customerA = new BankCustomer("customerA");
		testCustomerList.add(customerA);
		
		BankCustomer test = testAdminMenu.selectCurrentCustomer("customerA", testCustomerList);
		assertEquals(customerA, test);
		
	}
	
	@Test
	public void testGetCurrentCustomerBankAccountsNullCustomer() {
		
		AdminMenu testAdminMenu = new AdminMenu();
		try {
			testAdminMenu.getCurrentBankCustomerBankAccounts();
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
		
	}
	
	

}
