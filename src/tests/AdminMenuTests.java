package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import bankapp.*;


public class AdminMenuTests {
	
	@Test
	public void testInvalidMenuSelections() {
	    AdminMenu testMenu = new AdminMenu();
	    int[] invalidInputs = {0, 6, 100};
	    for (int input : invalidInputs) {
	        try {
	            testMenu.processMenuSelection(input);
	            fail("Expected IllegalArgumentException for input: " + input);
	        } catch (IllegalArgumentException e) {
	            assertTrue(e != null);
	        }
	    }
	}

	
	@Test
	public void testNegativeMenuSelection() {
		AdminMenu testMenu = new AdminMenu();
		try {
			testMenu.processMenuSelection(-1);
			fail();
        } catch (IllegalArgumentException e) {
        	assertTrue(e != null);
        }
	}
	
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
	
	@Test(expected = IllegalArgumentException.class)
	public void testSelectCurrentCustomerThrowsWhenUsernameNotFound() {
	    AdminMenu adminMenu = new AdminMenu();
	    ArrayList<BankCustomer> customers = new ArrayList<>();
	    customers.add(new BankCustomer("user1"));
	    customers.add(new BankCustomer("user2"));

	    adminMenu.selectCurrentCustomer("nonexistentUser", customers);
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
	
	@Test(expected = IllegalArgumentException.class)
    public void testDisplayBankAccountListWithoutCustomerThrowsException() {
        AdminMenu adminMenu = new AdminMenu();
        // Attempt to get accounts when no current customer is selected
        // This will call getCurrentBankCustomerBankAccounts() and should throw the exception
        adminMenu.displayBankAccountList(adminMenu.getCurrentBankCustomerBankAccounts());
    }
	
	

    @Test
    public void testUpdateCurrentCustomer() {
        AdminMenu adminMenu = new AdminMenu();

        BankCustomer customer1 = new BankCustomer("claire");
        BankCustomer customer2 = new BankCustomer("testUser");

        ArrayList<BankCustomer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);

        BankCustomer selected = adminMenu.selectCurrentCustomer("testUser", customerList);
        adminMenu.setCurrentCustomer(selected);

        assertEquals("testUser", adminMenu.getCurrentCustomer().getUsername());
    }
	

}
