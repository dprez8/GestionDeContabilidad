package Domain.Entities.Criterios;

import Domain.Entities.Criterios.CriterioUnico;
import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.ReglaCondiciones.Regla;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class OrdenValorPrimeroEgreso implements CriterioUnico {

    @Override
    public void aplicate(Regla regla) {
        List<Egreso> egresosOrdenados = regla.getEgresos();
        egresosOrdenados.sort(Comparator.comparing(Egreso::getMonto));

        List<Ingreso> ingresosOrdenados = regla.getIngresos();
        ingresosOrdenados.sort(Comparator.comparing(Ingreso::getMonto));

        Iterator egresosIterator = egresosOrdenados.iterator();
        while(egresosIterator.hasNext()){
            
        }
    }

    private void verificarMontos(){

    }
}
