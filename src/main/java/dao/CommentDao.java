package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa.Comment;
import main.EntityManagerHelper;

public class CommentDao implements GenericDao<Comment, String >{
    @Override
    public Comment save(Comment comment) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction
        entityManager.persist(comment);
        tx.commit(); // Validation de la transaction
        return comment;
    }

    @Override
    public Comment read(String id) {
        return EntityManagerHelper.getEntityManager().find(Comment.class,id);
    }

    @Override
    public Comment update(Comment comment) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            Comment updatedComment = entityManager.merge(comment);
            tx.commit(); // Validation de la transaction
            return updatedComment;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }

    @Override
    public void delete(Comment comment) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            entityManager.remove(entityManager.contains(comment) ? comment : entityManager.merge(comment));
            tx.commit(); // Validation de la transaction
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }
}
