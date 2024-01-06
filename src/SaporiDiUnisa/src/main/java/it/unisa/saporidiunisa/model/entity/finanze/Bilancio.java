package it.unisa.saporidiunisa.model.entity.finanze;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Bilancio
{
    private float guadagno;
    private float spese;
    private float incasso;
    private LocalDate dataInizio;
    private LocalDate dataFine;
}