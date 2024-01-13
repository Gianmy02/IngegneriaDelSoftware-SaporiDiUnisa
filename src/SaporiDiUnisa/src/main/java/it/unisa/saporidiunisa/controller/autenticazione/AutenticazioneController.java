package it.unisa.saporidiunisa.controller.autenticazione;

import it.unisa.saporidiunisa.model.dao.DipendenteDAO;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import lombok.val;

/**
 * @author Riviello Gianmarco
 * Un oggetto <code>AutenticazioneController</code> mette il relazione il dabatabase con tutti i metodi messi a disposizione per l'autenticazione. Da richiamare nelle servlet
 */
public class AutenticazioneController
{
    /**
     * Effettua il login dei dipendenti verificando i propri pìn
     * @param pin
     * @return Dipendente loggato oppure null nel caso non esista nel db
     */
    public static Dipendente login(char[] pin)
    {
        val dipendenteDAO = new DipendenteDAO();
        return dipendenteDAO.findDipendenteByPin(pin);
    }

    /**
     * Effettua la modifica del pin in un ruolo da parte dell'admin.
     * @param newPin pin nuovo da modificare nel dipendente
     * @param ruolo ruolo al quale modificare il pin
     * @return booleano di conferma
     */
    public static boolean modificaPin(char[] newPin, Dipendente.Ruolo ruolo)
    {
        val dipendenteDAO = new DipendenteDAO();
        return dipendenteDAO.updatePin(newPin,ruolo);
    }
}
