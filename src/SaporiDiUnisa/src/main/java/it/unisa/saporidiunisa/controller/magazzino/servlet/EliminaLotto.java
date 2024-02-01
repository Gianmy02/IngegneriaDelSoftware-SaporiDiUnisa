package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Messages;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

/**
 * Servlet eseguita da un admin per l'eliminazione di un lotto
 * @author Gianmarco Riviello
 */
@WebServlet(name = "EliminaLotto", value = "/EliminaLotto")
public class EliminaLotto extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        val dipendente = (Dipendente)request.getSession().getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.ADMIN)
        {
            Utils.dispatchError(Messages.NO_PERMISSIONS, request, response);
            return;
        }

        val lottoId = request.getParameter("lotto");
        if (lottoId == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("lotto"), request, response);
            return;
        }

        val id = Integer.parseInt(lottoId);
        if(!MagazzinoController.eliminaLotto(id)){
            Utils.dispatchError("Errore nell'eliminazione del lotto", request, response);
            return;
        }

        request.getRequestDispatcher("view/select/admin.jsp").forward(request, response);
    }

}
