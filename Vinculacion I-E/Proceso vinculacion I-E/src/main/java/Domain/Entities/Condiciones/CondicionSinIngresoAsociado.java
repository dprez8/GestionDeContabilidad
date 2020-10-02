package Domain.Entities.Condiciones;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

public class CondicionSinIngresoAsociado implements Condicion{

    @Override
    public boolean cumpleCondicion(Egreso egreso, Ingreso ingreso) {
        return  egreso.getIngresoAsociado() == null;
    }
}
