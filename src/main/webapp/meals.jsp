<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals Table</title>
</head>
<body>
    <table border="1">
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
        <c:forEach var="mealTo" items="${table}">
            <tr bgcolor="${mealTo.excess ? "#FF0040" : "#00FF66"}">
                <th><c:out value="${mealTo.dateTime.toLocalDate()} ${mealTo.dateTime.toLocalTime()}"/></th>
                <th><c:out value="${mealTo.description}"/></th>
                <th><c:out value="${mealTo.calories}"/></th>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
