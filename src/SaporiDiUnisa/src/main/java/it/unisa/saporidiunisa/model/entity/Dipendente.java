package it.unisa.saporidiunisa.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private int pin;
}