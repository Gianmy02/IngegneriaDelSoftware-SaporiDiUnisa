package it.unisa.saporidiunisa.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

@Deprecated(since = "Introduce una vulnerabilità che permetterebbe ad un utente malintenzionato di accedere arbitrariamente a qualsiasi JSP", forRemoval = true)
@WebServlet(value = "/GoToServlet")
public class GoToServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val address = req.getParameter("address");
        req.getRequestDispatcher("view/"+address+".jsp").forward(req, resp);
    }
}
