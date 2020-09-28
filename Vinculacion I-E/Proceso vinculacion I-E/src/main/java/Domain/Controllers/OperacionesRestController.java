package Domain.Controllers;

import Domain.Converters.CriteriosAdapter;
import Domain.Converters.LocalDateAdapter;
import Domain.DTO.ConfiguracionRequest;
import Domain.DTO.VincularRequest;
import Domain.DTO.VincularResponse;
import Domain.Entities.Condiciones.CondicionEntreFechas;
import Domain.Entities.Condiciones.CondicionSinIngresoAsociado;
import Domain.Entities.Condiciones.CondicionValor;
import Domain.Entities.Criterios.*;
import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Vinculador;
import com.google.gson.*;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OperacionesRestController {

    public String vincular(Request request, Response response) {

        // Gson builder implementa la clase LocalDateAdapter para transformar el String de Json "yyyy-MM-dd" a LocalDate
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe());
        Gson gson = gsonBuilder.create();

        System.out.println(request.body());

        VincularRequest vincularRequest = gson.fromJson(request.body(), VincularRequest.class);

        List<Ingreso> ingresos = vincularRequest.getIngresos();
        List<Egreso> egresos = vincularRequest.getEgresos();

        Vinculador vinculador = Vinculador.instancia();

        if(vinculador.getCriterio() == null) {
            // No se a configurado el vinculador
            // Enviar error
            response.status(403);
            response.type("application/json");
            response.body("");
        } else {
            // Se configuró el servidor previamente, asi que vincular y responder
            vinculador.vincular(egresos, ingresos);

            VincularResponse vincularResponse = new VincularResponse();
            vincularResponse.setIngresos(ingresos);
            vincularResponse.setEgresos(egresos);

            String jsonVincularRequest = gson.toJson(vincularResponse);
            response.type("application/json");
            response.body(jsonVincularRequest);

        }
        System.out.println(response.body());
        return response.body();
    }

    public String configurar(Request request, Response response) {

        // Gson builder implementa la clase CriteriosAdapter para convertir un String o un Array de Strings en
        // Un CriterioUnico o un Criterio Mix
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Criterio.class, new CriteriosAdapter().nullSafe());
        Gson gson = gsonBuilder.create();

        ConfiguracionRequest configuracionRequest = gson.fromJson(request.body(), ConfiguracionRequest.class);

        Criterio criterio = configuracionRequest.getCriterio();

        Vinculador vinculador = Vinculador.instancia();
        vinculador.setCriterio(criterio);

        Integer diasDesde = configuracionRequest.getDiasDesde();
        Integer diasHasta = configuracionRequest.getDiasHasta();

        CondicionEntreFechas condicionEntreFechas = new CondicionEntreFechas();
        condicionEntreFechas.setDiasDesde(diasDesde);
        condicionEntreFechas.setDiasHasta(diasHasta);
        CondicionSinIngresoAsociado condicionSinIngresoAsociado = new CondicionSinIngresoAsociado();
        CondicionValor condicionValor = new CondicionValor();

        vinculador.addCondiciones(condicionEntreFechas, condicionSinIngresoAsociado, condicionValor);

        response.type("application/json");
        response.body("");

        System.out.println(response.body());
        return response.body();
    }
}
