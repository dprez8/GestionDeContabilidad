
package Server;

import Domain.Controllers.LoginRestController;
import Domain.Controllers.LoginController;
import Domain.Controllers.PanelController;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import Spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine(){
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }
    public static void init(){
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){
        rutasVista();
        rutasApi();
    }

    private static void rutasVista() {
        LoginController loginController = new LoginController();
        PanelController panelController = new PanelController();
        
        Spark.get("/login", loginController::mostrarLogin, Router.engine);
        Spark.get("/", panelController::mostrarInicio, Router.engine);
        Spark.get("/operaciones", panelController::mostrarOperaciones, Router.engine);
    }

    private static void rutasApi() {
        LoginRestController loginRestController = new LoginRestController();
        
        Spark.post("/api/login",loginRestController::login);
    }
}
