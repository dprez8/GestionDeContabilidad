package Domain.Entities;

import java.time.LocalDate;

public interface Condicion {
    public boolean cumplisCon(LocalDate fechaInicio, LocalDate fechaLimite, LocalDate fechaEgreso);
}
