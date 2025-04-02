package tests;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import bankapp.Bank;
import bankapp.BankAccount;
import bankapp.BankCustomer;
import bankapp.Menu;

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
		testBank.addBankCustomer(testUser);
		assertEquals(testBank.getAllBankCustomers().size(), 1);
	}
	

}
