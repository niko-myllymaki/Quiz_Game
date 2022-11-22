<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>

<body>
<h1>Welcome to Quiz Game!</h1>
<p>Please login first to start playing</p>

  <c:if test="${not empty error }">
        <p class="text-danger">${error} </p>
    </c:if>

 <form action="LoginPageServlet" method="get">
   Username: <input type="text" class="form-control" id="username" name="username" value="${param.username}">
   <br>
   Password: <input type="password" class="form-control" id="password" name="password" value="${param.password}">
   <button type="submit" class="btn btn-default">Submit</button>
 </form>
  
</body>

</html>