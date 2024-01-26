<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Client</title>
</head>
<body>
<h2>Delete Client</h2>
<form action="deleteClient" method="get">
    <label for="clientId">Client ID:</label>
    <input type="text" id="clientId" name="clientId" required>
    <button type="submit">Delete</button>
</form>
<a href="viewClients.jsp">Back to View Clients</a>
</body>
</html>