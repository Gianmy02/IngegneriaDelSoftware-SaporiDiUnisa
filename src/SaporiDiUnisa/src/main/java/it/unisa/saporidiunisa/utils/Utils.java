package it.unisa.saporidiunisa.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Utils
{
    public static void dispatchError(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("message", message);
        request.getRequestDispatcher("view/error.jsp").forward(request, response);
    }
}
