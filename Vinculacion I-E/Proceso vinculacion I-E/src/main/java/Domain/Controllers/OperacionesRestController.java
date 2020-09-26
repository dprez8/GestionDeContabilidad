package Domain.Controllers;

import Domain.Converters.LocalDateAdapter;
import Domain.DTO.ConfiguracionDTO;
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
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe()).create();

        VincularRequest vincularRequest = gson.fromJson(request.body(), VincularRequest.class);

        Vinculador vinculador = Vinculador.instancia();

        vinculador.setEgresos(vincularRequest.getEgresos());
        vinculador.setIngresos(vincularRequest.getIngresos());

        vinculador.vincular();

        VincularResponse vincularResponse = new VincularResponse();

        vincularResponse.setIngresos(vinculador.getIngresos());
        vincularResponse.setEgresos(vinculador.getEgresos());

        String jsonOperacionesDTO = gson.toJson(vincularResponse);
        response.type("application/json");

        System.out.println(jsonOperacionesDTO);

        return jsonOperacionesDTO;
    }

    public Response configurar(Request request, Response response) {

        Gson gson = new Gson();

        ConfiguracionDTO configuracionDTO = gson.fromJson(request.body(), ConfiguracionDTO.class);

        String criterio = configuracionDTO.getCriterio();
        List<String> criterios = configuracionDTO.getCriterios();

        Vinculador vinculador = Vinculador.instancia();

        if(!criterio.equals("Mix")) {
            vinculador.setCriterio(criterioFromString(criterio));
        } else {
            Mix mix = new Mix();
            List<CriterioUnico> criteriosUnicos = criterios.stream().map(unCriterio ->
                    criterioFromString(unCriterio)).collect(Collectors.toList());

            mix.setCriteriosUnicos(criteriosUnicos);
            vinculador.setCriterio(mix);
        }

        Integer diasDesde = configuracionDTO.getDiasDesde();
        Integer diasHasta = configuracionDTO.getDiasHasta();

        CondicionEntreFechas condicionEntreFechas = new CondicionEntreFechas();
        condicionEntreFechas.setDiasDesde(diasDesde);
        condicionEntreFechas.setDiasHasta(diasHasta);
        CondicionSinIngresoAsociado condicionSinIngresoAsociado = new CondicionSinIngresoAsociado();
        CondicionValor condicionValor = new CondicionValor();

        vinculador.addCondiciones(condicionEntreFechas, condicionSinIngresoAsociado, condicionValor);

        System.out.println(vinculador.getCriterio());

        return response;
    }

    public CriterioUnico criterioFromString(String string) {
        CriterioUnico criterio = null;

        switch (string) {
            case "OrdenValorPrimeroEgreso": criterio = new OrdenValorPrimeroEgreso(); break;
            case "OrdenValorPrimeroIngreso": criterio = new OrdenValorPrimeroIngreso(); break;
            case "Fecha": criterio = new Fecha(); break;
        }

        return criterio;
    }
}
