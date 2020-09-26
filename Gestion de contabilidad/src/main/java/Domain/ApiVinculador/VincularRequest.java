package Domain.ApiVinculador;

import java.util.ArrayList;
import java.util.List;

public class VincularRequest {
    public List<IngresoAEnviar> ingresos;
    public List<EgresoAEnviar> egresos;

    public VincularRequest() {
        this.ingresos = new ArrayList<>();
        this.egresos = new ArrayList<>();
    }
}
