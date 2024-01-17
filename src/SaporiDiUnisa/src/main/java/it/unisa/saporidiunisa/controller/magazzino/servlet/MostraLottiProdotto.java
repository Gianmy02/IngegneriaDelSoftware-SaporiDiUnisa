package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@WebServlet(name = "MostraLottiProdotto", value = "/MostraLottiProdotto")
public class MostraLottiProdotto extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        val session = req.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() == Dipendente.Ruolo.CASSIERE || dipendente.getRuolo() == Dipendente.Ruolo.FINANZE)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, req, resp);
            return;
        }

        int id;

        try {
            id = Integer.parseInt(req.getParameter("prodotto"));
        }
        catch (NumberFormatException ex)
        {
            Utils.dispatchError(Errors.INVALID_FORMAT.formatted("prodotto"), req, resp);
            return;
        }

        boolean trovato = false;
        Prodotto prodotto = new Prodotto();
        HashMap<Prodotto, ArrayList<Lotto>> lottiMagazzino = (HashMap<Prodotto, ArrayList<Lotto>>) session.getAttribute("magazzino");
        Set<Prodotto> prodotti = lottiMagazzino.keySet();

        for (Prodotto p : prodotti)
            if(p.getId() == id && !trovato) {
                trovato = true;
                prodotto = p;
            }


        if(trovato)
        {
            req.setAttribute("prodotto", prodotto);
            req.getRequestDispatcher("view/mostraLotti.jsp").forward(req, resp);
        }
        else
        {
            req.setAttribute("message", "Prodotto non trovato");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
        }


    }

}