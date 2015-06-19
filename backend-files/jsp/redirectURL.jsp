<%@page import="java.util.Set"%>
<%@page import="java.util.Hashtable"%>
<%@page import="javax.crypto.Mac"%>
<%@page import="javax.crypto.spec.SecretKeySpec"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	String secretKey = "MERCHANT_SECRET_KEY";
	Hashtable<String, String> reqValMap = new Hashtable<String, String>(){
		public synchronized String toString() {
			Enumeration<String> keys = keys();
			StringBuffer buff = new StringBuffer("{");
			while(keys.hasMoreElements()){
				String key = keys.nextElement();
				String value = get(key);
				
				buff.append("'").append(key).append("'")
				 	.append(":")
					.append("'").append(value).append("'")
					.append(',');
			}
			buff = new StringBuffer(buff.toString().substring(0, buff.toString().length()-1));
			buff.append("}");
			return buff.toString();
		}
	};
	
	Enumeration<String> parameterList = request.getParameterNames();
	while(parameterList.hasMoreElements()){
		String paramName = parameterList.nextElement();
		String paramValue = request.getParameter(paramName);
		reqValMap.put(paramName, paramValue);
	}
	
	String dataString = new StringBuilder()
							.append(request.getParameter("TxId"))
							.append(request.getParameter("TxStatus"))
							.append(request.getParameter("amount"))
							.append(request.getParameter("pgTxnNo"))
							.append(request.getParameter("issuerRefNo"))
							.append(request.getParameter("authIdCode"))
							.append(request.getParameter("firstName"))
							.append(request.getParameter("lastName"))
							.append(request.getParameter("pgRespCode"))
							.append(request.getParameter("addressZip"))
							.toString();
	SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
	Mac mac = Mac.getInstance("HmacSHA1");
	mac.init(secretKeySpec);
	byte []hmacArr = mac.doFinal(dataString.getBytes());
	StringBuilder build = new StringBuilder();
	for (byte b : hmacArr) {
	    build.append(String.format("%02x", b));
	}
	String hmac = build.toString();
	
	String reqSignature = request.getParameter("signature");
	System.out.println("txn ID : " + request.getParameter("TxId"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Redirect URL</title>
<script type="text/javascript">
	function postResponse(data) {
		document.write(data);
		  CitrusResponse.pgResponse(data);
	}
	document.write(JSON.stringify(<%=reqValMap%>));
	var jsonObject;
	if('<%=hmac%>' === '<%=request.getParameter("signature")%>'){
		jsonObject = JSON.stringify(<%=reqValMap%>);
		postResponse(jsonObject);
		document.write(jsonObject);
	}else{
		var responseObj = {
								"Error" : "Transaction Failed",
								"Reason" : "Signature Verfication Failed"
						  };
		jsonObject = JSON.stringify(responseObj);
		postResponse(jsonObject);
		document.write(jsonObject);
	}
</script>
</head>
<body>

</body>
</html>
