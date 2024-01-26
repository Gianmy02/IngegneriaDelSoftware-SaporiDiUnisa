package it.unisa.saporidiunisa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dipendente
{
    public enum Ruolo
    {
        ADMIN,
        CASSIERE,
        MAGAZZINIERE,
        FINANZE,
    }

    private int id;
    private Ruolo ruolo;
    private String pin;
}