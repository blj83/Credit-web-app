package creditcard;

import java.util.*;

/**
 * Iteration 3 tracks the unpaid balance as the customer makes purchases. 
 * This iteration also includes a Bean interface.
 * @author Michael Crosby
 * @author Ben Johnson
 */
public class CustomerAccount {
	
	private int custId;
	private String custName;
	private String custAddr;
	private double unpaidBalance;
	private double creditLimit;
	private String imageURL;
	/** default initial credit limit */
	public static final double DEFAULT_CREDIT_LIMIT = 1000.0;
	/** max credit limit */
	public static final double MAX_CREDIT_LIMIT = 10000000.0;
	public String errorMessage = "";
	private ArrayList<Purchase> purchases;
	
	/**
	 * Creates a new customer Account with a null image
	 * @param custId
	 * @param custName
	 * @param custAddr
	 * @param creditLimit
	 */
	public CustomerAccount(int custId, String custName, String custAddr, double creditLimit) {
		this.custId = custId;
		this.custName = custName;
		this.custAddr = custAddr;
		//TODO: enforce non-null name and address
		this.creditLimit = creditLimit;
		this.imageURL=null;
	}
	/**
	 * Creates a customer Account with default credit limit and an image
	 * @param custId
	 * @param custName
	 * @param custAddr
	 * @param imageURL
	 */
	public CustomerAccount(int custId, String custName, String custAddr, String imageURL){
		this(custId, custName, custAddr, DEFAULT_CREDIT_LIMIT);
		this.imageURL=imageURL;
	}
	
	/**
	 * adds Purchase to customer records
	 * @param id
	 * @param merchantName
	 * @param merchantCity
	 * @param merchantState
	 * @param date
	 * @param purchaseAmount
	 * @param custID
	 * @return
	 */
	public boolean addPurchase(int id, String merchantName, String merchantCity, String merchantState, String date, double purchaseAmount, int custID){
		if(purchaseAmount<0){
			setErrorMessage("Purchase amount must be positive");
			return false;
		}
		if((this.getUnpaidBalance()+purchaseAmount)>this.getCreditLimit()){
			setErrorMessage("Amount exceeds your credit limit, sorry");
			return false;
		}
		this.setUnpaidBalance(this.getUnpaidBalance()+purchaseAmount);
		setErrorMessage("");
		Purchase p = new Purchase(id, merchantName, merchantCity, merchantState, date, purchaseAmount, custID);
		this.purchases.add(p);
		return true;
	}
	
	/**
	 * Calls addPurchase method
	 * @param id
	 * @param merchantName
	 * @param merchantCity
	 * @param merchantState
	 * @param purchaseAmount
	 * @param custID
	 */
	public void setPurchaseAmount(int id, String merchantName, String merchantCity, String merchantState, String date, double purchaseAmount, int custID){
		addPurchase(id, merchantName, merchantCity, merchantState, date, purchaseAmount, custID);
	}
	

	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getCustAddr() {
		return custAddr;
	}
	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}
	
	public double getUnpaidBalance() {
		return unpaidBalance;
	}
	public void setUnpaidBalance(double unpaidBalance) {
		this.unpaidBalance = unpaidBalance;
	}
	
	public double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {

		this.imageURL = imageURL;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	
	
	public ArrayList<Purchase> getPurchases() {
		return purchases;
	}
	public void setPurchases(ArrayList<Purchase> list){
		this.purchases=list;
	}

}
