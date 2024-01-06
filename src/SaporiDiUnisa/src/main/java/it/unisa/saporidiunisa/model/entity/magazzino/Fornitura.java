package it.unisa.saporidiunisa.model.entity.magazzino;

import java.time.LocalDate;
import java.util.List;

public class Fornitura
{
    private int id;
    private LocalDate giorno;
    private List<Lotto> lotti;
}
