package it.unisa.saporidiunisa.model.form;

import jakarta.validation.constraints.*;
import lombok.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.time.LocalDate;
import java.util.Set;

/**
 * Validare il form di aggiunta lotto
 * @author Salvatore Ruocco
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LottoForm {

    @NonNull
    @NotBlank(message = "Il nome non può essere vuoto")
    @NotEmpty(message = "Il nome non può essere vuoto")
    @Size(min = 2, max = 255, message = "Il nome deve essere compreso tra 2 e 255 caratteri")
    public String nome;

    @NonNull
    @NotBlank(message = "Il marchio non può essere vuoto")
    @NotEmpty(message = "Il marchio non può essere vuoto")
    @Size(min = 2, max = 255, message = "Il marchio deve essere compreso tra 2 e 255 caratteri")
    public String marchio;

    @NonNull
    @NotBlank(message = "Il prezzo non può essere vuoto")
    @NotEmpty(message = "Il prezzo non può essere vuoto")
    @Positive(message = "Il prezzo deve essere maggiore di 0")
    public float prezzo;

    @NonNull
    @NotBlank(message = "La quantità non può essere vuota")
    @NotEmpty(message = "La quantità non può essere vuota")
    @Positive(message = "La quantità deve essere maggiore di 0")
    public int quantita;

    @NonNull
    @NotBlank(message = "La data di scadenza non può essere vuota")
    @NotEmpty(message = "La data di scadenza non può essere vuota")
    @Future(message = "La data di scadenza deve essere superiore a quella odierna")
    public LocalDate dataScadenza;

    public String validate() {
        val validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<LottoForm>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            val s = new StringBuilder();
            violations.forEach(v -> s.append(v.getMessage()).append("\n"));
            return s.toString();
        }
        return null;
    }
}
