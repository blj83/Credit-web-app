package creditcard;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreditController
 */
@WebServlet("/CreditController")
public class CreditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreditController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher;
		String custId=request.getParameter("custId");
		CreditDAO access = new CreditDAO(); 
		
		if(custId!=null){
			CustomerAccount c = access.getCustomerAccount(Integer.parseInt(custId));
			request.setAttribute("customer", c);
			dispatcher = ctx.getRequestDispatcher("/customer.jsp");
			dispatcher.forward(request, response);
		}
		//when you click the a in the customer list, send to customer.jsp
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher;
		CreditDAO access = new CreditDAO(); 
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		String custName = request.getParameter("custName");
		String custAddr = request.getParameter("custAddr");
		double creditLimit = -5;
		String imageURL = request.getParameter("imageURL");
		
		if((custName!=null) && (custAddr!=null)){
			if((request.getParameter("creditLimit")==null)){
				creditLimit=CustomerAccount.DEFAULT_CREDIT_LIMIT;
			} else{
				creditLimit=Double.parseDouble(request.getParameter("creditLimit"));
			}
			
			dispatcher = ctx.getRequestDispatcher("/customerList.jsp");
			dispatcher.forward(request, response);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		String merchantName = request.getParameter("merchantName");
		String merchantCity = request.getParameter("merchantCity");
		String merchantState = request.getParameter("merchantState");
		double purchaseAmount = -5;
		
		
		
		if((merchantName != null) && (merchantCity!=null) && (merchantState!=null)){
			if((request.getParameter("purchaseAmount")==null)){
				//error message stuff here
			} else{
				purchaseAmount=Double.parseDouble(request.getParameter("purchaseAmount"));
			}
			access.setCustId(Integer.parseInt(request.getParameter("custId")));
			access.addPurchase(merchantName, merchantCity, merchantState, purchaseAmount);
			CustomerAccount c = access.getCustomerAccount(Integer.parseInt(request.getParameter("custId")));
			request.setAttribute("customer", c);
			dispatcher = ctx.getRequestDispatcher("/customer.jsp");
			dispatcher.forward(request, response);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		//form for adding Customer: check all but image URL filled in (maybe not credit limit?) call correct constructor
		//form for adding Purchase: check all areas filled out, add purchase to database and send to customer.jsp
	}

}
