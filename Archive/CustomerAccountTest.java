package creditcard;


import java.util.ArrayList;

import org.junit.Test;

import junit.framework.TestCase;

public class CustomerAccountTest extends TestCase {

	/**
	 * Verifies that a newly created CustomerAccount contains the supplied initial data
	 */
	@Test
	public void testConstructor() {
		CustomerAccount instance1 = new CustomerAccount(1, "Gordon Gecko", "1 Wall Street, NYC", 10000000.00);
		assertEquals("instance 1 account no",1,instance1.getCustId());
		assertEquals("instance 1 customer name","Gordon Gecko",instance1.getCustName());
		assertEquals("instance 1 customer address", "1 Wall Street, NYC", instance1.getCustAddr());
		assertEquals("instance 1 Image URL", null, instance1.getImageURL());
		assertEquals("instance 1 unpaid balance", 0.0, instance1.getUnpaidBalance(),0.001);
		assertEquals("instance 1 credit limit", 10000000.00, instance1.getCreditLimit(), 0.001);
		
		CustomerAccount instance2 = new CustomerAccount(2, "Dan Everett", "Computer Science Department, UGA", 50.00);
		assertEquals("instance 2 account no",2,instance2.getCustId());
		assertEquals("instance 2 customer name","Dan Everett",instance2.getCustName());
		assertEquals("instance 2 customer address", "Computer Science Department, UGA", instance2.getCustAddr());
		assertEquals("instance 2 Image URL", null, instance2.getImageURL());
		assertEquals("instance 2 unpaid balance", 0.0, instance2.getUnpaidBalance(),0.001);
		assertEquals("instance 2 credit limit", 50.00, instance2.getCreditLimit(), 0.001);
		
		CustomerAccount instance3 = new CustomerAccount(3, "Fred Flinstone", "Bedrock" , 1000.00);
		assertEquals("instance 3 account no",3,instance3.getCustId());
		assertEquals("instance 3 customer name","Fred Flinstone",instance3.getCustName());
		assertEquals("instance 3 customer address", "Bedrock", instance3.getCustAddr());
		assertEquals("instance 3 Image URL", null, instance3.getImageURL());
		assertEquals("instance 3 unpaid balance", 0.0, instance3.getUnpaidBalance(),0.001);
		assertEquals("instance 3 credit limit", 1000.00, instance3.getCreditLimit(), 0.001);
	}
	
	/**
	  * Test that adding a purchase increases the unpaid balance, UNLESS the purchase
	  * would put the unpaid balance above the credit limit. Also test the error conditions
	  */
	
