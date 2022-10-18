<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quiz Game</title>
</head>
<body>
  <h1>Quiz time!</h1>
     <form action="QuestionServlet" method="get">
        Choose:<br>
        <select name="choice">
            <option></option> <!-- ensimmäisenä tyhjä vaihtoehto -->
            <c:forEach var="q" items="${questions}">
                <option>${q.question }</option> 
            </c:forEach>
        </select>
        <input type="submit" value="Submit">
    </form>

</body>
</html>