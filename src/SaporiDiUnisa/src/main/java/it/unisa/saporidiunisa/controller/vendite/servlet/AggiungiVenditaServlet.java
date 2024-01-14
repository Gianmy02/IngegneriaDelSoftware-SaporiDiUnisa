package it.unisa.saporidiunisa.controller.vendite.servlet;

import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Venduto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "aggiungiVenditaServlet", value = "/aggiungiVenditaServlet")
public class AggiungiVenditaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if(d != null && d.getRuolo() == Dipendente.Ruolo.CASSIERE){
            ArrayList<Venduto> selezionati = (ArrayList<Venduto>) session.getAttribute("prodottiSelezionati");
            VenditaController vc = new VenditaController();
            if(vc.venditaProdotti(selezionati)){
                //TODO: vendita confermata
            }else {
                //TODO: errore nella vendita
            }
        }
        else{
            //TODO: errore da gestire, mandare ad una pagina di errore
        }

    }
}