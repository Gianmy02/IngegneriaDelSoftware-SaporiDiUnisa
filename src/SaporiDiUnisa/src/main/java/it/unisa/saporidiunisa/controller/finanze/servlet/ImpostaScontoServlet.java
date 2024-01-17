package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.finanze.FinanzeController;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "impostaScontoServlet", value = "/imposta-sconto-servlet")
public class ImpostaScontoServlet extends HttpServlet
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

        val dataInizioSconto = request.getParameter("dataInizioSconto");
        if (dataInizioSconto == null)
        {
            Utils.dispatchError(Errors.INVALID_FIELD.formatted("data inizio"), request, response);
            return;
        }

        val dataFineSconto = request.getParameter("dataFineSconto");
        if (dataFineSconto == null)
        {
            Utils.dispatchError(Errors.INVALID_FIELD.formatted("data fine"), request, response);
            return;
        }

        val dataInizioScontoDate = LocalDate.parse(dataInizioSconto);
        val dataFineScontoDate = LocalDate.parse(dataFineSconto);
        if (!dataFineScontoDate.isAfter(dataInizioScontoDate))
        {
            Utils.dispatchError("Le 2 date non coincidono", request, response);
            return;
        }

        val sconto = Integer.parseInt(request.getParameter("sconto"));
        if (sconto < 1 || sconto > 100)
        {
            Utils.dispatchError("Sconto impostato con una percentuale non corretta", request, response);
            return;
        }

        val prodottoId = request.getParameter("prodotto");
        if (prodottoId == null)
        {
            Utils.dispatchError(Errors.INVALID_FORMAT.formatted("prodotto"), request, response);
            return;
        }

        val id = Integer.parseInt(prodottoId);
        val prodotto = MagazzinoController.getProdottoById(id);
        if (prodotto == null)
        {
            Utils.dispatchError(Errors.NOT_FOUND.formatted("prodotto"), request, response);
            return;
        }

        if (!FinanzeController.impostaSconto(prodotto, sconto, dataInizioScontoDate, dataFineScontoDate))
        {
            Utils.dispatchError("Errore nell'impostazione dello sconto", request, response);
            return;
        }

        session.setAttribute("prodotti", FinanzeController.visualizzaProdotti());
        request.getRequestDispatcher("view/finanze/visualizzaProdotti.jsp").forward(request, response);
    }
}
