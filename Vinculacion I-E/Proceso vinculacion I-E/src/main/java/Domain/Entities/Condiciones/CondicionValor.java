package Domain.Entities.Condiciones;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

public class CondicionValor implements Condicion{

    @Override
    public boolean cumpleCondicion(Egreso egreso, Ingreso ingreso) {
        double montoIngreso = ingreso.getMonto();
        double montoEgresos = ingreso.getMontoEgresosAsociados();
        double montoEgreso = egreso.getMonto();

        return (montoIngreso - montoEgresos) >= montoEgreso;
    }
}
