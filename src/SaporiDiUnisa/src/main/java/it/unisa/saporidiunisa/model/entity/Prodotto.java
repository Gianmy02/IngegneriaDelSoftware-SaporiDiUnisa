package it.unisa.saporidiunisa.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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

    public boolean isSconto()
    {
        return this.prezzoScontato != 0;
    }
}