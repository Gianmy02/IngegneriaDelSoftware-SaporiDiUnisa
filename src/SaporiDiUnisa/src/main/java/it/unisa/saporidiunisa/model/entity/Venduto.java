package it.unisa.saporidiunisa.model.entity;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Venduto
{
    private Prodotto prodotto;
    @Positive(message = "La quantità deve essere maggiore di zero")
    private int quantita;
    @PositiveOrZero(message = "Il guadagno deve essere maggiore o uguale a 0")
    private float guadagno;
    @PositiveOrZero(message = "Il costo deve essere maggiore o uguale a 0")
    private float costo;
    private LocalDate giorno;
}