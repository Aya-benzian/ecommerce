<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mon panier</title>
    <style>
        body { font-family: sans-serif; margin: 30px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        a.btn { padding: 4px 10px; background: #2d6cdf; color: #fff; text-decoration: none; border-radius: 4px; }
        a.del { background: #c0392b; }
        .total { text-align: right; font-size: 1.2em; margin-top: 14px; }
    </style>
</head>
<body>
<h1>Mon panier</h1>
<p><a href="VitrineServlet?action=afficher">&larr; Continuer mes achats</a></p>

<c:choose>
    <c:when test="${empty panier.lignes}">
        <p>Votre panier est vide.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Produit</th><th>Prix unitaire</th><th>Quantité</th><th>Sous-total</th><th></th>
            </tr>
            <c:forEach var="l" items="${panier.lignes}">
                <tr>
                    <td>${l.produit.designation}</td>
                    <td>${l.produit.prix} DH</td>
                    <td>${l.quantite}</td>
                    <td>${l.produit.prix * l.quantite} DH</td>
                    <td>
                        <a class="btn del" href="PanierServlet?action=retirer&ligneId=${l.id}">Retirer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p class="total">Total : <strong>${total} DH</strong></p>
        <a class="btn del" href="PanierServlet?action=vider"
           onclick="return confirm('Vider le panier ?');">Vider le panier</a>
    </c:otherwise>
</c:choose>
</body>
</html>