<%@ page import="java.util.Arrays" %>
<%@ page import="static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE" %>
<%@ page import="static jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION_TYPE" %>
<%@ page import="static jakarta.servlet.RequestDispatcher.*" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isErrorPage="true" %>
<style><%@include file="/all_components/style.css"%></style>
<script><%@include file="/all_components/show.js"%></script>
<html>
<head>
    <title>Error</title>
</head>
<body>
<div class="text-center error-page">
    <div class="container">
        <div class = "error-page-content">
            <h1>404</h1>
            <h2>Oops, Page not found</h2>
            <p><%=ERROR_STATUS_CODE%> <%=request.getAttribute(ERROR_STATUS_CODE)%></p>
            <p><%=ERROR_EXCEPTION_TYPE%> <%=request.getAttribute(ERROR_EXCEPTION_TYPE)%></p>
            <p><%=ERROR_MESSAGE%> <%=request.getAttribute(ERROR_MESSAGE)%></p>
        </div>
    </div>
</div>
</body>
</html>
