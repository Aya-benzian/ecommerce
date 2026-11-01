package ma.fstt.ecommerce.web;

import ma.fstt.ecommerce.dao.CategorieDao;
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

@WebServlet("/ProduitServlet")
public class ProduitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject private ProduitDao produitDao;
    @Inject private CategorieDao categorieDao;
    @Inject private VitrineDao vitrineDao;


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
        if (action == null) action = "lister";

        if ("lister".equals(action)) {
            String mc = request.getParameter("motCle");
            request.setAttribute("produits",
                    (mc == null || mc.trim().isEmpty()) ? produitDao.findAll() : produitDao.search(mc));
            request.getRequestDispatcher("/WEB-INF/views/produit/list.jsp").forward(request, response);

        } else if ("nouveau".equals(action)) {
            chargerListes(request);
            request.getRequestDispatcher("/WEB-INF/views/produit/form.jsp").forward(request, response);

        } else if ("editer".equals(action)) {
            Long id = Long.valueOf(request.getParameter("id"));
            request.setAttribute("produit", produitDao.findById(id));
            chargerListes(request);
            request.getRequestDispatcher("/WEB-INF/views/produit/form.jsp").forward(request, response);

        } else if ("ajouter".equals(action)) {
            produitDao.save(lireProduit(request, new Produit()));
            response.sendRedirect("ProduitServlet?action=lister");

        } else if ("modifier".equals(action)) {
            Produit p = produitDao.findById(Long.valueOf(request.getParameter("id")));
            produitDao.update(lireProduit(request, p));
            response.sendRedirect("ProduitServlet?action=lister");

        } else if ("supprimer".equals(action)) {
            produitDao.delete(Long.valueOf(request.getParameter("id")));
            response.sendRedirect("ProduitServlet?action=lister");

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue : " + action);
        }
    }

    private void chargerListes(HttpServletRequest request) {
        request.setAttribute("categories", categorieDao.findAll());
        request.setAttribute("vitrines", vitrineDao.findAll());
    }

    private Produit lireProduit(HttpServletRequest r, Produit p) {
        p.setDesignation(r.getParameter("designation"));
        p.setDescription(r.getParameter("description"));
        p.setPrix(Double.parseDouble(r.getParameter("prix")));
        p.setStock(Integer.parseInt(r.getParameter("stock")));
        p.setImage(r.getParameter("image"));

        String cat = r.getParameter("categorieId");
        p.setCategorie((cat == null || cat.isEmpty()) ? null : categorieDao.findById(Long.valueOf(cat)));

        String vit = r.getParameter("vitrineId");
        p.setVitrine((vit == null || vit.isEmpty()) ? null : vitrineDao.findById(Long.valueOf(vit)));
        return p;
    }
}