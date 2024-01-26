<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>${not empty title ? title : param.title}</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <!-- Header -->
                <div class="card bg-dark py-4">
                    <div class="card-body">
                        <h2 class="card-title text-center text-white py-3">Автосалон "Викинг"</h2>
                        <ul class="text-center list-inline py-2">
                            <li class="list-inline-item">
                                <a href="/" class="btn btn-info">О Проекте</a>
                            </li>
                            <li class="list-inline-item">
                                <a href="/cars" class="btn btn-info">База Машин</a>
                            </li>
                            <li class="list-inline-item">
                                <a href="/customers" class="btn btn-info">База Клиентов</a>
                            </li>
                            <li class="list-inline-item">
                                <a href="/sales" class="btn btn-info">База Продаж</a>
                            </li>
                            <li class="list-inline-item">
                                <a href="/add-sale" class="btn btn-danger">Продать автомобиль</a>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>

        <c:choose>
            <c:when test="${not empty contentFile}">
                <jsp:include page="${contentFile}" />
            </c:when>
            <c:when test="${not empty param.contentFile}">
                <jsp:include page="${param.contentFile}" />
            </c:when>
        </c:choose>

        <div class="row">
            <div class="col-md-12">

                <!-- Footer -->
                <div class="card bg-dark py-4">
                    <ul class="text-center list-inline py-2">
                        <li class="list-inline-item">
                            <a href="/" class="text-center text-white">О Проекте</a>
                        </li>
                        <li class="list-inline-item">
                            <a href="/cars" class="text-center text-white">База Машин</a>
                        </li>
                        <li class="list-inline-item">
                            <a href="/customers" class="text-center text-white">База Клиентов</a>
                        </li>
                        <li class="list-inline-item">
                            <a href="/sales" class="text-center text-white">База Продаж</a>
                        </li>
                    </ul>
                    <p class="text-center text-white">© 2023 All rights reserved. Created by Pastukhanov.</p>
                </div>
            </div>

        </div>
    </div>
</div>

    <script src="js/script.js"></script>
</body>
</html>
