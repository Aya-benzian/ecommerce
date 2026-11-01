package ma.fstt.ecommerce.web;

import ma.fstt.ecommerce.dao.PanierDao;
import ma.fstt.ecommerce.entities.Internaute;
import ma.fstt.ecommerce.entities.Panier;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/PanierServlet")
public class PanierServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject private PanierDao panierDao;



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

        // The cart requires a logged-in internaute
        Internaute user = (Internaute) request.getSession().getAttribute("internaute");
        if (user == null) {
            response.sendRedirect("InternauteServlet?action=formLogin");
            return;
        }

        Panier panier = panierDao.findByInternaute(user.getId());
        if (panier == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Panier introuvable");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "voir";

        if ("voir".equals(action)) {
            double total = 0;
            for (var l : panier.getLignes()) {
                total += l.getProduit().getPrix() * l.getQuantite();
            }
            request.setAttribute("panier", panier);
            request.setAttribute("total", total);
            request.getRequestDispatcher("/WEB-INF/views/panier/panier.jsp").forward(request, response);

        } else if ("ajouter".equals(action)) {
            Long produitId = Long.valueOf(request.getParameter("produitId"));
            int quantite = Integer.parseInt(request.getParameter("quantite"));
            panierDao.ajouterProduit(panier.getId(), produitId, quantite);
            response.sendRedirect("PanierServlet?action=voir");

        } else if ("retirer".equals(action)) {
            Long ligneId = Long.valueOf(request.getParameter("ligneId"));
            panierDao.retirerLigne(panier.getId(), ligneId);
            response.sendRedirect("PanierServlet?action=voir");

        } else if ("vider".equals(action)) {
            panierDao.vider(panier.getId());
            response.sendRedirect("PanierServlet?action=voir");

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue : " + action);
        }
    }
}