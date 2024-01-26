<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>View Vehicles</title>
</head>
<body>
    <h2>View Vehicles</h2>
    <ul>
        <c:forEach var="vehicle" items="${vehicles}">
            <li>
                Vehicle #${vehicle.vehicleId}: ${vehicle.mark} (${vehicle.registrationNumber})<br>
                Year of Manufacture: ${vehicle.yearOfManufacture}<br>
            </li>
        </c:forEach>
    </ul>
    
    <a href="createVehicle.jsp">Create New Vehicle</a><br>
	<a href="deleteVehicle.jsp">Delete Vehicle</a><br>
</body>
</html>