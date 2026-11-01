package ma.fstt.ecommerce.web;

import ma.fstt.ecommerce.dao.InternauteDao;
import ma.fstt.ecommerce.entities.Internaute;
import javax.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/InternauteServlet")
public class InternauteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject private InternauteDao internauteDao;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        traiter(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        traiter(request, response);
    }

    private void traiter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "formLogin";

        if ("formLogin".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/internaute/login.jsp").forward(request, response);

        } else if ("formInscription".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/internaute/inscription.jsp").forward(request, response);

        } else if ("inscrire".equals(action)) {
            String email = request.getParameter("email");
            if (internauteDao.findByEmail(email) != null) {
                request.setAttribute("erreur", "Cet email est déjà utilisé.");
                request.getRequestDispatcher("/WEB-INF/views/internaute/inscription.jsp").forward(request, response);
                return;
            }
            Internaute i = new Internaute();
            i.setNom(request.getParameter("nom"));
            i.setPrenom(request.getParameter("prenom"));
            i.setEmail(email);
            i.setMotDePasse(request.getParameter("motDePasse"));
            internauteDao.inscrire(i);
            response.sendRedirect("InternauteServlet?action=formLogin");

        } else if ("connecter".equals(action)) {
            Internaute i = internauteDao.authentifier(
                    request.getParameter("email"), request.getParameter("motDePasse"));
            if (i == null) {
                request.setAttribute("erreur", "Email ou mot de passe incorrect.");
                request.getRequestDispatcher("/WEB-INF/views/internaute/login.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession(true);
                session.setAttribute("internaute", i);
                response.sendRedirect("VitrineServlet?action=afficher");
            }

        } else if ("deconnecter".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            response.sendRedirect("VitrineServlet?action=afficher");

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue : " + action);
        }
    }
}