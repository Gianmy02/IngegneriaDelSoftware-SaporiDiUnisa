package it.unisa.saporidiunisa.model.entity.magazzino;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Venduto
{
    private Prodotto prodotto;
    private int quantita;
    private float guadagno;
    private float costo;
}
