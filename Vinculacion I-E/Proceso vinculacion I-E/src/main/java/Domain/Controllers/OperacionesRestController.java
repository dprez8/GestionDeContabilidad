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

        VincularRequest vincularRequest = gson.fromJson(request.body(), VincularRequest.class);

        Vinculador vinculador = Vinculador.instancia();

        vinculador.setEgresos(vincularRequest.getEgresos());
        vinculador.setIngresos(vincularRequest.getIngresos());

        vinculador.vincular();

        VincularResponse vincularResponse = new VincularResponse();

        vincularResponse.setIngresos(vinculador.getIngresos());
        vincularResponse.setEgresos(vinculador.getEgresos());

        String jsonVincularRequest = gson.toJson(vincularResponse);
        response.type("application/json");

        System.out.println(jsonVincularRequest);

        return jsonVincularRequest;
    }

    public Response configurar(Request request, Response response) {

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

        System.out.println(vinculador.getCriterio());

        return response;
    }
}
