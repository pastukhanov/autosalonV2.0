<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card my-5">
    <div class="card-body">

        <div class="mb-2 d-flex justify-content-between">
            <div>
                <a class="btn btn-success" href="add-car">Добавить</a>
            </div>
            <div>
                <button class="btn btn-primary mr-1" onclick="editSelectedObject('car')">Изменить</button>
                <button class="btn btn-danger" onclick="deleteSelectedObjects('cars')">Удалить</button>
            </div>
        </div>

        <table class="table table-bordered table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">
                    <input type="checkbox" onclick="toggleAllObjectCheckboxes(this); event.stopPropagation();">
                </th>
                <th scope="col">ID</th>
                <th scope="col">Type</th>
                <th scope="col">Model</th>
                <th scope="col">Brand</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="car" items="${cars}">
                <tr onclick="toggleObjSelection(this)">
                    <td>
                        <input type="checkbox" data-id="${car.id}" onclick="event.stopPropagation();">
                    </td>
                    <td>${car.id}</td>
                    <td>${car.type}</td>
                    <td>${car.model}</td>
                    <td>${car.brand}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>
