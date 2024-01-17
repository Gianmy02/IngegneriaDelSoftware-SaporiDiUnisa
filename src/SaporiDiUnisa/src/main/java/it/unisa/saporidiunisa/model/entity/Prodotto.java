package it.unisa.saporidiunisa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public Prodotto(String nome, String marchio, float prezzo, byte[] foto) {
        this.nome = nome;
        this.marchio = marchio;
        this.prezzo = prezzo;
        this.foto = foto;
    }

    public boolean isSconto()
    {
        if(fineSconto == null)
            return false;
        return this.fineSconto.isAfter(LocalDate.now());
    }
}