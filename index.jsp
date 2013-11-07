<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" import="creditcard.CustomerAccount"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Customer Account</title>
</head>
<body>
	<% 
	CustomerAccount instance = new CustomerAccount("Gordon Gecko", "1 Wall Street, NYC", 1.0, 10000000.0);
	instance.setImageUrl("http://cedricmnich.files.wordpress.com/2007/11/gordongecko.gif");
	%>
	<h1>E-Z Credit, Inc.</h1>
	<img src="<%= instance.getImageUrl() %>" style="float:right" width="226" height="284">
	<p>Valued Customer: <%= instance.getCustName() %></p>
	<p>Address: <%= instance.getCustAddress() %> </p>
	<%-- TODO: format money amounts for unpaid balance and credit limit. --%>
	<p>Unpaid Balance: <%= instance.getUnpaidBalance() %></p>
	<p>Credit Limit: <%= instance.getCreditLimit() %></p>
	<p>Gordon Gecko Image: cedricmnich.wordpress.com</p>
	
</body>
</html>