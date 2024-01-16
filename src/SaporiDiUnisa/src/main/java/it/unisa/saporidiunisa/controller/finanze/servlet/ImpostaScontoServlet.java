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
            req.setAttribute("message", "Permessi non concessi per questa pagina");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
            return;
        }


        val dataInizioSconto = req.getParameter("dataInizioSconto");
        if (dataInizioSconto == null)
        {
            req.setAttribute("message", "Data Inizio non inserita");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
            return;
        }

        val dataFineSconto = req.getParameter("dataFineSconto");
        if (dataFineSconto == null)
        {
            req.setAttribute("message", "Data Fine Sconto non inserita");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
            return;
        }

        val dataInizioScontoDate = LocalDate.parse(dataInizioSconto);
        val dataFineScontoDate = LocalDate.parse(dataFineSconto);
        if (!dataFineScontoDate.isAfter(dataInizioScontoDate))
        {
            req.setAttribute("message", "Le 2 date non coincidono");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
            return;
        }

        val sconto = Integer.parseInt(req.getParameter("sconto"));
        if (sconto < 1 || sconto > 100)
        {
            req.setAttribute("message", "Sconto impostato con una percentuale non corretta");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
            return;
        }

        val prodottoId = req.getParameter("prodotto");
        if (prodottoId == null)
        {
            req.setAttribute("message", "Errore sul prodotto");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
            return;
        }

        val id = Integer.parseInt(prodottoId);
        val prodotto = MagazzinoController.getProdottoById(id);
        if (prodotto == null)
        {
            req.setAttribute("message", "Errore sul prodotto non trovato");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
            return;
        }

        if (FinanzeController.impostaSconto(prodotto, sconto, dataInizioScontoDate, dataFineScontoDate))
        {
            //TODO: dispatch alla pagina di conferma
        }
        else
        {
            req.setAttribute("message", "Errore nell'impostazione dello sconto");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
        }
    }
}
