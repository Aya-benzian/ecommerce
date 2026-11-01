<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Produits</title>
    <style>
        body { font-family: sans-serif; margin: 30px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        a.btn { padding: 4px 10px; background: #2d6cdf; color: #fff; text-decoration: none; border-radius: 4px; }
        a.del { background: #c0392b; }
    </style>
</head>
<body>
<h1>Gestion des produits</h1>

<form action="ProduitServlet" method="get">
    <input type="hidden" name="action" value="lister">
    <input type="text" name="motCle" placeholder="Rechercher..." value="${param.motCle}">
    <button type="submit">Chercher</button>
    <a class="btn" href="ProduitServlet?action=nouveau">+ Nouveau produit</a>
</form>
<br>

<table>
    <tr>
        <th>ID</th><th>Désignation</th><th>Prix</th><th>Stock</th>
        <th>Catégorie</th><th>Vitrine</th><th>Actions</th>
    </tr>
    <c:forEach var="p" items="${produits}">
        <tr>
            <td>${p.id}</td>
            <td>${p.designation}</td>
            <td>${p.prix}</td>
            <td>${p.stock}</td>
            <td>${p.categorie.nom}</td>
            <td>${p.vitrine.nom}</td>
            <td>
                <a class="btn" href="ProduitServlet?action=editer&id=${p.id}">Modifier</a>
                <a class="btn del" href="ProduitServlet?action=supprimer&id=${p.id}"
                   onclick="return confirm('Supprimer ce produit ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty produits}">
        <tr><td colspan="7">Aucun produit.</td></tr>
    </c:if>
</table>
</body>
</html>