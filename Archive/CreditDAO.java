package creditcard;

import java.sql.*;
import java.util.ArrayList;


/**
 * Handles database access for the Credit Card project
 *
 */
public class CreditDAO {

	private static final String  JDBC_URL = "jdbc:mysql://localhost/csci4300";
	private String custName;
	private String custAddr;
	private String imageURL;
	private double creditLimit;
	private int custId;
	private String merchantName, merchantCity, merchantState;
	private double purchaseAmount, unpaidBalance;
	
	/**
	 * Statement to add a customer account to the database
	 */
	private PreparedStatement addCustomerStatement;
	/**
	 * State to list all customer accounts
	 */
	private PreparedStatement listCustomerAccountsStatement;
	/**
	 * Statement to retrieve a customer account of a particular account
	 */
	private PreparedStatement getCustomerAccount;
	/**
	 * Statement to list purchases of a certain Customer
	 */
	private PreparedStatement listPurchases;
	/**
	 * Statement to add a purchase of a customer
	 */
	private PreparedStatement addPurchase;
	/**
	 * Statement to update the unpaidBalance of a customer
	 */
	private PreparedStatement updateUnpaidBalanceStatement;
	/**
	 * Connect to DB and create PreparedStatements
	 */
	public CreditDAO(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, "root", "webPro");
			addCustomerStatement=conn.prepareStatement("insert into Customer (custName, custAddr, creditLimit, imageURL, unpaidBalance) values (?,?,?,?,?)");
			listCustomerAccountsStatement=conn.prepareStatement("select id, custName, custAddr, creditLimit, unpaidBalance, imageURL from Customer");
			getCustomerAccount=conn.prepareStatement("select * from Customer where id = ?");
			listPurchases=conn.prepareStatement("select * from Purchase where custId= ?");
			addPurchase=conn.prepareStatement("insert into Purchase (merchantName, merchantCity, merchantState, purchaseDate, purchaseAmount, custId) values (?,?,?,now(),?,?)");
			updateUnpaidBalanceStatement=conn.prepareStatement("update Customer set unpaidBalance = ? where id = ?");
			System.out.println("Connected to csci4300 database");
			
		} catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * Adds a new record to the Customer table
	 */
	public void addCustomerAccount(String custName, String custAddr, double creditLimit, String imageURL){
		try{
			addCustomerStatement.setString(1, custName);
			addCustomerStatement.setString(2, custAddr);
			addCustomerStatement.setDouble(3, creditLimit);
			addCustomerStatement.setString(4, imageURL);
			addCustomerStatement.setDouble(5, 0.0);
			addCustomerStatement.executeUpdate();
		}catch(Exception e){
			System.out.println("Error adding customer to Customer table\n " + e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Returns a list of all Customers in the Customer table, but without embedded Purchases
	 */
	public ArrayList<CustomerAccount> getCustomerList(){
		ArrayList<CustomerAccount> list = new ArrayList<CustomerAccount>();
		String custName, custAddr, imageURL;
		int id;
		double creditLimit, unpaidBalance;
		try{
			ResultSet set = listCustomerAccountsStatement.executeQuery();
			while(set.next()){
				id=set.getInt("id");
				custName=set.getString("custName");
				custAddr=set.getString("custAddr");
				unpaidBalance = set.getDouble("unpaidBalance");
				creditLimit=set.getDouble("creditLimit");
				imageURL=set.getString("imageURL");
				CustomerAccount c = new CustomerAccount(id, custName, custAddr,creditLimit);
				c.setUnpaidBalance(unpaidBalance);
				c.setImageURL(imageURL);
				list.add(c);
			}
		}catch(Exception e){
			System.out.println("Error getting customer List\n " + e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Returns the Customer with account no 'id', including the list
	 * of Purchases
	 */
	public CustomerAccount getCustomerAccount(int id){
		String custName, custAddr, imageURL;
		double unpaidBalance, creditLimit;
		ArrayList<Purchase> list= null;
		CustomerAccount c = null;
		try{
			getCustomerAccount.setInt(1, id);
			ResultSet set = getCustomerAccount.executeQuery();
			set.next();
			custName = set.getString("custName");
			custAddr = set.getString("custAddr");
			imageURL=set.getString("imageURL");
			unpaidBalance = set.getDouble("unpaidBalance");
			creditLimit=set.getDouble("creditLimit");
			list=getPurchases(id);
			c = new CustomerAccount(id, custName, custAddr, creditLimit);
			c.setPurchases(list);
			c.setUnpaidBalance(unpaidBalance);
			c.setImageURL(imageURL);
		}catch (Exception e){
			System.out.println("Error getting customer account\n " + e.getClass().getName() + ": " + e.getMessage());
		}
		return c;
	}
	
	/**
	 * Adds a Purchase to 'account' with purchase date the present moment.
	 * If the purchase would put 'account' over its credit limit, does not
	 * add the Purchase but sets the error message of 'account'.
	 */
	public CustomerAccount addPurchase (String merchantName, String merchantCity, String merchantState, double purchaseAmount){
		double unpaidBalance;
		
		CustomerAccount c = getCustomerAccount(this.custId);
		unpaidBalance=c.getUnpaidBalance();
		if(purchaseAmount<0){
			c.setErrorMessage("Purchase Amount must be greater than 0");
			return c;
		}
		if((purchaseAmount+unpaidBalance)>c.getCreditLimit()){
			c.setErrorMessage("Purchase amount exceeds your credit limit");
			return c;
		}	
		
		try{			
			addPurchase.setString(1,merchantName);
			addPurchase.setString(2,merchantCity);
			addPurchase.setString(3,merchantState);
			addPurchase.setDouble(4,purchaseAmount);
			addPurchase.setInt(5, this.custId);
			addPurchase.executeUpdate();
			System.out.println("ADDED A PURCHASE");
		} catch (Exception e){
			System.out.println("Error adding purchase \n " + e.getClass().getName() + ": " + e.getMessage());
		}
		
		unpaidBalance+=purchaseAmount;
		try{
			updateUnpaidBalanceStatement.setDouble(1, unpaidBalance);
			updateUnpaidBalanceStatement.setInt(2, this.custId);
			updateUnpaidBalanceStatement.executeUpdate();
		} catch (Exception e){
			System.out.println("Error updating unpaidBalance in customer account\n " + e.getClass().getName() + ": " + e.getMessage());
		}
		c.setErrorMessage("");
		return c;
	}
	
	public ArrayList<Purchase> getPurchases(int id){
		ArrayList<Purchase> list = new ArrayList<Purchase>();
		int purchaseId;
		String merchantName, merchantCity, merchantState, date;
		double purchaseAmount;
		
		try{
			listPurchases.setInt(1, id);
			ResultSet set = listPurchases.executeQuery();
			while(set.next()){
				merchantName=set.getString("merchantName");
				merchantCity=set.getString("merchantCity");
				merchantState=set.getString("merchantState");
				purchaseId = set.getInt("id");
				purchaseAmount=set.getDouble("purchaseAmount");
				date = set.getDate("purchaseDate").toString();
				Purchase p = new Purchase(purchaseId, merchantName, merchantCity, merchantState, date, purchaseAmount, custId);
				list.add(p);
			}
		} catch (Exception e){
			System.out.println("Error getting purchases \n " + e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	public void setCustId(int custId){
		this.custId=custId;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	
	public void setImageURL(String imageURL){
		addCustomerAccount(this.custName,this.custAddr, this.creditLimit, imageURL);
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}

	public void setMerchantState(String merchantState) {
		this.merchantState = merchantState;
	}
	
	public void setPurchaseAmount(double purchaseAmount) {
		//addPurchase(this.merchantName, this.merchantCity, this.merchantState, purchaseAmount);
		this.purchaseAmount=purchaseAmount;
	}
	
}