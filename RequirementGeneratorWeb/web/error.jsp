<%-- 
    Document   : error
    Created on : Sep 1, 2014, 2:43:43 PM
    Author     : alexandre
--%>

<%@ page isErrorPage="true" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Erro</title>;
    </head>
    <body bgcolor="#E6E6FA">
        
       ERROR! NO FILE WAS SELECTED! <br/>
       Error code: ${pageContext.errorData.statusCode}
       
    </body>
</html>
