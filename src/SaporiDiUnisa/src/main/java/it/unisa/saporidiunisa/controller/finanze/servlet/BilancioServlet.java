package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.finanze.FinanzeController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

@WebServlet(name = "BilancioServet", value = "/bilancio-servlet")
public class BilancioServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if(d!= null && (d.getRuolo() == Dipendente.Ruolo.FINANZE)){
            val b = FinanzeController.visualizzaBilancio();
            //TODO: dispatch alla pagina del bilancio
        }else{
            req.setAttribute("message", "Permessi non concessi per questa pagina");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
        }
    }
}
