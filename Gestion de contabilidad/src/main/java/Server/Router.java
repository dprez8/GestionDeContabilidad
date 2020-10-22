
package Server;

import Domain.Controllers.DireccionPostalController;
import Domain.Controllers.BandejaDeMensajesRestController;
import Domain.Controllers.LoginRestController;
import Domain.Controllers.MedioDePagoController;
import Domain.Controllers.ProveedorController;
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
        LoginRestController loginRestController = new LoginRestController();
        DireccionPostalController direccionController = new DireccionPostalController();
        ProveedorController proveedorController = new ProveedorController();
        MedioDePagoController medioController = new MedioDePagoController();
        BandejaDeMensajesRestController bandejaDeMensajesRestController= new BandejaDeMensajesRestController();
        
        Spark.post("/api/login",loginRestController::login);
        Spark.get("/api/pais",direccionController::listadoDePaises);
        Spark.get("/api/pais/:clavePais/provincia",direccionController::listadoDeProvincias);
        Spark.get("/api/pais/:clavePais/provincia/:claveProvincia/ciudad",direccionController::listadoDeCiudades);
        Spark.get("/api/proveedor",proveedorController::crearProveedor);
        Spark.get("/api/medios",medioController::listadoMediosDePago);
        Spark.get("/api/medio",medioController::listadoTiposDePago);
        Spark.get("api/bandeja",bandejaDeMensajesRestController::mostrarMensajes);

    }
}
