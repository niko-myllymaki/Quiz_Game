<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page for Quiz Game</title>
</head>

<body>
<h1>Welcome to login page of Quiz Game!</h1>
<p>Please login first to start playing</p>
<p>If you don't have a user press the "Create user" button.</p>

  <c:if test="${not empty error }">
        <p class="text-danger">${error} </p>
    </c:if>

 <form action="LoginPageServlet" method="post">
   Username: <input type="text" class="form-control" id="username" name="username" value="${param.username}">
   <br>
   Password: <input type="password" class="form-control" id="password" name="password" value="${param.password}">
   <button type="submit" class="btn btn-default">Submit</button>
 </form>
 
 <br>
 
  <form action="RegistrationPageServlet">
   <button type="submit" class="btn btn-default">Create user</button>
 </form>
  
</body>

</html>