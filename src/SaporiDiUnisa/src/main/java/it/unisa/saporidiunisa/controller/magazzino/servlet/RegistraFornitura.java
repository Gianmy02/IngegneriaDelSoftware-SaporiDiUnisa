package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;

import java.io.IOException;

@WebServlet(name = "RegistraFornitura", value = "/RegistraFornitura")
public class RegistraFornitura extends HttpServlet {
    final MagazzinoController magazzinoController = new MagazzinoController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // prendo i dati dalla sessione
        final HttpSession session = req.getSession();
        val dipendente = (Dipendente)req.getSession().getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.MAGAZZINIERE)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, req, resp);
            return;
        }
        val fornitura = (Fornitura) session.getAttribute("fornitura");

        if(magazzinoController.registraFornitura(fornitura))
            req.setAttribute("success", "Fornitura registrata con successo");
        else
            req.setAttribute("error", "Errore durante la registrazione della fornitura");

        session.removeAttribute("fornitura");

        req.getRequestDispatcher("/view/registraFornitura.jsp").forward(req, resp);
    }
}
