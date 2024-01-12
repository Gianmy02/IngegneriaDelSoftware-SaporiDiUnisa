package it.unisa.saporidiunisa.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Venduto
{
    private Prodotto prodotto;
    private int quantita;
    private float guadagno;
    private float costo;
    private LocalDate giorno;
}