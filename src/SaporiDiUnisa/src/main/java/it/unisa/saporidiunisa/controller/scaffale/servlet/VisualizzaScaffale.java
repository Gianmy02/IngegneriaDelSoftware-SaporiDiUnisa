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

@WebServlet(name = "VisualizzaScaffale", value = "/VisualizzaScaffale")
public class VisualizzaScaffale extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String address = req.getParameter("address");
        ArrayList<Esposizione> lottiScaffale = ScaffaleController.visualizzaProdottiScaffale();
        req.setAttribute("lottiScaffale", lottiScaffale);

        if(address.equalsIgnoreCase("aggiunta_scaffale")){
            ArrayList<Lotto> lottiMagazzino = ScaffaleController.visualizzaProdottiMagazzino();
            req.setAttribute("lottiMagazzino", lottiMagazzino);
        }
        else
        {
            ArrayList<Esposizione> lottiScaffaleScaduti = ScaffaleController.visualizzaProdottiScaffaleScaduti();
            req.setAttribute("lottiScaffaleScaduti", lottiScaffaleScaduti);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("view/" + address + ".jsp");
        dispatcher.forward(req, resp);
    }
}
