package Domain.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PanelController {
    public ModelAndView mostrarInicio(Request request, Response response) {

        Map<String, String> parametros = new HashMap<>();

        parametros.put("ip", "181.21.245.54");
        parametros.put("titulo", "Inicio");
        parametros.put("panelSelected", "inicio"); // Se puede especificar que template usar en esta variable

        return new ModelAndView(parametros, "panel.hbs");
    }
    
    public ModelAndView mostrarOperaciones(Request request, Response response) {

        Map<String, String> parametros = new HashMap<>();

        parametros.put("ip", "181.21.245.54");
        parametros.put("titulo", "Operaciones");
        parametros.put("panelSelected", "operaciones"); // Se puede especificar que template usar en esta variable

        return new ModelAndView(parametros, "panel.hbs");
    }
}
