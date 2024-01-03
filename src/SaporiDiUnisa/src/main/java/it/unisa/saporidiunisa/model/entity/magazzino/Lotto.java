package it.unisa.saporidiunisa.model.entity.magazzino;

import java.util.Date;

public class Lotto
{
    private int id;
    private float costo;
    private Date dataScadenza;
    private int quantita;
    private int quantitaAttuale;
    private Fornitura[] forniture;
    private Prodotto prodotto;
}
