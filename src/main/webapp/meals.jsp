<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table {
            width: 30%; /* Ширина таблицы */
            margin-left: auto; /* сделать таблицу по центру */
            margin-right: auto; /* сделать таблицу по центру */
            text-align: center; /* текст внутри таблицы по центру */
            border: 1px solid black; /* Рамка вокруг таблицы */
            border-collapse: collapse; /* Отображать только одинарные линии */
        }

        td {
            padding: 5px; /* Поля вокруг содержимого ячеек */
            border: 1px solid black; /* Граница вокруг ячеек */
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<hr>

<table>
    <tr>
        <td>Время</td>
        <td>Приём пищи</td>
        <td>Калории</td>
    </tr>
    <c:forEach var="meal" items="${mealToList}">
        <tr style="color: ${meal.excess ? 'red' : 'green'}">
            <td>${fn:replace(meal.dateTime, 'T', ' ')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
