package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController;
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

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        val session = request.getSession();

        if (session.getAttribute("dipendente") == null)
        {
            val pin = request.getParameter("pin");
            if (pin == null)
            {
                Utils.dispatchError(Errors.INVALID_FIELD.formatted("pin"), request, response);
                return;
            }

            val matcherPin = Patterns.LOGIN_PIN.matcher(pin);
            if (!matcherPin.matches())
            {
                Utils.dispatchError(Errors.INVALID_FORMAT.formatted("pin"), request, response);
                return;
            }

            val dipendente = AutenticazioneController.login(pin);
            if (dipendente == null)
            {
                Utils.dispatchError(Errors.NOT_FOUND.formatted("dipendente"), request, response);
                return;
            }

            session.setAttribute("dipendente", dipendente);

            val address = switch (dipendente.getRuolo())
            {
                case CASSIERE -> "view/select_Cassiere.jsp";
                case ADMIN -> "view/select_admin.jsp";
                case FINANZE -> "view/select/finanze.jsp";
                case MAGAZZINIERE -> "view/select_Magazzino_Scaffale.jsp";
            };

            response.sendRedirect(address);
        }
    }
}

