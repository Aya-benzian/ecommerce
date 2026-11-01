<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <style>
        body { font-family: sans-serif; margin: 30px; }
        label { display: block; margin-top: 12px; }
        input { width: 280px; padding: 6px; }
        button { margin-top: 16px; padding: 8px 18px; }
        .erreur { color: #c0392b; }
    </style>
</head>
<body>
<h1>Inscription</h1>
<c:if test="${not empty erreur}"><p class="erreur">${erreur}</p></c:if>

<form action="InternauteServlet" method="post">
    <input type="hidden" name="action" value="inscrire">
    <label>Nom <input type="text" name="nom" required></label>
    <label>Prénom <input type="text" name="prenom" required></label>
    <label>Email <input type="email" name="email" required></label>
    <label>Mot de passe <input type="password" name="motDePasse" required></label>
    <button type="submit">Créer mon compte</button>
</form>
<p><a href="InternauteServlet?action=formLogin">Déjà inscrit ? Se connecter</a></p>
</body>
</html>