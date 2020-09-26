package Domain.ApiVinculador;

import java.util.List;

public class VincularResponse {
    public List<IngresoARecibir> ingresos;
    public List<EgresoARecibir> egresos;

    private class IngresoARecibir {
        public Integer numeroIngreso;
        public List<Integer> egresosAsociados;
    }

    private class EgresoARecibir {
        public Integer numeroEgreso;
        public List<Integer> ingresoAsociado;
    }
}
