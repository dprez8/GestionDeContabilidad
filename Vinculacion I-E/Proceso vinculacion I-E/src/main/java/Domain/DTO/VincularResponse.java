package Domain.DTO;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VincularResponse {
    public List<IngresoResponse> ingresos;
    public List<EgresoResponse> egresos;

    public VincularResponse() {
        this.ingresos = new ArrayList<>();
        this.egresos = new ArrayList<>();
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos.stream().map(unIngreso ->
                mapearIngreso(unIngreso)).collect(Collectors.toList());
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos.stream().map(unEgreso ->
                mapearEgreso(unEgreso)).collect(Collectors.toList());
    }

    private class IngresoResponse {
        public Integer numeroIngreso;
        public List<Integer> egresosAsociados;

        public IngresoResponse() {
            this.egresosAsociados = new ArrayList<>();
        }
    }

    private class EgresoResponse {
        public Integer numeroEgreso;
        public Integer ingresoAsociado;
    }

    private IngresoResponse mapearIngreso(Ingreso ingreso) {
        IngresoResponse ingresoResponse = new IngresoResponse();
        ingresoResponse.numeroIngreso = ingreso.getNumeroIngreso();
        ingresoResponse.egresosAsociados = ingreso.getEgresosAsociados().stream().map(unEgreso ->
                unEgreso.getNumeroEgreso()).collect(Collectors.toList());
        return ingresoResponse;
    }
    private EgresoResponse mapearEgreso(Egreso egreso) {
        EgresoResponse egresoResponse = new EgresoResponse();
        egresoResponse.numeroEgreso = egreso.getNumeroEgreso();
        if(egreso.getIngresoAsociado() == null)
            egresoResponse.ingresoAsociado = -1;
        else
            egresoResponse.ingresoAsociado = egreso.getIngresoAsociado().getNumeroIngreso();
        return egresoResponse;
    }
}
