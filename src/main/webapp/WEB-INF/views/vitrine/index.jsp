<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vitrine</title>
    <style>
        body { font-family: sans-serif; margin: 30px; }
        .barre { display: flex; justify-content: space-between; align-items: center; }
        .grille { display: flex; flex-wrap: wrap; gap: 16px; margin-top: 20px; }
        .carte { border: 1px solid #ccc; border-radius: 6px; padding: 14px; width: 220px; }
        .prix { font-weight: bold; color: #2d6cdf; }
        a.btn, button { padding: 6px 12px; background: #2d6cdf; color: #fff; border: 0;
                        border-radius: 4px; text-decoration: none; cursor: pointer; }
        .filtres a { margin-right: 10px; }
    </style>
</head>
<body>

<div class="barre">
    <h1>Notre vitrine</h1>
    <div>
        <c:choose>
            <c:when test="${not empty sessionScope.internaute}">
                Bonjour ${sessionScope.internaute.prenom} |
                <a href="PanierServlet?action=voir">Mon panier</a> |
                <a href="InternauteServlet?action=deconnecter">Déconnexion</a>
            </c:when>
            <c:otherwise>
                <a href="InternauteServlet?action=formLogin">Connexion</a> |
                <a href="InternauteServlet?action=formInscription">Inscription</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="filtres">
    <a href="VitrineServlet?action=afficher">Tout</a>
    <c:forEach var="v" items="${vitrines}">
        <a href="VitrineServlet?action=afficher&vitrineId=${v.id}">${v.nom}</a>
    </c:forEach>
</div>

<div class="grille">
    <c:forEach var="p" items="${produits}">
        <div class="carte">
            <h3>${p.designation}</h3>
            <p>${p.description}</p>
            <p class="prix">${p.prix} DH</p>
            <p>Stock : ${p.stock}</p>
            <c:if test="${p.stock > 0}">
                <form action="PanierServlet" method="post">
                    <input type="hidden" name="action" value="ajouter">
                    <input type="hidden" name="produitId" value="${p.id}">
                    <input type="number" name="quantite" value="1" min="1" max="${p.stock}" style="width:60px">
                    <button type="submit">Ajouter au panier</button>
                </form>
            </c:if>
            <c:if test="${p.stock <= 0}"><p>Rupture de stock</p></c:if>
        </div>
    </c:forEach>
    <c:if test="${empty produits}"><p>Aucun produit.</p></c:if>
</div>

</body>
</html>