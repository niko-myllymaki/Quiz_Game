<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration for Quiz Game</title>
</head>
<body>
<h1>Welcome to the registration page of Quiz Game!</h1>
<p>Please register your user first to start playing.</p>
<p>If you already have a user press the "Login" button.</p>

  <c:if test="${not empty error }">
    <p class="text-danger">${error} </p>
  </c:if>
  
 <form action="RegistrationPageServlet" method="post">
   Username: <input type="text" class="form-control" id="username" name="username" value="${param.username}">
   <br>
   Password: <input type="password" class="form-control" id="password" name="password" value="${param.password}">
   <button type="submit" class="btn btn-default">Submit</button>
 </form>
 
 <br>
 
 <form action="LoginPageServlet">
   <button type="submit" class="btn btn-default">Login</button>
 </form>
 

</body>
</html>