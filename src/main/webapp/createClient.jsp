<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Create Client</title>
</head>
<body>
<h2>Create Client</h2>
<form action="createClient" method="post">
    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" required><br>

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required><br>

    <label for="middleName">Middle Name:</label>
    <input type="text" id="middleName" name="middleName"><br>

    <label for="contactPhone">Contact Phone:</label>
    <input type="text" id="contactPhone" name="contactPhone" required><br>

    <button type="submit">Create</button>
</form>
<a href="viewClients.jsp">Back to View Clients</a>
</body>
</html>