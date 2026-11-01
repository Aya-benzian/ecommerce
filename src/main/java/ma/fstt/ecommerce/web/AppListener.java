package ma.fstt.ecommerce.web;

import ma.fstt.ecommerce.dao.JPAUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent e) {
        System.out.println(">>> AppListener started, initializing JPA");
        EntityManager em = JPAUtil.createEntityManager();  // forces login + table creation
        em.close();
        System.out.println(">>> JPA initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        JPAUtil.close();
    }
}