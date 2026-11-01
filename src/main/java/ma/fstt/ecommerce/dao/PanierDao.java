package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.entities.LignePanier;
import ma.fstt.ecommerce.entities.Panier;
import ma.fstt.ecommerce.entities.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped

public class PanierDao extends GenericDao<Panier> {

    public PanierDao() { super(Panier.class); }

    public Panier findByInternaute(Long internauteId) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            List<Panier> r = em.createQuery(
                            "SELECT DISTINCT p FROM Panier p LEFT JOIN FETCH p.lignes " +
                                    "WHERE p.internaute.id = :id", Panier.class)
                    .setParameter("id", internauteId)
                    .getResultList();
            return r.isEmpty() ? null : r.get(0);
        } finally {
            em.close();
        }
    }

    public void ajouterProduit(Long panierId, Long produitId, int quantite) {
        EntityManager em = JPAUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Panier panier = em.find(Panier.class, panierId);
            Produit produit = em.find(Produit.class, produitId);

            LignePanier existante = null;
            for (LignePanier l : panier.getLignes()) {
                if (l.getProduit().getId().equals(produitId)) { existante = l; break; }
            }
            if (existante != null) {
                existante.setQuantite(existante.getQuantite() + quantite);
            } else {
                LignePanier l = new LignePanier();
                l.setPanier(panier);
                l.setProduit(produit);
                l.setQuantite(quantite);
                panier.getLignes().add(l);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void retirerLigne(Long panierId, Long ligneId) {
        EntityManager em = JPAUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Panier panier = em.find(Panier.class, panierId);
            panier.getLignes().removeIf(l -> l.getId().equals(ligneId)); // orphanRemoval deletes it
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void vider(Long panierId) {
        EntityManager em = JPAUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Panier panier = em.find(Panier.class, panierId);
            panier.getLignes().clear();
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}