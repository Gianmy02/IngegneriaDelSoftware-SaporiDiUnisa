package it.unisa.saporidiunisa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Fornitura
{
    private int id;
    private LocalDate giorno;
    private ArrayList<Lotto> lotti;

    public Fornitura() {
        this.giorno = LocalDate.now();
        this.lotti = new ArrayList<>();
    }
}