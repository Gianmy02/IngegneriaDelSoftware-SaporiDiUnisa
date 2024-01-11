package it.unisa.saporidiunisa.controller.autenticazione;

import it.unisa.saporidiunisa.model.dao.AutenticazioneDAO;
import it.unisa.saporidiunisa.model.entity.Dipendente;

public class AutenticazioneController
{
    public Dipendente login(int pin)
    {
        AutenticazioneDAO adao = new AutenticazioneDAO();
        return adao.login(pin);
    }


    public boolean modificaPin(int newPin, Dipendente.Ruolo ruolo)
    {
        AutenticazioneDAO ad = new AutenticazioneDAO();
        return ad.updatePin(newPin,ruolo);
    }
}
