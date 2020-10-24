package Domain.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PanelController {
    public ModelAndView mostrarInicio(Request request, Response response) {

        Map<String, String> parametros = new HashMap<>();

        parametros.put("ip", "181.21.133.179");
        parametros.put("titulo", "Inicio");

        return new ModelAndView(parametros, "panel.hbs");
    }
}
