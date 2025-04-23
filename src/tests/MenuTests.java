package tests;

import bankapp.Bank;
import bankapp.Main;
import bankapp.CustomerMenu;
import bankapp.BankCustomer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import org.junit.jupiter.api.Test;

public class MenuTests {
	
	
	@Test
	public void testInvalidMenuSelections() {
	    CustomerMenu testMenu = new CustomerMenu();
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
		CustomerMenu testMenu = new CustomerMenu();
		try {
			testMenu.processMenuSelection(-1);
			fail();
        } catch (IllegalArgumentException e) {
        	assertTrue(e != null);
        }
	}
	
	@Test
	public void testInvalidAccountModification() {
		CustomerMenu testMenu = new CustomerMenu();
		int[] invalidInputs = {0, 6, 100, -1};
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
	public void testGetCurrentCustomerEmpty() {
		CustomerMenu testMenu = new CustomerMenu();
		assertTrue(testMenu.getCurrentCustomer()==null);		
	}
	
	@Test
	public void testGetCurrentCustomerNotEmpty() {
		Main.mainBank = new Bank();
		CustomerMenu testMenu = new CustomerMenu();
		testMenu.createCustomerUserInterface("testUser", "");
		assertTrue(testMenu.getCurrentCustomer() != null);
	}
	

    @Test
    public void testIsNameInUse() {
        Bank testBank = new Bank();
        BankCustomer testCustomer = new BankCustomer("existingUser");
        testBank.addBankCustomer(testCustomer, "password123");

        Main.mainBank = testBank;
        CustomerMenu testMenu = new CustomerMenu();

        assertFalse(testMenu.isNameInUse("newUser"));
        assertTrue(testMenu.isNameInUse("existingUser"));
    }
    
    @Test
    public void testCreateCustomerUserInterfaceRejectsDuplicateUsername() {
        Bank testBank = new Bank();
        BankCustomer customer = new BankCustomer("claire");
        testBank.addBankCustomer(customer, "password");

        Main.mainBank = testBank;

        CustomerMenu testMenu = new CustomerMenu();

        assertThrows(IllegalArgumentException.class, () -> {
            testMenu.createCustomerUserInterface("claire", "anotherpass");
        });
    }

}


	


