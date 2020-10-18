package Domain.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    public ModelAndView mostrarLogin(Request request, Response response) {

        Map<String, String> parametros = new HashMap<>();

        return new ModelAndView(parametros, "login.hbs");
    }
}
