
package Server;

import Domain.Controllers.LoginRestController;
import Domain.Controllers.LoginController;
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
        LoginRestController loginRestController = new LoginRestController();
        LoginController loginController = new LoginController();

        Spark.post("/api/login",loginRestController::login);
        Spark.get("/login", loginController::mostrarLogin, Router.engine);
    }
}
