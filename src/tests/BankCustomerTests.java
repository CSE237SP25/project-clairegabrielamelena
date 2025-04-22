package tests;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import bankapp.BankAccount;
import bankapp.BankCustomer;

public class BankCustomerTests {

	@Test
	public void testBankCustomerAccountCreationUsername() {
		BankCustomer testUser = new BankCustomer("testUser");
		String username = testUser.getUsername();
		assertEquals(username, "testUser");

	}

	@Test
	public void testBankCustomerAccountCreationAccountList() {
		BankCustomer testUser = new BankCustomer("testUser");
		ArrayList<BankAccount> emptyAccountList = new ArrayList<BankAccount>();
		assertEquals(emptyAccountList, testUser.getAccountList());

	}

	@Test
	public void testRenameAccount() {
		BankCustomer testUser = new BankCustomer("testUser");
		testUser.renameAccount("newName"); 
		assertEquals("newName", testUser.getUsername());
	}
	
	@Test
	public void testCustomerBankAccountCreation() {
		BankCustomer testUser = new BankCustomer("testUser");
		BankAccount testBankAccount = testUser.openAccount("testing");

		assertEquals(testUser.getAccountList().size(), 1);
		assertEquals(testBankAccount.getCurrentBalance(), 0, 0.01);
	}

	@Test
	public void testTransferMoney() {
		BankCustomer testUser = new BankCustomer("testUser");
		BankAccount testBankAccount1 = testUser.openAccount("testing1");
		BankAccount testBankAccount2 = testUser.openAccount("testing2");
		
		testBankAccount1.deposit(100); 
		testUser.transferMoney(testBankAccount1, testBankAccount2, 100);
		assertEquals(testUser.getAccountList().size(), 2);
		assertEquals(testBankAccount1.getCurrentBalance(), 0, 0) ; 
	}
	
	@Test
	public void testTransferAccountSelections() {
		BankCustomer testUser = new BankCustomer("testUser");
		BankAccount testBankAccount1 = testUser.openAccount("testing1");

		assertThrows(IllegalArgumentException.class, () -> {
			testUser.transferMoney(testBankAccount1, testBankAccount1, 100);
		});
		
	}
	
	@Test
	public void testNoRepeatBankAccountName() {
		BankCustomer testUser = new BankCustomer("testUser");
		BankAccount testBankAccount1 = testUser.openAccount("testing1");

		assertThrows(IllegalArgumentException.class, () -> {
			testUser.openAccount("testing1");
		});
		
	}
	
	@Test
	public void testAllowsNonRepeatBankAccountName() {
		BankCustomer testUser = new BankCustomer("testUser");
		BankAccount testBankAccount1 = testUser.openAccount("testing1");
		BankAccount testBankAccount2 = testUser.openAccount("differentName");
		
		assertEquals(testUser.getAccountList().size(), 2);
		
	}
	
	@Test
	public void testTransferMoneyInsufficientFunds() {
	    BankCustomer testUser = new BankCustomer("testUser");
	    BankAccount testBankAccount1 = testUser.openAccount("checking");
	    BankAccount testBankAccount2 = testUser.openAccount("savings");

	    testBankAccount1.deposit(50); //Not enough for the transfer attempt

	    assertThrows(IllegalArgumentException.class, () -> {
	        testUser.transferMoney(testBankAccount1, testBankAccount2, 100);
	    });

	    //balances should remain unchanged
	    assertEquals(50.0, testBankAccount1.getCurrentBalance(), 0.001);
	    assertEquals(0.0, testBankAccount2.getCurrentBalance(), 0.001);
	}
	
	@Test
	public void testNullUsernameThrowsException() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new BankCustomer(null);
	    });
	}

	@Test
	public void testEmptyUsernameThrowsException() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new BankCustomer("");
	    });
	}

	@Test
	public void testWhitespaceUsernameThrowsException() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new BankCustomer("   ");
	    });
	}

	
	
}
