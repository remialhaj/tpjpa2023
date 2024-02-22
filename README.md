# Template de projet pour le TP JPA 2021 UniR

sh run-hsqldb-server.sh
sh show-hsqldb.sh


TP2-4 JPA
Qestion 1:
créeation de 5 entités dans JPA: admin, comment, tag, ticket, user

Question 2:
association entre les entités avec ticket et comment dans ticket @OneToMany(mappedBy

Question 3:
base de données peuplée dans JpaTest en faisant des save dans la fonction createTickets

Question 4:
Connexion à la base de données avec l'interface

Question 5:
-l'hériatge entre les entités avec user et admin (@Inheritance(strategy = InheritanceType.JOINED)))
-requete dans UserDao et AdminDao pour récupérer les utilisateurs et les admins (getAllUsers (utilisé dans UserServelet) et getAll)
-requete nommée dans AdminDao (getAlls)

Question 6:
J'ai vu sur internet le problème de n+1 select et j'ai compris, comme vous l'avez dit.

TP5
mvn compile
mvn jetty:run
Partie 1:

Question 1: ajout des dépendances dans le pom.xml, c'est fait.

Question 2: Création de index.html, c'est fait dans le package webapp.

Question 3: Création de la classe MyServlet, c'est fait dans le package servlet.
http://localhost:8080/myurl
Question 4: Création de la classe myform.html et userInfo, c'est fait dans le package webapp.
http://localhost:8080/myform.html
Question 5: Création de la classe UserServlet, c'est fait dans le package servlet et de user.jsp dans le package webapp.
http://localhost:8080/user