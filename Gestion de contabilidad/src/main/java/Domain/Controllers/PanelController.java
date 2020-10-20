package Domain.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PanelController {
    public ModelAndView mostrarPanel(Request request, Response response) {

        Map<String, String> parametros = new HashMap<>();

        parametros.put("titulo", "Inicio");
        parametros.put("panelSelected", "inicio.hbs"); // Se puede especificar que template usar en esta variable

        return new ModelAndView(parametros, "panel.hbs");
    }
    public ModelAndView mostrarOperaciones(Request request, Response response) {

        Map<String, String> parametros = new HashMap<>();

        parametros.put("titulo", "Operaciones");
        parametros.put("panelSelected", "operaciones.hbs"); // Se puede especificar que template usar en esta variable

        return new ModelAndView(parametros, "panel.hbs");
    }
}
