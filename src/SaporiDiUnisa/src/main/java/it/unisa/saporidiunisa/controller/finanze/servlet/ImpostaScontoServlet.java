package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.finanze.FinanzeController;
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
            Utils.dispatchError(Messages.NO_PERMISSIONS, request, response);
            return;
        }

        val prodottoId = request.getParameter("prodotto");
        if (prodottoId == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("prodotto"), request, response);
            return;
        }

        val id = Utils.parseAsInteger(prodottoId);
        if (id == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("id"), request, response);
            return;
        }

        val prodotto = MagazzinoController.getProdottoById(id);
        if (prodotto == null)
        {
            Utils.dispatchError(Messages.NOT_FOUND.formatted("prodotto"), request, response);
            return;
        }

        val dataInizioSconto = request.getParameter("dataInizioSconto");
        if (dataInizioSconto == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("data inizio"), request, response);
            return;
        }

        val dataFineSconto = request.getParameter("dataFineSconto");
        if (dataFineSconto == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("data fine"), request, response);
            return;
        }

        val dataInizioScontoDate = Utils.parseAsLocalDate(dataInizioSconto);
        if (dataInizioScontoDate == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("data inizio"), request, response);
            return;
        }

        val dataFineScontoDate = Utils.parseAsLocalDate(dataFineSconto);
        if (dataFineScontoDate == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("data fine"), request, response);
            return;
        }

        if (dataInizioScontoDate.isBefore(LocalDate.now()))
        {
            Utils.dispatchError("La data di inizio è nel passato.", request, response);
            return;
        }

        if (dataFineScontoDate.isBefore(dataInizioScontoDate))
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("intervallo di date"), request, response);
            return;
        }

        val sconto = request.getParameter("sconto");
        if (sconto == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("sconto"), request, response);
            return;
        }

        val scontoInteger = Utils.parseAsInteger(sconto);
        if (scontoInteger == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("sconto"), request, response);
            return;
        }

        if (scontoInteger < 1)
        {
            Utils.dispatchError("Lo sconto è al di sotto del minimo consentito.", request, response);
            return;
        }

        if (scontoInteger > 100)
        {
            Utils.dispatchError("Lo sconto è al di sopra del massimo consentito.", request, response);
            return;
        }

        if (!FinanzeController.impostaSconto(prodotto, scontoInteger, dataInizioScontoDate, dataFineScontoDate))
        {
            Utils.dispatchError(Messages.FAIL, request, response);
            return;
        }

        session.setAttribute("prodotti", MagazzinoController.getAllProducts());
        request.getRequestDispatcher("view/finanze/visualizzaProdotti.jsp").forward(request, response);
    }
}
