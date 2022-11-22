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
    <b>Logged in as:</b> ${LoggedUser}<br><br>
    ${questionForClient.question}
    <form action="QuestionServlet" method="get">
      Choose:<br>
      <select name="choice">
        <option></option> <!-- ensimmäisenä tyhjä vaihtoehto -->
        <c:forEach var="a" items="${possibleAnswers}">
          <option>${a}</option> 
        </c:forEach>
      </select>
      <input type="submit" value="Submit">
    </form>
    
    <form action="QuestionServlet" method="get">    
      <input type="submit" value="Generate">
    </form>
   
    
    <c:if test="${not empty Answer && Answer == true}">
      That is correct!
    </c:if>
    
    <c:if test="${not empty Answer && Answer == false}">
      Not correct.
    </c:if>

</body>
</html>