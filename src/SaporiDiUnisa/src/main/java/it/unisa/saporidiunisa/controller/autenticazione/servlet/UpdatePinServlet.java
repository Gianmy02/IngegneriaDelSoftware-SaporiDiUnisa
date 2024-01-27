package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Messages;
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
            Utils.dispatchError(Messages.NO_PERMISSIONS, request, response);
            return;
        }

        val pin = request.getParameter("newPin");
        if (pin == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("nuovo pin"), request, response);
            return;
        }

        if (!Patterns.LOGIN_PIN.matcher(pin).matches())
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("nuovo pin"), request, response);
            return;
        }

        val ruolo = request.getParameter("ruolo");
        if (ruolo == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("ruolo"), request, response);
            return;
        }

        val ruoloValue = Utils.getEnum(Dipendente.Ruolo.class, ruolo);
        if (ruoloValue == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("ruolo"), request, response);
            return;
        }

        if (!AutenticazioneController.modificaPin(pin, ruoloValue))
        {
            Utils.dispatchError("Non è stato possibile completare l'operazione per pin uguale ad altri ruoli oppure allo stesso.", request, response);
            return;
        }

        Utils.dispatchSuccess(Messages.SUCCESS, request, response);
    }
}