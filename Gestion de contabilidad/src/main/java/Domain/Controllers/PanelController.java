package Domain.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PanelController {
    public ModelAndView mostrarInicio(Request request, Response response) {

        Map<String, String> parametros = new HashMap<>();

        parametros.put("ip", "gesoc.ddns.net");
        parametros.put("titulo", "Inicio");

        return new ModelAndView(parametros, "panel.hbs");
    }
}
