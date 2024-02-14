package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa.Ticket;
import main.EntityManagerHelper;

public class TicketDao implements GenericDao<Ticket, String >{
    @Override
    public Ticket save(Ticket ticket) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction
        entityManager.persist(ticket);
        tx.commit(); // Validation de la transaction
        return ticket;
    }

    @Override
    public Ticket read(String id) {
        return EntityManagerHelper.getEntityManager().find(Ticket.class,id);
    }

    @Override
    public Ticket update(Ticket ticket) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            Ticket updatedTicket = entityManager.merge(ticket);
            tx.commit(); // Validation de la transaction
            return updatedTicket;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }

    @Override
    public void delete(Ticket ticket) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin(); // Début de la transaction

        try {
            entityManager.remove(entityManager.contains(ticket) ? ticket : entityManager.merge(ticket));
            tx.commit(); // Validation de la transaction
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Annulation de la transaction en cas d'erreur
            }
            throw e; // Propagation de l'exception
        }
    }
}
