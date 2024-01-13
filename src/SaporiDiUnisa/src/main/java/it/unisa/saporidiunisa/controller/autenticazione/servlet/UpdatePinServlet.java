package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Patterns;
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
        val session = request.getSession();
        val dipendente = (Dipendente)session.getAttribute("dipendente");

        if (dipendente != null && dipendente.getRuolo() == Dipendente.Ruolo.ADMIN)
        {
            val pin = request.getParameter("newPin");
            val ruolo = Dipendente.Ruolo.valueOf(request.getParameter("ruolo"));
            val matcherPin = Patterns.LOGIN_PIN.matcher(pin);

            if (!matcherPin.matches())
            {
                // TODO: errore da gestire
                return;
            }

            if (AutenticazioneController.modificaPin(pin.toCharArray(), ruolo))
            {
                System.out.println("Andato a buon fine");
            }
            else
            {
                // TODO: errore modifica pin
            }
        }
    }
}