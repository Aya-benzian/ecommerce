# Atelier JEE - Application E-Commerce

Application web de commerce électronique développée en **Java EE / Jakarta EE** dans le cadre des travaux pratiques (FSTT).

---

## 🛠️ Technologies Utilisées

- **Langage** : Java 11
- **Architecture** : MVC (Modèle - Vue - Contrôleur) avec pattern DAO
- **ORM / Persistance** : JPA 2.2 avec **EclipseLink 2.7.5**
- **Base de Données** : MySQL 8.x
- **Conteneur Web** : Apache Tomcat 9 (Servlets 4.0, JSP 2.3, JSTL 1.2)
- **Gestion des dépendances & Build** : Apache Maven

---

## 📂 Structure du Projet

```
ecommerce/
├── src/
│   ├── main/
│   │   ├── java/ma/fstt/ecommerce/
│   │   │   ├── dao/           # Couche d'accès aux données (GenericDao, ProduitDao, PanierDao, etc.)
│   │   │   ├── entities/      # Entités JPA (Produit, Categorie, Vitrine, Panier, LignePanier, Internaute)
│   │   │   └── web/           # Contrôleurs Servlets & Listeners (ProduitServlet, VitrineServlet, etc.)
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml   # Configuration de la persistence unit (ecommercePU)
│   │   └── webapp/
│   │       ├── index.jsp      # Page d'accueil
│   │       └── WEB-INF/
│   │           ├── web.xml    # Configuration du déploiement web
│   │           └── views/     # Vues JSP (produit, vitrine, internaute, panier)
├── pom.xml                    # Configuration Maven
└── README.md
```

---

## ⚙️ Configuration & Installation

### 1. Prérequis
- Java JDK 11 ou supérieur
- Apache Maven 3.6+
- Apache Tomcat 9
- Serveur MySQL en cours d'exécution

### 2. Base de données
Créer la base de données et l'utilisateur MySQL conformément au fichier `persistence.xml` :
```sql
CREATE DATABASE IF NOT EXISTS ecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'ecom'@'localhost' IDENTIFIED BY 'ecom123';
GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecom'@'localhost';
FLUSH PRIVILEGES;
```
> *Note : Les tables sont générées automatiquement par EclipseLink au démarrage (`eclipselink.ddl-generation` = `create-tables`).*

### 3. Compilation et Déploiement
Pour compiler et générer le fichier WAR :
```bash
mvn clean package
```
Le fichier WAR généré (`target/ecommerce.war`) peut ensuite être déployé sur un serveur Tomcat 9 ou exécuté via IntelliJ IDEA / Smart Tomcat.

---

## 🌟 Fonctionnalités

- **Gestion des Produits** : Consultation, ajout, modification et suppression de produits avec catégories.
- **Vitrine Client** : Affichage dynamique des produits disponibles avec filtrage par vitrine / catégorie.
- **Gestion du Panier** : Ajout d'articles au panier, modification des quantités et consultation du total.
- **Authentification & Inscription** : Gestion des comptes internaute (clients).
