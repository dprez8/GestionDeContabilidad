package Services.ProcesoVinculacionServices;

import Domain.ApiVinculador.*;
import Services.MercadoLibreServices.EleccionApi;
import com.google.gson.Gson;

import javax.xml.ws.Response;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class VinculacionMain {
    public static void main(String[] args) throws IOException {
        ServicioVinculacion servicioVinculacion = ServicioVinculacion.instancia("http://181.21.131.77/");

        ConfigurarVinculadorRequest configurarVinculadorRequest = new ConfigurarVinculadorRequest();
        VincularRequest vincularRequest = new VincularRequest();

        configurarVinculadorRequest.diasDesde = -3;
        configurarVinculadorRequest.diasHasta = 3;
        configurarVinculadorRequest.criterio = "OrdenValorPrimeroEgreso";
        configurarVinculadorRequest.criterios = new ArrayList<>();

        servicioVinculacion.configurar(configurarVinculadorRequest);

        IngresoAEnviar creditosHaberes = new IngresoAEnviar();
        IngresoAEnviar donacion = new IngresoAEnviar();

        creditosHaberes.numeroIngreso = 1;
        creditosHaberes.monto = 10000.0;
        creditosHaberes.fecha = "2020-09-25";

        donacion.numeroIngreso = 2;
        donacion.monto = 500.0;
        donacion.fecha = "2020-09-26";

        EgresoAEnviar compraUno = new EgresoAEnviar();
        EgresoAEnviar compraDos = new EgresoAEnviar();
        EgresoAEnviar compraTres = new EgresoAEnviar();

        compraUno.numeroEgreso = 1;
        compraUno.fecha = "2020-09-26";
        compraUno.monto = 1000.0;

        compraDos.numeroEgreso = 2;
        compraDos.fecha = "2020-09-27";
        compraDos.monto = 2500.0;

        compraTres.numeroEgreso = 3;
        compraTres.fecha = "2020-09-23";
        compraTres.monto = 10000.0;

        vincularRequest.ingresos.add(creditosHaberes);
        vincularRequest.ingresos.add(donacion);

        vincularRequest.egresos.add(compraUno);
        vincularRequest.egresos.add(compraDos);
        vincularRequest.egresos.add(compraTres);

        VincularResponse vincularResponse = servicioVinculacion.vincular(vincularRequest);

        Gson gson = new Gson();
        System.out.println(gson.toJson(vincularResponse));
    }
}
