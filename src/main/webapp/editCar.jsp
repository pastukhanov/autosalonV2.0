<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card my-5">
    <div class="card-body">

        <form id="carForm" action="/add-car" method="post">
            <input type="hidden" name="id" value="${car.id}">
            <input type="hidden" name="action" value="${action}">

            <div class="form-group row">
                <label for="carType" class="col-sm-2 col-form-label">Тип кузова:</label>
                <div class="col-sm-10">
                    <select id="carType" name="carType">
                        <option value="TRUCK" ${car.type == 'TRUCK' ? 'selected="selected"' : ''}>Truck</option>
                        <option value="SUV" ${car.type == 'SUV' ? 'selected="selected"' : ''}>SUV</option>
                        <option value="SEDAN" ${car.type == 'SEDAN' ? 'selected="selected"' : ''}>Sedan</option>
                        <option value="VAN" ${car.type == 'VAN' ? 'selected="selected"' : ''}>Van</option>
                        <option value="PASSENGER_CAR" ${car.type == 'PASSENGER_CAR' ? 'selected="selected"' : ''}>Passenger Car</option>
                        <option value="UNKNOWN" ${car.type == 'UNKNOWN' ? 'selected="selected"' : ''}>Other</option>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label for="model" class="col-sm-2 col-form-label">Модель:</label>
                <div class="col-sm-10">
                    <input type="text" id="model" name="model" value="${car.model}">
                </div>
            </div>

            <div class="form-group row">
                <label for="brand" class="col-sm-2 col-form-label">Бренд:</label>
                <div class="col-sm-10">
                    <input type="text" id="brand" name="brand" value="${car.brand}">
                </div>
            </div>

            <div>
                <button type="submit" class="btn btn-primary mr-2">Сохранить</button>
                <a href="/cars" class="btn btn-secondary">Отменить</a>
            </div>
        </form>

    </div>
</div>

