package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.entities.Produit;

import javax.persistence.EntityManager;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class ProduitDao extends GenericDao<Produit> {

    public ProduitDao() { super(Produit.class); }

    public List<Produit> findByCategorie(Long categorieId) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM Produit p WHERE p.categorie.id = :cid", Produit.class)
                    .setParameter("cid", categorieId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Produit> search(String motCle) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM Produit p WHERE LOWER(p.designation) LIKE :mc", Produit.class)
                    .setParameter("mc", "%" + motCle.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}