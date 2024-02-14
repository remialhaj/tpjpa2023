package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import main.EntityManagerHelper;
import jpa.User;

public class UserDao implements GenericDao<User, String > {
    @Override
    public User save(User user) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction
        entityManager.persist(user);
        tx.commit(); // Validation de la transaction
        return user;
    }

    @Override
    public User read(String id) {
        return EntityManagerHelper.getEntityManager().find(User.class,id);
    }

    @Override
    public User update(User user) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            User updatedUser = entityManager.merge(user);
            tx.commit(); // Validation de la transaction
            return updatedUser;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }

    @Override
    public void delete(User user) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
            tx.commit(); // Validation de la transaction
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }

}
