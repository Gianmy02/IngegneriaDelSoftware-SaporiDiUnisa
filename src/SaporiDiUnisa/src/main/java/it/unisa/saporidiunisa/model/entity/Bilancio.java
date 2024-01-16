package it.unisa.saporidiunisa.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bilancio
{
    private float guadagno;
    private float spese;
    private float incasso;
    private float perdite;

    public float calculateUtile()
    {
        return this.guadagno - this.perdite;
    }
}