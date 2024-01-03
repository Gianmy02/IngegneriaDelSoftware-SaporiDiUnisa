package it.unisa.saporidiunisa.model.entity.magazzino;

import java.util.Date;

public class Prodotto
{
    private int id;
    private String nome;
    private String marchio;
    private float prezzo;
    private float prezzoScontato;
    private Date inizioSconto;
    private Date fineSconto;
    private byte[] foto;
}
