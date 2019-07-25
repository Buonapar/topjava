<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="meal.title"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
</head>

<jsp:include page="fragments/headTag.jsp"/>
<jsp:include page="fragments/bodyHeader.jsp"/>

<body>
<section>
    <hr>
    <spring:message code="meal.add" var="mealAdd"/>
    <spring:message code="meal.edit" var="mealEdit"/>
    <h2>${param.id == null? mealAdd : mealEdit}</h2>

    <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="${param.id == null? 'create' : 'update'}">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.dateTime"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="cancel"/></button>
    </form>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
