package Domain.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class VistaController {
    public ModelAndView VueJSApp(Request request, Response response) {

        Map<String, String> parametros = new HashMap<>();

        parametros.put("ip", "gesoc.ddns.net");

        return new ModelAndView(parametros, "index.hbs");
    }
}
