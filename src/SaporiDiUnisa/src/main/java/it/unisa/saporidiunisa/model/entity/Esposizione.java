package it.unisa.saporidiunisa.model.entity;

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