package it.unisa.saporidiunisa.controller.scaffale.servlet;

import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Lotto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "AggiungiScaffale", value = "/AggiungiScaffale")
public class AggiungiScaffale extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Lotto> lottiMagazzino = ScaffaleController.visualizzaProdottiMagazzino();
        ArrayList<Esposizione> lottiScaffale = ScaffaleController.visualizzaProdottiScaffale();

        int qntScaffale = 0;

        for(Esposizione e : lottiScaffale) {
            if (Integer.parseInt(req.getParameter("qntScaffale" + e.getLotto().getId())) != 0){
                qntScaffale = Integer.parseInt(req.getParameter("qntScaffale" + e.getLotto().getId()));
                if(qntScaffale <= e.getLotto().getQuantitaAttuale()) {
                    ScaffaleController.aumentaEsposizione(qntScaffale, e);
                    ScaffaleController.diminuisciLotto(e.getLotto().getId(), qntScaffale);
                }
            }
        }

        for(Lotto l : lottiMagazzino) {
            if (Integer.parseInt(req.getParameter("qntMagazzino" + l.getId())) != 0){
                qntScaffale = Integer.parseInt(req.getParameter("qntMagazzino" + l.getId()));
                if(qntScaffale <= l.getQuantitaAttuale()) {
                    ScaffaleController.inserisciEsposizione(qntScaffale, l);
                    ScaffaleController.diminuisciLotto(l.getId(), qntScaffale);
                }
            }
        }

        lottiMagazzino = ScaffaleController.visualizzaProdottiMagazzino();
        lottiScaffale = ScaffaleController.visualizzaProdottiScaffale();
        req.setAttribute("lottiMagazzino", lottiMagazzino);
        req.setAttribute("lottiScaffale", lottiScaffale);

        RequestDispatcher dispatcher = req.getRequestDispatcher("view/aggiunta_scaffale.jsp");
        dispatcher.forward(req, resp);
    }

}
