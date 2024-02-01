package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * La servlet <code>logoutServlet</code> consente di uscire dall'account
 * @author Antonio Facchiano, Gianmarco Riviello
 */
@WebServlet(name = "logoutServlet", value = "/logout-servlet")
public class LogoutServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getSession().invalidate();
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
