<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Produit</title>
    <style>
        body { font-family: sans-serif; margin: 30px; }
        label { display: block; margin-top: 12px; }
        input, textarea, select { width: 320px; padding: 6px; }
        button { margin-top: 16px; padding: 8px 18px; }
    </style>
</head>
<body>
<h1>${empty produit ? 'Nouveau produit' : 'Modifier le produit'}</h1>

<form action="ProduitServlet" method="post">
    <input type="hidden" name="action" value="${empty produit ? 'ajouter' : 'modifier'}">
    <c:if test="${not empty produit}">
        <input type="hidden" name="id" value="${produit.id}">
    </c:if>

    <label>Désignation
        <input type="text" name="designation" value="${produit.designation}" required>
    </label>
    <label>Description
        <textarea name="description">${produit.description}</textarea>
    </label>
    <label>Prix
        <input type="number" step="0.01" name="prix" value="${produit.prix}" required>
    </label>
    <label>Stock
        <input type="number" name="stock" value="${produit.stock}" required>
    </label>
    <label>Image (URL)
        <input type="text" name="image" value="${produit.image}">
    </label>

    <label>Catégorie
        <select name="categorieId">
            <option value="">-- aucune --</option>
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}" ${produit.categorie.id == c.id ? 'selected' : ''}>${c.nom}</option>
            </c:forEach>
        </select>
    </label>
    <label>Vitrine
        <select name="vitrineId">
            <option value="">-- aucune --</option>
            <c:forEach var="v" items="${vitrines}">
                <option value="${v.id}" ${produit.vitrine.id == v.id ? 'selected' : ''}>${v.nom}</option>
            </c:forEach>
        </select>
    </label>

    <button type="submit">Enregistrer</button>
    <a href="ProduitServlet?action=lister">Annuler</a>
</form>
</body>
</html>