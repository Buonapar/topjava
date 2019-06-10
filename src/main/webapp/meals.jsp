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
                <th><a href="meals?action=edit&id=${mealTo.id}">Редактировать</a></th>
                <th><a href="meals?action=delete&id=${mealTo.id}"> Удалить</a></th>
            </tr>
        </c:forEach>
    </table>
    <form action="editMeal.jsp">
        <button>Добавить блюдо</button>
    </form>
</body>
</html>
