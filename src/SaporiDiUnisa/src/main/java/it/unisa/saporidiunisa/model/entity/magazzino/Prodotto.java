package it.unisa.saporidiunisa.model.entity.magazzino;

import java.time.LocalDate;

public class Prodotto
{
    private int id;
    private String nome;
    private String marchio;
    private float prezzo;
    private float prezzoScontato;
    private LocalDate inizioSconto;
    private LocalDate fineSconto;
    private byte[] foto;
}
