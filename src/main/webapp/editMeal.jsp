<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактирование</title>
</head>
<body>
<form method="post" action="meals">
    <c:set var="meal" value="${table}"/>
    <input type="hidden" name="id" value="${meal.id}">
<table border="1">
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <tr>
        <th><input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}" />" /></th>
        <th><input type="text" name="description" value="<c:out value="${meal.description}" />" /></th>
        <th><input type="text" name="calories" value="<c:out value="${meal.calories}"/> "/></th>
        <th><input type="submit" value="${empty meal.id ? "Добавить блюдо" : "Подтвердить изменения"}"></th>
    </tr>
</table>
</form>
</body>
</html>
