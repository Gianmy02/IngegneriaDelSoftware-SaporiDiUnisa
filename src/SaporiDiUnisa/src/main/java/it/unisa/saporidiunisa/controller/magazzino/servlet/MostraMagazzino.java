package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Messages;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

/**
 * Servlet che mostra i prodotti presenti nel magazzino
 * @author Gianmarco Riviello
 */
@WebServlet(name = "MostraMagazzino", value = "/MostraMagazzino")
public class MostraMagazzino extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        val session = request.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() == Dipendente.Ruolo.CASSIERE || dipendente.getRuolo() == Dipendente.Ruolo.FINANZE)
        {
            Utils.dispatchError(Messages.NO_PERMISSIONS, request, response);
            return;
        }
        session.setAttribute("magazzino", MagazzinoController.visualizzaProdottiMagazzino());
        request.getRequestDispatcher("view/mostraProdottoLotti.jsp").forward(request, response);
    }
}
