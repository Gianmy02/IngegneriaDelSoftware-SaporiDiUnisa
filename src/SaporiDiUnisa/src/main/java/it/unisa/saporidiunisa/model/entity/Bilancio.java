package it.unisa.saporidiunisa.model.entity;

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
    private float perdite;
}