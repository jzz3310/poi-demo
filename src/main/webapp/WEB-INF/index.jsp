<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="css/index_work.css"/>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<html>
<head>
    <title>Title</title>
</head>

<body>
<div class="excel">

    <form action="${pageContext.request.contextPath}/import", method="post", enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input type="submit">
    </form>
    <table>
        <tr>
            <td>ID</td>
            <td>name</td>
        </tr>
        <c:forEach items="${list}" var="a">

            <tr>
                <td>${a.id}</td>
                <td>${a.name}</td>
            </tr>
        </c:forEach>
    </table>
    <form action="${pageContext.request.contextPath}/expor">
        <button>导出Excel</button>
    </form>
</div>
</body>
</html>
