package it.unisa.saporidiunisa.model.entity.magazzino;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
