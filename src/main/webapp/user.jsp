<%@ page import="jpa.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Utilisateurs</title>
</head>
<body>
<h1>Liste des Utilisateurs</h1>
<ul>
    <% for (User user : (List<User>) request.getAttribute("users")) { %>
    <li><%= user.getUsername() %>: <%= user.getEmail() %></li>
    <% } %>
</ul>

<h2>Ajouter un nouvel Utilisateur</h2>
<form action="user" method="post">
    <label for="username">Nom d'utilisateur:</label>
    <input type="text" id="username" name="username"><br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email"><br>
    <label for="password">Mot de passe:</label>
    <input type="password" id="password" name="password"><br>
    <input type="submit" value="Ajouter">
</form>
</body>
</html>
