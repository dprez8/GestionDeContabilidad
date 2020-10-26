
package Server;

import Domain.Controllers.*;
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
        rutasApi();
        rutasVista();
    }

    private static void rutasVista() {
        LoginController loginController = new LoginController();
        PanelController panelController = new PanelController();
        
        Spark.get("/login", loginController::mostrarLogin, Router.engine);
        Spark.get("/*", panelController::mostrarInicio, Router.engine);
    }

    private static void rutasApi() {
        LoginRestController loginRestController = new LoginRestController();
        DireccionPostalController direccionController = new DireccionPostalController();
        ProveedorController proveedorController = new ProveedorController();
        MedioDePagoController medioController = new MedioDePagoController();
        BandejaDeMensajesRestController bandejaDeMensajesRestController= new BandejaDeMensajesRestController();
        OperacionesRestController operacionesRestController = new OperacionesRestController();
        
        Spark.post("/api/login",loginRestController::login);
        Spark.get("/api/pais",direccionController::listadoDePaises);
        Spark.get("/api/pais/:clavePais/provincia",direccionController::listadoDeProvincias);
        Spark.get("/api/pais/:clavePais/provincia/:claveProvincia/ciudad",direccionController::listadoDeCiudades);
        Spark.get("/api/proveedor",proveedorController::crearProveedor);
        Spark.get("/api/proveedores",proveedorController::listadoProveedores);
        Spark.get("/api/medios",medioController::listadoMediosDePago);
        Spark.get("api/bandeja",bandejaDeMensajesRestController::mostrarMensajes);
        //Spark.get("/api/bandeja/:usuarioId",bandejaDeMensajesRestController::mostrarMensajes);
        Spark.post("/api/operaciones/egreso",operacionesRestController::cargarNuevoEgreso);
        Spark.get("/api/operaciones/egresos",operacionesRestController::listadoDeEgresos);
    }
}
