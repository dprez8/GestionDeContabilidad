package Domain.Entities.Condiciones;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

public interface Condicion {
    public boolean cumpleCondicion(Egreso egreso, Ingreso ingreso);
}
