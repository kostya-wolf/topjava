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
<h2>Edit meal</h2>
<form name="editMeal" method="post" action="meals">
    <table>
        <tr><th></th><th></th></tr>
        <tr><td>DateTime:</td><td><input type="datetime-local" name="dateTime" value="2021-10-26T23:07"></td></tr>
        <tr><td>Description:</td><td><input type="text" name="description" size="75"></td></tr>
        <tr><td>Calories:</td><td><input type="text" name="calories" size="7"></td></tr>
        <tr>
            <td colspan="2"><input type="submit" value="save"/>&nbsp;&nbsp;&nbsp;<input type="button" value="cancel"/></td>
        </tr>

    </table>
</form>
</body>
</html>