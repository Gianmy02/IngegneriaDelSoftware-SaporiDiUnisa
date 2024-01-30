package it.unisa.saporidiunisa.controller.finanze.servlet;

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
 * @author Antonio Facchiano
 * La servlet <code>mostraProdottoFinanzeServlet</code> mostra il prodotto scelto
 */
@WebServlet(name = "mostraProdottoFinanzeServlet", value = "/mostra-prodotto-finanze-servlet")
public class MostraProdottoFinanzeServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        val session = request.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            Utils.dispatchError(Messages.NO_PERMISSIONS, request, response);
            return;
        }

        val id = request.getParameter("prodotto");
        if (id == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("prodotto"), request, response);
            return;
        }

        val idInteger = Utils.parseAsInteger(id);
        if (idInteger == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("prodotto"), request, response);
            return;
        }

        session.setAttribute("prodottoSelezionato", MagazzinoController.getProdottoById(idInteger));
        request.getRequestDispatcher("view/finanze/impostaSconto.jsp").forward(request, response);
    }
}
