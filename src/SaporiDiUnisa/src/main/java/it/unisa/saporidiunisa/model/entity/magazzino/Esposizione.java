package it.unisa.saporidiunisa.model.entity.magazzino;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Esposizione
{
    private final Prodotto prodotto;
    private final Lotto lotto;
    private int quantita;
}
