<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://util.topjava.javawebinar.ru/functions" prefix="f" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <style type="text/css">
        td {
            border: 1px green solid;
            margin: 3px;
            padding: 3px;
            background-color: white;
        }
        th {
            width: 250px;
        }
        table {
            border: 2px darkgreen solid;
            background-color: greenyellow;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr><th>Date</th><th>Description</th><th>Calories</th></tr>
    <c:forEach var="meal" items="${allMealsTo}">
        <tr style='color: ${meal.excess ? "red" : "green"}'>
            <td>${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}</td><td>${meal.description}</td><td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>