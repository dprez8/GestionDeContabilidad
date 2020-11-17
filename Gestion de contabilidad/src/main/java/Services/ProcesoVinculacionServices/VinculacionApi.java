package Services.ProcesoVinculacionServices;

import Domain.Entities.ApiVinculador.*;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class VinculacionApi {
    public void vincularEgresosIngresos(List<Ingreso> ingresos, List<Egreso> egresos, Properties properties) throws IOException {
        List<IngresoAEnviar> ingresosAEnviar = ingresos.stream().map(this::mapIngresos).collect(Collectors.toList());
        List<EgresoAEnviar> egresosAEnviar = egresos.stream().map(this::mapEgresos).collect(Collectors.toList());

        VincularRequest vincularRequest = new VincularRequest();
        vincularRequest.ingresos = ingresosAEnviar;
        vincularRequest.egresos = egresosAEnviar;

        VincularResponse vincularResponse = ServicioVinculacion
                .instancia(properties.getProperty("URLVINCULADOR"))
                .vincular(vincularRequest);

        vincularResponse.egresos.forEach(egresoARecibir -> {
            Ingreso ingreso = ingresos.stream()
                    .filter(unIngreso -> egresoARecibir.ingresoAsociado.equals(unIngreso.getId()))
                    .findAny()
                    .orElse(null);

            Egreso egreso = egresos.stream()
                    .filter(unEgreso -> egresoARecibir.numeroEgreso.equals(unEgreso.getId()))
                    .findAny()
                    .orElse(null);

            if(ingreso != null) {
                egreso.setIngresoAsociado(ingreso);
                ingreso.setEgresos(egreso);
            }
        });
    }
    public void configurarVinculador(String criterio, Properties properties) throws IOException {
        enviarConfigurarVinculador(criterio, properties);
    }
    public void configurarVinculador(List<String> criterios, Properties properties) throws IOException {
        enviarConfigurarVinculador(criterios, properties);
    }
    private void enviarConfigurarVinculador(Object criterio, Properties properties) throws IOException {
        ConfigurarVinculadorRequest configurarVinculadorRequest = new ConfigurarVinculadorRequest();
        configurarVinculadorRequest.criterio = criterio;

        ServicioVinculacion
                .instancia(properties.getProperty("URLVINCULADOR"))
                .configurar(configurarVinculadorRequest);
    }

    private IngresoAEnviar mapIngresos(Ingreso ingreso) {
        IngresoAEnviar ingresoAEnviar = new IngresoAEnviar();
        ingresoAEnviar.numeroIngreso = ingreso.getId();
        ingresoAEnviar.monto = ingreso.montoSobrante();
        ingresoAEnviar.fecha = ingreso.getFechaOperacion().toString();
        ingresoAEnviar.fechaAceptacionEgreso =ingreso.getFechaAceptacionEgreso().toString();
        return ingresoAEnviar;
    }
    private EgresoAEnviar mapEgresos(Egreso egreso) {
        EgresoAEnviar egresoAEnviar = new EgresoAEnviar();
        egresoAEnviar.numeroEgreso = egreso.getId();
        egresoAEnviar.monto = egreso.getValorTotal();
        egresoAEnviar.fecha = egreso.getFechaOperacion().toString();
        return egresoAEnviar;
    }
}