	 @Test
	  public void testAddPurchase() {
		  CustomerAccount instance = new CustomerAccount (2, "Fred Freekowtski", "22 Slum St", 1000.00);
		  instance.setPurchases(new ArrayList<Purchase>());
		  boolean addPurchaseResult1 = instance.addPurchase(1, "Target", "Athens", "GA","Today",10.00,2);
		  assertEquals("zero purchase result", true, addPurchaseResult1);
		  assertEquals("zero purchase unpaid balance",10.00, instance.getUnpaidBalance(),0.001);
		  assertEquals("zero purchase error message","", instance.getErrorMessage());
		  assertEquals("Purchase list size is: ",1,instance.getPurchases().size());
		  
		 
		/*
		  Add this later when we can handle Exceptions
		 
		  boolean addPurchaseResult3 = instance.addPurchase("$25.00");
		  assertEquals("non-numeric purchase result", false, addPurchaseResult3);
		  assertEquals("non-numeric purchase unpaid balance",0.00, instance.getUnpaidBalance(),0.001);
		  assertEquals("non-numeric purchase error message","Purchase amount must be numeric", instance.getErrorMessage());
		 */ 
		  boolean addPurchaseResult4 = instance.addPurchase(2, "Target", "Athens", "GA", "Today", -25.0,2);
		  assertEquals("negative purchase result", false, addPurchaseResult4);
		  assertEquals("negative purchase unpaid balance",10.00, instance.getUnpaidBalance(),0.001);
		  assertEquals("negative purchase error message","Purchase amount must be positive", instance.getErrorMessage());
		  assertEquals("Purchase list size is: ",1,instance.getPurchases().size());
	
		  boolean addPurchaseResult5 = instance.addPurchase(3, "Target", "Athens", "GA", "Tomorrow", 300,2);
		  assertEquals("first purchase result", true, addPurchaseResult5);
		  assertEquals("first purchase unpaid balance",310.00, instance.getUnpaidBalance());
		  assertEquals("first purchase error message","", instance.getErrorMessage());
		  assertEquals("Purchase list size is: ",2,instance.getPurchases().size());
		  
		  boolean addPurchaseResult6 = instance.addPurchase(4, "Target", "Athens", "GA", "Maybe a week ago",500,2);
		  assertEquals("second purchase result", true, addPurchaseResult6);
		  assertEquals("second purchase unpaid balance",810.00, instance.getUnpaidBalance());
		  assertEquals("second purchase error message","", instance.getErrorMessage());
		  assertEquals("Purchase list size is: ",3,instance.getPurchases().size());
		  
		  boolean addPurchaseResult7 = instance.addPurchase(5, "Target", "Athens", "GA", "Whenever", 300,2);
		  assertEquals("third purchase result", false, addPurchaseResult7);
		  assertEquals("third purchase unpaid balance",810.00, instance.getUnpaidBalance());
		  assertEquals("third purchase error message","Amount exceeds your credit limit, sorry", instance.getErrorMessage());
		  assertEquals("Purchase list size is: ",3,instance.getPurchases().size());
	  }
	 
	 @Test
	  public void testSetPurchaseAmount() {
		  CustomerAccount instance = new CustomerAccount (2, "Fred Freekowtski", "22 Slum St" , null);
		  instance.setPurchases(new ArrayList<Purchase>());
		  instance.setPurchaseAmount(2, "Target", "Athens", "GA", "Today",0.0,2);
		  assertEquals("zero purchase unpaid balance",0.00, instance.getUnpaidBalance(),0.001);
		  assertEquals("zero purchase error message","", instance.getErrorMessage());
		 
		/*
		  Add this later when we can handle Exceptions
		 
		  boolean addPurchaseResult3 = instance.addPurchase("$25.00");
		  assertEquals("non-numeric purchase result", false, addPurchaseResult3);
		  assertEquals("non-numeric purchase unpaid balance",0.00, instance.getUnpaidBalance(),0.001);
		  assertEquals("non-numeric purchase error message","Purchase amount must be numeric", instance.getErrorMessage());
		 */ 
		  instance.setPurchaseAmount(2, "Target", "Athens", "GA", "tomorrow",-25.0,2);
		  assertEquals("negative purchase unpaid balance",0.00, instance.getUnpaidBalance(),0.001);
		  assertEquals("negative purchase error message","Purchase amount must be positive", instance.getErrorMessage());
	
		  instance.setPurchaseAmount(3, "Target", "Athens", "GA", "I dont know",300.0,2);
		  assertEquals("first purchase unpaid balance",300.00, instance.getUnpaidBalance());
		  assertEquals("first purchase error message","", instance.getErrorMessage());
		  
		  instance.setPurchaseAmount(4, "Target", "Athens", "GA", "Maybe Another Day",500.0,2);
		  assertEquals("second purchase unpaid balance",800.00, instance.getUnpaidBalance());
		  assertEquals("second purchase error message","", instance.getErrorMessage());
		  
		  instance.setPurchaseAmount(5, "Target", "Athens", "GA", "Over the weekend",300.0,2);
		  assertEquals("third purchase unpaid balance",800.00, instance.getUnpaidBalance());
		  assertEquals("third purchase error message","Amount exceeds your credit limit, sorry", instance.getErrorMessage());
	  }

}
