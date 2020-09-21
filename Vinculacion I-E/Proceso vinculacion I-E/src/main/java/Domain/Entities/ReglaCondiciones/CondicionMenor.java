package Domain.Entities.ReglaCondiciones;

import java.time.LocalDate;

public class CondicionMenor implements Condicion{

    @Override
    public boolean cumplisCon(LocalDate fechaInicio, LocalDate fechaLimite, LocalDate fechaEgreso) {
        return fechaEgreso.isAfter(fechaInicio) && fechaEgreso.isBefore(fechaLimite); //Comparo que la fecha del egreso este en el periodo de las fechas indicadas
    }
}
