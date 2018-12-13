

<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

</head>
<body>

	
	<% String ruta2 = System.getenv("DOMAIN_HOME") + File.separator + "servers" 
			+ File.separator + "AdminServer" + File.separator + "upload" 
			+ File.separator + "CitaMedica.war" + File.separator + "app" 
			+ File.separator + "CitaMedica" + File.separator + "WebContent" + File.separator + "Reporte" + File.separator; %>
	<img alt="" src= <%=ruta2 + "logo-login.png"%> >
</body>
</html>