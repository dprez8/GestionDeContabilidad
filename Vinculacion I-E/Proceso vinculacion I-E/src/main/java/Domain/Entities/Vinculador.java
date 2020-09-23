package Domain.Entities;

import Domain.Entities.Condiciones.Condicion;
import Domain.Entities.Criterios.Criterio;
import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.List;

public class Vinculador {
    private List<Ingreso> ingresos;
    private List<Egreso> egresos;
    private List<Condicion> condiciones;
    private Criterio criterio;

    public void vincular() {
        criterio.setEgresos(egresos);
        criterio.setIngresos(ingresos);
        criterio.ordenar();

        while(!criterio.termino()) {
            Egreso egreso = criterio.getEgreso();
            Ingreso ingreso = criterio.getIngreso();

            if(condiciones.stream().allMatch(unaCondicion->unaCondicion.cumpleCondicion(egreso, ingreso))) {
                // Se puede vincular el egreso al ingreso
                ingreso.addEgresosAsociados(egreso);
            }
        }
    }
}
