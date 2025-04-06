package tests;

import bankapp.Menu;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MenuTests {
	
	@Test
	public void testCustomerBankAccountCreation() {
		
		Menu testMenu = new Menu();
		testMenu.createCustomerUser("testUser");
		testMenu.createBankAccount("name");
		assertEquals(1, testMenu.getBankAccountList().size());
		
	}
	@Test
	public void testInvalidMenuSelection() {
		Menu testMenu = new Menu();
		try {
			testMenu.processMenuSelection(100);
			fail();
        } catch (IllegalArgumentException e) {
        	assertTrue(e != null);
        }
	}
	
	@Test
	public void testNegativeMenuSelection() {
		Menu testMenu = new Menu();
		try {
			testMenu.processMenuSelection(-1);
			fail();
        } catch (IllegalArgumentException e) {
        	assertTrue(e != null);
        }
	}
	
	@Test
	public void testInvalidAccountModification() {
		Menu testMenu = new Menu();
		try {
			testMenu.processAccountModification(50); 
			fail();
        } catch (IllegalArgumentException e) {
        	assertTrue(e != null);
        }
	}
	
	public void testGetCurrentCustomerEmpty() {
		Menu testMenu = new Menu();
		assertTrue(testMenu.getCurrentCustomer()==null);		
	}
	
	@Test
	public void testGetCurrentCustomerNotEmpty() {
		Menu testMenu = new Menu();
		testMenu.createCustomerUser("testUser");
		assertTrue(testMenu.getCurrentCustomer() != null);
		
	}


	

}

