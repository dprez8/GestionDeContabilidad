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
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        VincularRequest vincularRequest = gson.fromJson(request.body(), VincularRequest.class);

        System.out.println("Recibo para vincular \n"+gson.toJson(vincularRequest));

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
        System.out.println("Envio ya vinculado \n"+response.body());
        return response.body();
    }

    public String configurar(Request request, Response response) {

        // Gson builder implementa la clase CriteriosAdapter para convertir un String o un Array de Strings en
        // Un CriterioUnico o un Criterio Mix
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Criterio.class, new CriteriosAdapter().nullSafe());
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        ConfiguracionRequest configuracionRequest = gson.fromJson(request.body(), ConfiguracionRequest.class);

        System.out.println("Recibo configuracion \n"+gson.toJson(new JsonParser().parse(request.body())));

        Criterio criterio = configuracionRequest.getCriterio();

        Vinculador vinculador = Vinculador.instancia();
        vinculador.setCriterio(criterio);

        CondicionEntreFechas condicionEntreFechas = new CondicionEntreFechas();
        CondicionSinIngresoAsociado condicionSinIngresoAsociado = new CondicionSinIngresoAsociado();
        CondicionValor condicionValor = new CondicionValor();

        vinculador.addCondiciones(condicionEntreFechas, condicionSinIngresoAsociado, condicionValor);

        response.type("application/json");
        response.body("");

        return response.body();
    }
}
