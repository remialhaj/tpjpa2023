package servlet;

import dao.UserDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.User;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    private UserDao userDao = new UserDao(); // Assurez-vous que UserDao est correctement importé

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les données de la base de données
        List<User> users = userDao.getAllUsers(); // Assurez-vous que userDao est correctement initialisé

        // Ajouter les données à la requête
        request.setAttribute("users", users);

        // Rediriger vers la JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les données du formulaire
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Créer un nouvel utilisateur
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        // Sauvegarder l'utilisateur dans la base de données
        userDao.save(user); // Assurez-vous que userDao est correctement initialisé

        // Rediriger vers la page principale
        response.sendRedirect(request.getContextPath() + "/user");
    }
}
