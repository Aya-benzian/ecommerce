package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.entities.Vitrine;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class VitrineDao extends GenericDao<Vitrine> {
    public VitrineDao() { super(Vitrine.class); }
}