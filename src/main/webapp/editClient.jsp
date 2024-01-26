<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card my-5">
    <div class="card-body">

        <form id="carForm" action="/add-customer" method="post">
            <input type="hidden" name="id" value="${customer.id}">
            <input type="hidden" name="action" value="${action}">

            <div class="form-group row">
                <label for="gender" class="col-sm-2 col-form-label">Пол:</label>
                <div class="col-sm-10">
                    <select id="gender" name="gender">
                        <option value="MALE" ${customer.gender == 'MALE' ? 'selected="selected"' : ''}>Мужчина</option>
                        <option value="FEMALE" ${customer.gender == 'FEMALE' ? 'selected="selected"' : ''}>Женщина</option>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label for="name" class="col-sm-2 col-form-label">ФИО:</label>
                <div class="col-sm-10">
                    <input type="text" id="name" name="name" value="${customer.name}">
                </div>
            </div>

            <div class="form-group row">
                <label for="age" class="col-sm-2 col-form-label">Возраст:</label>
                <div class="col-sm-10">
                    <input type="number" id="age" name="age" value="${customer.age}" min="1" max="100">
                </div>
            </div>

            <div>
                <button type="submit" class="btn btn-primary mr-2">Сохранить</button>
                <a href="/customers" class="btn btn-secondary">Отметить</a>
            </div>
        </form>

    </div>
</div>

