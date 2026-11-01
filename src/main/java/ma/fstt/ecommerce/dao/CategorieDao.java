package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.entities.Categorie;
import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class CategorieDao extends GenericDao<Categorie> {
    public CategorieDao() { super(Categorie.class); }
}