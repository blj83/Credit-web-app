package creditcard;





import org.junit.Test;

import junit.framework.TestCase;

public class PurchaseTester extends TestCase {

	@Test
	public void testConstructor() {
		Purchase instance1 = new Purchase(1, "Target", "Athens", "GA", "Today", 10.00,1);
		assertEquals("Purchase id is: " ,1, instance1.getId());
		assertEquals("Purchase merchant is: " ,"Target", instance1.getMerchantName());
		assertEquals("Purchase city is: " ,"Athens", instance1.getMerchantCity());
		assertEquals("Purchase state is: " ,"GA", instance1.getMerchantState());
		assertEquals("Purchase amount is: " ,10.00, instance1.getPurchaseAmount());
		assertEquals("Purchase customer id is: " ,1, instance1.getCustID());
		
		Purchase instance2 = new Purchase(2, "Kroger", "Atlanta", "GA", "Yesterday", 20.00,1);
		assertEquals("Purchase id is: " ,2, instance2.getId());
		assertEquals("Purchase merchant is: " ,"Kroger", instance2.getMerchantName());
		assertEquals("Purchase city is: " ,"Atlanta", instance2.getMerchantCity());
		assertEquals("Purchase state is: " ,"GA", instance2.getMerchantState());
		assertEquals("Purchase amount is: " ,20.00, instance2.getPurchaseAmount());
		assertEquals("Purchase customer id is: " ,1, instance2.getCustID());
	}

}
