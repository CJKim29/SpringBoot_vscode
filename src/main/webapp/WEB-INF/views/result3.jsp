<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>result3</title>
        </head>

        <body>
            <hr3>업로드 결과</hr3>
            제목 : ${ requestScope.title }
            <c:forEach var="filename" items="${ filename_list }">
                <img src="images/${ filename }" width="100">
            </c:forEach>
            <a href="input3.html">다시하기</a>
        </body>

        </html>