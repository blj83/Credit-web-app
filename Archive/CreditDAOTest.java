package creditcard;


import java.util.ArrayList;

import org.junit.Test;

import junit.framework.TestCase;
/**
 * Tests the CreditDAO class, which manages DB
 * access for the Credit Card project
 * @author drdan
 *
 */
public class CreditDAOTest extends TestCase {

	@Test
	/**
	 * Tests that an object can be created
	 */
	public void testConstructor() {
		CreditDAO instance = new CreditDAO();
		assertNotNull("newly created CreditDAO", instance);
	}
	
	/**
	 * Tests creating accounts
	 */
	@Test
	public void testCreate(){
		CreditDAO instance = new CreditDAO();
		//add first customer
		instance.addCustomerAccount("Gordon Gecko", "1 Wall St, NYC",1000.00,null);
		instance.setCustId(1);
		instance.addPurchase("target", "atlanta", "ga", 20.00);
		instance.addPurchase( "bojangles", "somehwereville", "nc", 90.00);
		ArrayList<CustomerAccount> acctList = instance.getCustomerList();
		assertEquals("number of customers after one addition",1,acctList.size());
		CustomerAccount acct0 = acctList.get(0);
		assertEquals("First cust name after one addition", "Gordon Gecko",acct0.getCustName());
		assertEquals("First cust address after one addition","1 Wall St, NYC",acct0.getCustAddr());
		assertEquals("First cust image url after one addition",null,acct0.getImageURL());
		assertEquals("First cust unpaid balance after one addition",110.0,acct0.getUnpaidBalance(),0.001);
		assertEquals("First cust credit limit after one addition", 1000.0,acct0.getCreditLimit(),0.001);
		
		//add second customer
		instance.addCustomerAccount("Fred Freekowtski", "22 Slum St, Oakland CA",1000.00,"images/I/61zenAO2uOL._SL500_SS500_.jpg");
		instance.setCustId(2);
		instance.addPurchase("kroger", "athens", "ga", 40.00);
		acctList = instance.getCustomerList();
		assertEquals("number of customers after two additions",2,acctList.size());
	    acct0 = acctList.get(1);
		assertEquals("2nd cust name ", "Fred Freekowtski",acct0.getCustName());
		assertEquals("2nd cust address ","22 Slum St, Oakland CA",acct0.getCustAddr());
		assertEquals("2nd cust image url ","images/I/61zenAO2uOL._SL500_SS500_.jpg",acct0.getImageURL());
		assertEquals("2nd cust unpaid balance ",40.0,acct0.getUnpaidBalance(),0.001);
		assertEquals("2nd cust credit limit ",1000.0,acct0.getCreditLimit(),0.001);
		assertEquals("number of customers ",2,acctList.size());
	
		
	}

}