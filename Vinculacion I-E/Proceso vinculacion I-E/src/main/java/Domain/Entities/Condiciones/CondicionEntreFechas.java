package Domain.Entities.Condiciones;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.time.LocalDate;

public class CondicionEntreFechas implements Condicion{

    private int diasDesde;
    private int diasHasta;

    @Override
    public boolean cumpleCondicion(Egreso egreso, Ingreso ingreso) {
        LocalDate fechaDesde = ingreso.getFecha().plusDays(diasDesde);
        LocalDate fechaHasta = ingreso.getFecha().plusDays(diasHasta);
        LocalDate fechaEgreso = egreso.getFecha();

        return fechaEgreso.isAfter(fechaDesde) && fechaEgreso.isBefore(fechaHasta); //Comparo que la fecha del egreso este en el periodo de las fechas indicadas
    }

    public void setDiasDesde(int diasDesde) {
        this.diasDesde = diasDesde;
    }

    public void setDiasHasta(int diasHasta) {
        this.diasHasta = diasHasta;
    }
}
