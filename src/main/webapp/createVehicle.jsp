<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Vehicle</title>
</head>
<body>
    <h2>Create Vehicle</h2>
    <form action="${pageContext.request.contextPath}/createVehicle" method="post">
        <label for="mark">Mark:</label>
        <input type="text" id="mark" name="mark" required><br>

        <label for="registrationNumber">Registration Number:</label>
        <input type="text" id="registrationNumber" name="registrationNumber" required><br>

        <label for="yearOfManufacture">Year of Manufacture:</label>
        <input type="number" id="yearOfManufacture" name="yearOfManufacture" required><br>

        <button type="submit">Create</button>
    </form>
</body>
</html>