package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController;
import it.unisa.saporidiunisa.utils.Patterns;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        val session = request.getSession();

        if (session.getAttribute("dipendente") == null)
        {
            val pin = request.getParameter("pin");
            if (pin == null)
            {
                // TODO: errore da gestire
                return;
            }

            val matcherPin = Patterns.LOGIN_PIN.matcher(pin);
            if (!matcherPin.matches())
            {
                // TODO: errore da gestire
                return;
            }

            val dipendente = AutenticazioneController.login(pin);
            if (dipendente == null)
            {
                // TODO: errore da gestire
                return;
            }

            session.setAttribute("dipendente", dipendente);

            val address = switch (dipendente.getRuolo())
            {
                case CASSIERE, ADMIN, FINANZE -> "index.jsp";
                case MAGAZZINIERE -> "view/select_Magazzino_Scaffale.jsp";
            };

            response.sendRedirect(address);
        }
    }
}

