package it.unisa.saporidiunisa.controller.scaffale.servlet;

import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "EliminaScaduti", value = "/EliminaScaduti")
public class EliminaScaduti extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ScaffaleController.eliminaScadutiScaffale();

        ArrayList<Esposizione> lottiScaffale = ScaffaleController.visualizzaProdottiScaffale();
        ArrayList<Esposizione> lottiScaffaleScaduti = ScaffaleController.visualizzaProdottiScaffaleScaduti();
        req.setAttribute("lottiScaffale", lottiScaffale);
        req.setAttribute("lottiScaffaleScaduti", lottiScaffaleScaduti);

        RequestDispatcher dispatcher = req.getRequestDispatcher("view/visualizza_scaffale.jsp");
        dispatcher.forward(req, resp);
    }

}
