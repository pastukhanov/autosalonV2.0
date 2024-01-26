<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="card my-5">
    <div class="card-body">

        <div class="mb-2 d-flex justify-content-between">
            <div>
                <a class="btn btn-success" href="add-sale">Добавить</a>
            </div>
            <div>
                <button class="btn btn-danger" onclick="deleteSelectedObjects('sales')">Удалить</button>
            </div>
        </div>


        <table class="table table-bordered table-striped table-hover">
            <thead class="thead-dark">
            <tr onclick="toggleObjSelection(this)">
                <th scope="col">
                    <input type="checkbox" onclick="toggleAllObjectCheckboxes(this); event.stopPropagation();">
                </th>
                <th scope="col">ID</th>
                <th scope="col">Car Model</th>
                <th scope="col">Car Brand</th>
                <th scope="col">Customer Name</th>
                <th scope="col">Customer ID</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="sale" items="${sales}">
                <tr>
                    <td>
                        <input type="checkbox" data-id="${sale.id}" onclick="event.stopPropagation();">
                    </td>
                    <td>${sale.id}</td>
                    <td>${sale.car.model}</td>
                    <td>${sale.car.brand}</td>
                    <td>${sale.customer.name}</td>
                    <td>${sale.customer.id}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>


