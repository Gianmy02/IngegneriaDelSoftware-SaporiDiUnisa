package it.unisa.saporidiunisa.model.entity.magazzino;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class Fornitura
{
    private int id;
    private LocalDate giorno;
    private ArrayList<Lotto> lotti;
}