package creditcard;

import java.util.*;

public class Purchase {
	private int id;
	private String merchantName;
	private String merchantCity;
	private String merchantState;
	private String date;
	private double purchaseAmount;
	private int custID;
	
	/**
	 * Creates a new Purchase object
	 * @param id
	 * @param merchantName
	 * @param merchantCity
	 * @param merchantState
	 * @param date
	 * @param purchaseAmount
	 * @param custID
	 */
	public Purchase(int id, String merchantName, String merchantCity, String merchantState, String date, double purchaseAmount,int custID) {
		this.id = id;
		this.merchantName = merchantName;
		this.merchantCity = merchantCity;
		this.merchantState = merchantState;
		this.date=date;
		this.purchaseAmount = purchaseAmount;
		this.custID = custID;
	}
	
	public int getId() {
		return id;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public String getMerchantCity() {
		return merchantCity;
	}
	public String getMerchantState() {
		return merchantState;
	}
	public double getPurchaseAmount() {
		return purchaseAmount;
	}
	public int getCustID() {
		return custID;
	}
	public String getDate(){
		return this.date;
	}
	
	
}
