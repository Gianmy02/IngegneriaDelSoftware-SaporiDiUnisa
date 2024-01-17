package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

@WebServlet(name = "MostraLottiProdotto", value = "/MostraLottiProdotto")
public class MostraLottiProdotto extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        val session = req.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.MAGAZZINIERE)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, req, resp);
            return;
        }


    }

}