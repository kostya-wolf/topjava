<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
        .margin7 {
            margin: 7px;
            padding: 7px;
        }
        .filter {
            background-color: greenyellow;
            border: 1px green solid;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <form action="meals" method="get">
        <input type="hidden" name="action" value="filter">
        <table class="filter">
            <tr class="margin7">
                <th>От даты (включая)</th>
                <th>До даты (включая)</th>
                <th>От времени (включая)</th>
                <th>До времени (исключая)</th>
            </tr>
            <tr class="margin7">
                <td align="center"><input type="date" class="filterInput" name="startDate" value="${param.startDate}"></td>
                <td align="center"><input type="date" class="filterInput" name="endDate" value="${param.endDate}"></td>
                <td align="center"><input type="time" class="filterInput" name="startTime" value="${param.startTime}"></td>
                <td align="center"><input type="time" class="filterInput" name="endTime" value="${param.endTime}"></td>
            </tr>
            <tr class="margin7">
                <td colspan="4" align="center">
                    <input type="button" value="Сбросить" onClick="resetAll()">&nbsp;
                    <input type="submit">
                </td>
            </tr>
        </table>
    </form>
</section>

<script>
    function resetAll() {
        let filters = document.querySelectorAll('input[class=filterInput]');
        for (let i = 0; i < filters.length; i++) {
            filters[i].value = null;
        }
        document.querySelector('input[type=submit]').click();
    }
</script>
</body>
</html>