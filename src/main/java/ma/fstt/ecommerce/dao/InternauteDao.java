package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.entities.Internaute;
import ma.fstt.ecommerce.entities.Panier;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class InternauteDao extends GenericDao<Internaute> {

    public InternauteDao() { super(Internaute.class); }

    /** Saves the internaute and creates their Panier in one transaction. */
    public Internaute inscrire(Internaute i) {
        EntityManager em = JPAUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Panier p = new Panier();
            p.setInternaute(i);
            i.setPanier(p);
            em.persist(i);          // cascade persists the Panier
            tx.commit();
            return i;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public Internaute findByEmail(String email) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            List<Internaute> r = em.createQuery(
                            "SELECT i FROM Internaute i WHERE i.email = :e", Internaute.class)
                    .setParameter("e", email)
                    .getResultList();
            return r.isEmpty() ? null : r.get(0);
        } finally {
            em.close();
        }
    }

    public Internaute authentifier(String email, String motDePasse) {
        Internaute i = findByEmail(email);
        return (i != null && motDePasse != null && motDePasse.equals(i.getMotDePasse())) ? i : null;
    }
}