package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.finanze.FinanzeController;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        val session = req.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null)
        {
            // TODO: errore
            return;
        }

        if (dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            // TODO: errore
            return;
        }

        val dataInizioSconto = req.getParameter("dataInizioSconto");
        if (dataInizioSconto == null)
        {
            // TODO: errore
            return;
        }

        val dataFineSconto = req.getParameter("dataFineSconto");
        if (dataFineSconto == null)
        {
            // TODO: errore
            return;
        }

        val dataInizioScontoDate = LocalDate.parse(dataInizioSconto);
        val dataFineScontoDate = LocalDate.parse(dataFineSconto);
        if (!dataFineScontoDate.isAfter(dataInizioScontoDate))
        {
            //TODO: dispatch alla pagina di errore
        }

        val sconto = Integer.parseInt(req.getParameter("sconto"));
        if (sconto < 1 || sconto > 100)
        {
            //TODO: dispatch alla pagina di errore
        }

        val prodottoId = req.getParameter("prodotto");
        if (prodottoId == null)
        {
            // TODO: errore
            return;
        }

        val id = Integer.parseInt(prodottoId);
        val prodotto = MagazzinoController.getProdottoById(id);
        if (prodotto == null)
        {
            // TODO: errore
            return;
        }

        if (FinanzeController.impostaSconto(prodotto, sconto, dataInizioScontoDate, dataFineScontoDate))
        {
            //TODO: dispatch alla pagina di conferma
        }
        else
        {
            //TODO: dispatch alla pagina di errore
        }
    }
}
