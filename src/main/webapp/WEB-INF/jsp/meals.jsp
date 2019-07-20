<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="HH" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="meal.title"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="fragments/headTag.jsp"/>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3><spring:message code="meal.title"/></h3>

    <form method="get" action="filter">
                <dl>
                    <dt><spring:message code="date.from"/>:</dt>
                    <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="date.to"/>:</dt>
                    <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="time.from"/>:</dt>
                    <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="time.to"/>:</dt>
                    <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
                </dl>
                <button type="submit">Filter</button>
            </form>

    <a href="edit"><spring:message code="meal.add"/></a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr data-mealExcess="${meal.excess}">
                <td><c:out value="${fn:formatDateTime(meal.dateTime)}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="edit?id=${meal.id}"><spring:message code="meal.update"/></a></td>
                <td><a href="delete?id=${meal.id}"><spring:message code="meal.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>