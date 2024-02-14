package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa.Tag;
import main.EntityManagerHelper;

public class TagDao implements GenericDao<Tag, String >{
    @Override
    public Tag save(Tag tag) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction
        entityManager.persist(tag);
        tx.commit(); // Validation de la transaction
        return tag;
    }

    @Override
    public Tag read(String id) {
        return EntityManagerHelper.getEntityManager().find(Tag.class,id);
    }

    @Override
    public Tag update(Tag tag) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            Tag updatedTag = entityManager.merge(tag);
            tx.commit(); // Validation de la transaction
            return updatedTag;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }

    @Override
    public void delete(Tag tag) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            entityManager.remove(entityManager.contains(tag) ? tag : entityManager.merge(tag));
            tx.commit(); // Validation de la transaction
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }
}
