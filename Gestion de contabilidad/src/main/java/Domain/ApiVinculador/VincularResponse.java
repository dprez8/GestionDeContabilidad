package Domain.ApiVinculador;

import java.util.ArrayList;
import java.util.List;

public class VincularResponse {
    public List<IngresoARecibir> ingresos;
    public List<EgresoARecibir> egresos;

    public VincularResponse() {
        this.ingresos = new ArrayList<>();
        this.egresos = new ArrayList<>();
    }

    private class IngresoARecibir {
        public Integer numeroIngreso;
        public List<Integer> egresosAsociados;
    }

    private class EgresoARecibir {
        public Integer numeroEgreso;
        public Integer ingresoAsociado;
    }
}
