package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController;
import it.unisa.saporidiunisa.utils.Patterns;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        val session = request.getSession();

        if (session.getAttribute("dipendente") == null)
        {
            val pin = request.getParameter("pin");
            if (pin == null)
            {
                request.setAttribute("message", "Il pin è nullo");
                request.getRequestDispatcher("view/error.jsp").forward(request, response);
                return;
            }

            val matcherPin = Patterns.LOGIN_PIN.matcher(pin);
            if (!matcherPin.matches())
            {
                request.setAttribute("message", "Il pin non rispetta il formato richiesto");
                request.getRequestDispatcher("view/error.jsp").forward(request, response);
                return;
            }

            val dipendente = AutenticazioneController.login(pin);
            if (dipendente == null)
            {
                request.setAttribute("message", "Nessun dipendente corrisponde al pin inserito");
                request.getRequestDispatcher("view/error.jsp").forward(request, response);
                return;
            }

            session.setAttribute("dipendente", dipendente);

            val address = switch (dipendente.getRuolo())
            {
                case CASSIERE -> "view/select_Cassiere.jsp";
                case ADMIN -> "index.jsp";
                case FINANZE -> "view/select/finanze.jsp";
                case MAGAZZINIERE -> "view/select_Magazzino_Scaffale.jsp";
            };

            response.sendRedirect(address);
        }
    }
}

