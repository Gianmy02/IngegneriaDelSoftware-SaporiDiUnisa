package it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();

        val dipendente = (Dipendente) session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.MAGAZZINIERE){
            Utils.dispatchError(Errors.NO_PERMISSIONS, req, resp);
            return;
        }

        val fornitura = (Fornitura) session.getAttribute("fornitura");
        session.removeAttribute("fornitura");

        if(MagazzinoController.registraFornitura(fornitura))
            Utils.dispatchSuccess("Fornitura registrata con successo", req, resp);
        else
            Utils.dispatchError(Errors.GENERIC, req, resp);

    }
}
