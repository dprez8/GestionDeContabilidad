package Server;

import Domain.Controllers.OperacionesRestController;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

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

        OperacionesRestController operacionesRestController = new OperacionesRestController();

        Spark.get("/saludo",((request, response) -> "<h1>Bienvenido a la compu de lau</h1>"));

        Spark.post("/api/enviar-operaciones",operacionesRestController::obtenerOperaciones);

        Spark.get("/api/operaciones-resueltas",operacionesRestController::retornarOperaciones);
    }
}
