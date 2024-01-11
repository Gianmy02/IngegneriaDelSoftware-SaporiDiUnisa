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

@WebServlet(name = "UpdatePinServlet", value = "/UpdatePinServlet")
public class UpdatePinServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Dipendente d = (Dipendente) session.getAttribute("utenteLoggato");
        if(d != null && d.getRuolo() == Dipendente.Ruolo.ADMIN){
            String pin = String.valueOf(request.getParameter("newPin"));
            Dipendente.Ruolo ruolo = Dipendente.Ruolo.valueOf(request.getParameter("ruolo"));
            String regPin = "^[0-9]{4}$";
            Pattern patternPin = Pattern.compile(regPin);
            Matcher matcherPin = patternPin.matcher(pin);
            if(!matcherPin.matches()) {
                //errore da gestire
            }else {

                AutenticazioneController ac = new AutenticazioneController();
                if(ac.modificaPin(Integer.parseInt(pin), ruolo)){
                    System.out.println("Andato a buon fine");
                }else {
                    //errore modifica pin
                }

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
