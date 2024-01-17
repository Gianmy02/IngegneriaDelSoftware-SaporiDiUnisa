package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Patterns;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

@WebServlet(name = "updatePinServlet", value = "/update-pin-servlet")
public class UpdatePinServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        val dipendente = (Dipendente)request.getSession().getAttribute("dipendente");

        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.ADMIN)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, request, response);
            return;
        }

        val pin = request.getParameter("newPin");
        val ruolo = Dipendente.Ruolo.valueOf(request.getParameter("ruolo"));

        if (!Patterns.LOGIN_PIN.matcher(pin).matches())
        {
            Utils.dispatchError(Errors.INVALID_FORMAT.formatted("nuovo pin"), request, response);
            return;
        }

        if (!AutenticazioneController.modificaPin(pin, ruolo))
        {
            Utils.dispatchError("Non è stato possibile completare l'operazione.", request, response);
            return;
        }

        request.getRequestDispatcher("view/select/select_admin.jsp").forward(request, response);
    }
}