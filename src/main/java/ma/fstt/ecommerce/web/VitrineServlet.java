package ma.fstt.ecommerce.web;

import ma.fstt.ecommerce.dao.ProduitDao;
import ma.fstt.ecommerce.dao.VitrineDao;
import ma.fstt.ecommerce.entities.Produit;
import javax.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/VitrineServlet")
public class VitrineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject private ProduitDao produitDao;
    @Inject private VitrineDao vitrineDao;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "afficher";

        if ("afficher".equals(action)) {
            List<Produit> produits = produitDao.findAll();

            String vid = request.getParameter("vitrineId");
            if (vid != null && !vid.isEmpty()) {
                Long vitrineId = Long.valueOf(vid);
                produits = produits.stream()
                        .filter(p -> p.getVitrine() != null && vitrineId.equals(p.getVitrine().getId()))
                        .collect(Collectors.toList());
            }

            request.setAttribute("produits", produits);
            request.setAttribute("vitrines", vitrineDao.findAll());
            request.getRequestDispatcher("/WEB-INF/views/vitrine/index.jsp").forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue : " + action);
        }
    }
}