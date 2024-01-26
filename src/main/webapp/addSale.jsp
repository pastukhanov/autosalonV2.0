<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card my-5">
    <div class="card-body">

        <form id="carForm" action="/add-sale" method="post">

            <div class="form-group row">
                <label for="searchInput" class="col-sm-2 col-form-label">Найти машину:</label>
                <div class="col-sm-10">
                    <div class="search-container">
                        <input type="text" id="searchInput" placeholder="Search...">
                        <div id="searchDropdown" class="dropdown-content">
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label for="carModel" class="col-sm-2 col-form-label">Модель:</label>
                <div class="col-sm-10">
                    <input type="text" id="carModel" name="carModel" readonly>
                </div>
            </div>

            <div class="form-group row">
                <label for="carBrand" class="col-sm-2 col-form-label">Бренд:</label>
                <div class="col-sm-10">
                    <input type="text" id="carBrand" name="carBrand" readonly>
                </div>
            </div>

            <div class="form-group row" hidden="true">
                <label for="carId">Car ID:</label>
                <div class="col-sm-10">
                    <input type="text" id="carId" name="carId" readonly>
                </div>
            </div>

            <div class="form-group row">
                <label for="name" class="col-sm-2 col-form-label">ФИО:</label>
                <div class="col-sm-10">
                    <input type="text" id="name" name="name" value="${customer.name}">
                </div>
            </div>

            <div class="form-group row">
                <label for="gender" class="col-sm-2 col-form-label">Пол:</label>
                <div class="col-sm-10">
                    <select id="gender" name="gender">
                        <option value="MALE">Мужчина</option>
                        <option value="FEMALE">Женщина</option>
                    </select>
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


<script>
    document.addEventListener('DOMContentLoaded', function() {
        var searchInput = document.getElementById('searchInput');
        var dropdown = document.getElementById('searchDropdown');

        var data = <c:out value="${cars}" default="[]" escapeXml="false" />;

        searchInput.addEventListener('input', function() {
            var searchQuery = this.value.toLowerCase();

            dropdown.innerHTML = '';

            var filteredData = data.filter(function(item) {
                return item['model'].toLowerCase().includes(searchQuery) ||
                    item['brand'].toLowerCase().includes(searchQuery);
            });

            filteredData.forEach(function(item) {
                var div = document.createElement('div');
                div.textContent = item['model'] + ' (' + item['brand'] + ')';
                div.addEventListener('click', function() {
                    searchInput.value = item['model'] + ' (' + item['brand'] + ')';
                    dropdown.innerHTML = '';
                    document.getElementById("carModel").value=item['model'];
                    document.getElementById("carBrand").value=item['brand'];
                    document.getElementById("carId").value=item['id'];
                });
                dropdown.appendChild(div);
            });

            if (searchQuery === '') {
                dropdown.innerHTML = '';
            }
        });
    });
</script>
