package it.unisa.saporidiunisa.controller.vendite.servlet;

import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Messages;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author Gianmarco Riviello
 * La servlet <code>MostraProdottiCassiereServlet</code> restituisce nella sessione i prodotti esposti nel supermercato per la vista del cassiere
 */
@WebServlet(name = "MostraProdottiCassiereServlet", value = "/MostraProdottiCassiereServlet")
public class MostraProdottiCassiereServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if (d == null || d.getRuolo() != Dipendente.Ruolo.CASSIERE) {
            Utils.dispatchError(Messages.NO_PERMISSIONS, req, resp);
            return;
        }
        session.setAttribute("prodottiEsposti", VenditaController.visualizzaProdottiEsposti());
        req.getRequestDispatcher("view/cassiere/vendita.jsp").forward(req, resp);
    }
}
