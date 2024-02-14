package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import main.EntityManagerHelper;
import jpa.Admin;

import java.util.List;

public class AdminDao implements GenericDao<Admin, String > {
    @Override
    public Admin save(Admin admin) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction
        entityManager.persist(admin);
        tx.commit(); // Validation de la transaction
        return admin;
    }

    @Override
    public Admin read(String id) {
        return EntityManagerHelper.getEntityManager().find(Admin.class,id);
    }

    @Override
    public Admin update(Admin admin) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            Admin updatedAdmin = entityManager.merge(admin);
            tx.commit(); // Validation de la transaction
            return updatedAdmin;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }

    @Override
    public void delete(Admin admin) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            entityManager.remove(entityManager.contains(admin) ? admin : entityManager.merge(admin));
            tx.commit(); // Validation de la transaction
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }
    // Add a method to get all the admins if role is admin
    public List<Admin> getAll(String role) {
        return EntityManagerHelper.getEntityManager().createQuery("SELECT a FROM Admin a WHERE a.role = :role", Admin.class)
                .setParameter("role", role)
                .getResultList();
    }
}
