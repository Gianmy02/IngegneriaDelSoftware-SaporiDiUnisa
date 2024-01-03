package it.unisa.saporidiunisa.model.entity.magazzino;

public class Dipendente
{
    private enum Ruolo
    {
        CASSIERE,
        ADMIN,
        MAGAZZINIERE,
        FINANZE,
    }

    private int id;
    private Ruolo ruolo;
    private char[] pin;
}
