<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Vehicle</title>
</head>
<body>
    <h2>Delete Vehicle</h2>
    <form action="${pageContext.request.contextPath}/deleteVehicle" method="get">
        <label for="vehicleId">Vehicle ID:</label>
        <input type="number" id="vehicleId" name="vehicleId" required><br>

        <button type="submit">Delete</button>
    </form>
</body>
</html>