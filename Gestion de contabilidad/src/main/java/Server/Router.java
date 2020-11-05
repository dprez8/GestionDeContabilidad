
package Server;

import Domain.Controllers.*;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.ValidadorTransparencia.*;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import db.EntityManagerHelperTwo;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static Repositorio<Organizacion> repoOrganizaciones = new Repositorio<>(new DaoHibernate<>(Organizacion.class));

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
        verificarTareasProgramadas();
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
        EgresosRestController egresosRestController = new EgresosRestController();
        CriteriosCategoriasController categoriasController = new CriteriosCategoriasController();
        IngresosRestController ingresosRestController = new IngresosRestController();
        AsociacionOperacionesRestController asociacionOperacionesRestController = new AsociacionOperacionesRestController();
        
        Spark.post("/api/login",loginRestController::login);
        Spark.get("/api/login",loginRestController::sessionStatus);
        Spark.get("/api/pais",direccionController::listadoDePaises);
        Spark.get("/api/pais/:clavePais/provincia",direccionController::listadoDeProvincias);
        Spark.get("/api/pais/:clavePais/provincia/:claveProvincia/ciudad",direccionController::listadoDeCiudades);
        Spark.get("/api/proveedor",proveedorController::crearProveedor);
        Spark.get("/api/proveedores",proveedorController::listadoProveedores);
        Spark.get("/api/medios",medioController::listadoMediosDePago);
        Spark.get("/api/bandeja",bandejaDeMensajesRestController::mostrarMensajes);
        Spark.get("/api/bandeja/configuracion",bandejaDeMensajesRestController::mostrarConfiguracion);
        Spark.post("/api/bandeja/configurar", bandejaDeMensajesRestController::configurar);
        Spark.post("/api/bandeja/visto", bandejaDeMensajesRestController::mensajeVisto);
        Spark.get("api/bandeja",bandejaDeMensajesRestController::mostrarMensajes);
        //Spark.get("/api/bandeja/:usuarioId",bandejaDeMensajesRestController::mostrarMensajes);
        Spark.get("/api/categorias",categoriasController::listadoCriterios);
        Spark.post("/api/categorias",categoriasController::crearCategoria);
        Spark.post("/api/categorias",categoriasController::crearCriterio);    
        Spark.post("/api/operaciones/egreso", egresosRestController::cargarNuevoEgreso);
        Spark.get("/api/operaciones/egresos", egresosRestController::listadoDeEgresos);
        Spark.get("/api/operaciones/egreso/:egresoId", egresosRestController::mostrarEgreso);
        Spark.get("/api/operaciones/ingresos",ingresosRestController::listadoDeIngresos);
        Spark.post("/api/operaciones/asociarManualmente",asociacionOperacionesRestController::asociarManualmente);
        Spark.post("/api/categorias/asociar",categoriasController::asociarCategoriaEgreso);
        Spark.post("/api/operaciones/ingreso",ingresosRestController::cargarNuevoIngreso);


        Spark.after("/api/*",(request, response) -> {
            if(EntityManagerHelperTwo.getEntityManager().isOpen()){
                EntityManagerHelperTwo.closeEntityManager();
            }
            response.type("application/json");
        });
    }
    private static void verificarTareasProgramadas() {
        List<Organizacion> organizaciones = repoOrganizaciones.buscarTodos();
        organizaciones.forEach(unaOrg->{
                Tarea tarea = new Tarea();
                Timer timer = new Timer();

                HashMap<Integer, Tarea> schedulerHashMap = new HashMap<>();
                schedulerHashMap.put(unaOrg.getSchedulerInit().getId(), tarea);

                HashMap<Integer, Timer> timerHashMap = new HashMap<>();
                timerHashMap.put(unaOrg.getSchedulerInit().getId(),timer);

                TimersController.instancia().setSchedulerHashMap(schedulerHashMap);
                TimersController.instancia().setTimerHashMap(timerHashMap);

                unaOrg.getSchedulerInit().setTarea(tarea);
                unaOrg.getSchedulerInit().setTimer(timer);
                unaOrg.getSchedulerInit().arrancarTarea();
        });
        /*if(EntityManagerHelperTwo.getEntityManager().isOpen()){
            EntityManagerHelperTwo.closeEntityManager();
        }*/
    }
}
