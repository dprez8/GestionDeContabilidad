package Domain.Entities.Condiciones;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.time.LocalDate;

public class CondicionEntreFechas implements Condicion{

    @Override
    public boolean cumpleCondicion(Egreso egreso, Ingreso ingreso) {
        LocalDate fechaDesde = ingreso.getFecha();
        LocalDate fechaHasta = ingreso.getFechaAceptacionEgreso();
        LocalDate fechaEgreso = egreso.getFecha();
        System.out.println((fechaEgreso.isEqual(fechaDesde) || fechaEgreso.isAfter(fechaDesde)) &&
                (fechaEgreso.isBefore(fechaHasta)|| fechaEgreso.isEqual(fechaHasta)));
        return  (fechaEgreso.isEqual(fechaDesde) || fechaEgreso.isAfter(fechaDesde)) &&
                (fechaEgreso.isBefore(fechaHasta)|| fechaEgreso.isEqual(fechaHasta)); //Comparo que la fecha del egreso este en el periodo de las fechas indicadas
    }
}
