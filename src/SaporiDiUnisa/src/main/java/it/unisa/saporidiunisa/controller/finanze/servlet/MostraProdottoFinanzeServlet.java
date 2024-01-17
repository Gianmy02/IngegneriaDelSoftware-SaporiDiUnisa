package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

@WebServlet(name = "MostraProdottoFinanzeServlet", value = "/mostra-prodotto-finanze-servlet")
public class MostraProdottoFinanzeServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        val session = request.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, request, response);
            return;
        }

        val id = request.getParameter("prodotto");
        if (id == null)
        {
            Utils.dispatchError("Errore sul prodotto richiesto", request, response);
            return;
        }

        session.setAttribute("prodottoSelezionato", MagazzinoController.getProdottoById(Integer.parseInt(id)));
        request.getRequestDispatcher("view/finanze/impostaSconto.jsp").forward(request, response);
    }
}
