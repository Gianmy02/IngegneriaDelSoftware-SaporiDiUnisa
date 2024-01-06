package it.unisa.saporidiunisa.model.entity.magazzino;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Esposizione
{
    private Prodotto prodotto;
    private Lotto lotto;
    private int quantita;
}