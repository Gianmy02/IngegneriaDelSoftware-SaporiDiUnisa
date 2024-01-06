package it.unisa.saporidiunisa.model.entity.magazzino;

import java.time.LocalDate;

public class Lotto
{
    private int id;
    private float costo;
    private LocalDate dataScadenza;
    private int quantita;
    private int quantitaAttuale;
    private Fornitura fornitura;
    private Prodotto prodotto;
}
