<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="card my-5">
    <div class="card-body">

        <div class="mb-2 d-flex justify-content-between">
            <div>
                <a class="btn btn-success" href="add-customer">Добавить</a>
            </div>
            <div>
                <button class="btn btn-primary mr-1" onclick="editSelectedObject('customer')">Изменить</button>
                <button class="btn btn-danger"  onclick="deleteSelectedObjects('customers')">Удалить</button>
            </div>
        </div>

        <table class="table table-bordered table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">
                    <input type="checkbox" onclick="toggleAllObjectCheckboxes(this); event.stopPropagation();">
                </th>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="col">Age</th>
                <th scope="col">Gender</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="client" items="${customers}">
                <tr onclick="toggleObjSelection(this)">
                    <td>
                        <input type="checkbox" data-id="${client.id}" onclick="event.stopPropagation();">
                    </td>
                    <td>${client.id}</td>
                    <td>${client.name}</td>
                    <td>${client.age}</td>
                    <td>${client.gender}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>



