package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("utenteLoggato") == null){
            AutenticazioneController ac = new AutenticazioneController();
            String pin = String.valueOf(request.getParameter("pin"));
            String regPin = "^[0-9]{4}$";
            Pattern patternPin = Pattern.compile(regPin);
            Matcher matcherPin = patternPin.matcher(pin);
            if(!matcherPin.matches()) {
                //errore da gestire
            }else
            {
                Dipendente d = ac.login(Integer.parseInt(pin));
                if(d == null) {
                    //errore da gestire
                }else {
                        session.setAttribute("utenteLoggato", d);
                }

                /*
                RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                dispatcher.forward(request, response);*/
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}

