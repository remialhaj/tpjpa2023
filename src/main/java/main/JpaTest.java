package main;

import dao.*;
import jakarta.persistence.*;
import jpa.*;
import java.util.*;

public class JpaTest {

	private EntityManager manager;
	private UserDao userDao; // Ajoutez cette déclaration
	private AdminDao adminDao; // Ajoutez cette déclaration
	private TicketDao ticketDao;
	private CommentDao commentDao;
	private TagDao tagDao;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
		this.userDao = new UserDao(); // Instanciez le UserDao
		this.adminDao = new AdminDao(); // Instanciez l'AdminDao
		this.ticketDao = new TicketDao();
		this.commentDao = new CommentDao();
		this.tagDao = new TagDao();
	}

	public static void main(String[] args) {
		EntityManagerFactory factory =
				Persistence.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			test.createTickets();

		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();

		test.listTickets();

		manager.close();
		System.out.println(".. done");
	}

	private void createTickets() {
		int numOfTickets = manager.createQuery("Select t From Ticket t", Ticket.class).getResultList().size();
		if (numOfTickets == 0) {

			Admin admin = new Admin();
			admin.setUsername("john_doe");
			admin.setRole("Administrator");
			admin.setEmail("john.doe@example.com");
			admin.setPassword("password123");

			User user1 = new User();
			user1.setUsername("user1");
			user1.setEmail("user1@example.com");
			user1.setPassword("password1");

			User user2 = new User();
			user2.setUsername("user2");
			user2.setEmail("user2@example.com");
			user2.setPassword("password2");

			// Utilisez le DAO pour sauvegarder l'utilisateur
			adminDao.save(admin);
			userDao.save(user1);
			userDao.save(user2);

			Tag tag1 = new Tag();
			tag1.setName("Bug");
			tagDao.save(tag1);

			Tag tag2 = new Tag();
			tag2.setName("Feature Request");
			tagDao.save(tag2);

			Ticket ticket1 = new Ticket();
			ticket1.setTitle("Issue 1");
			ticket1.setDescription("Description of Issue 1");
			ticket1.setCreatedDate(new Date());
			ticket1.setCreatedBy(user1);
			ticket1.setAssignedTo(user2);
			ticket1.getTags().add(tag1);
			ticketDao.save(ticket1);

			Comment comment1 = new Comment();
			comment1.setContent("This is a comment about issue 1");
			comment1.setCreatedBy(user2);
			comment1.setCreatedDate(new Date());
			comment1.setTicket(ticket1);
			commentDao.save(comment1);

			Ticket ticket2 = new Ticket();
			ticket2.setTitle("Issue 2");
			ticket2.setDescription("Description of Issue 2");
			ticket2.setCreatedDate(new Date());
			ticket2.setCreatedBy(user2);
			ticket2.setAssignedTo(user1);
			ticket2.getTags().add(tag2);
			ticketDao.save(ticket2);
		}
	}

	private void listTickets() {
		List<Ticket> resultList = manager.createQuery("Select t From Ticket t", Ticket.class).getResultList();
		System.out.println("Number of tickets: " + resultList.size());
		for (Ticket next : resultList) {
			System.out.println("Next ticket: " + next.getTitle() + " - Description: " + next.getDescription());
			System.out.println("Tags:");
			for (Tag tag : next.getTags()) {
				System.out.println("- " + tag.getName());
			}
			System.out.println("Comments:");
			for (Comment comment : next.getComments()) {
				System.out.println("- " + comment.getContent());
			}
		}
	}
}
