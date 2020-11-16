
package Server;

import Domain.Controllers.*;
import Domain.Controllers.jwt.AuthController;
import Domain.Controllers.jwt.AuthFilter;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.ValidadorTransparencia.*;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import db.EntityManagerHelper;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Router {
    private static HandlebarsTemplateEngine engine;
    private static Repositorio<Organizacion> repoOrganizaciones = new Repositorio<>(new DaoHibernate<>(Organizacion.class));
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final String SECRET_JWT = "secret_jwt";
    private static final String TOKEN_PREFIX = "Bearer";
    private static TokenService tokenService = new TokenService(SECRET_JWT);

    private static void initEngine(){
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }
    public static void init(){
        Router.initEngine();
        Spark.staticFiles.location("/public");
        Router.configure();
    }

    private static void configure(){
        rutasApi();
        rutasVista();
        verificarTareasProgramadas();
    }

    private static void rutasVista() {
        Spark.get("/*", (req, res) ->
                        new ModelAndView(new HashMap(),
                                "../public/index.html"),
                        Router.engine);
    }

    private static void rutasApi() {
        AuthFilter authFilter = new AuthFilter("/api/auth",tokenService);

        AuthController authController           = new AuthController(tokenService);

        EgresosRestController egresosRestController = new EgresosRestController(tokenService,TOKEN_PREFIX);
        IngresosRestController ingresosRestController = new IngresosRestController(tokenService,TOKEN_PREFIX);
        AsociacionOperacionesRestController asociacionOperacionesRestController = new AsociacionOperacionesRestController(tokenService,TOKEN_PREFIX);
        BandejaDeMensajesRestController bandejaDeMensajesRestController= new BandejaDeMensajesRestController(tokenService,TOKEN_PREFIX);
        OrganizacionController organizacionController = new OrganizacionController(tokenService,TOKEN_PREFIX);
        CategoriasEgresosController categoriasEgresosController = new CategoriasEgresosController(tokenService,TOKEN_PREFIX);
        CriteriosCategoriasController criteriosCategoriasController = new CriteriosCategoriasController();
        EntidadRestController entidadRestController = new EntidadRestController(tokenService,TOKEN_PREFIX);
        DireccionPostalController direccionController = new DireccionPostalController();
        ProveedorController proveedorController = new ProveedorController(tokenService, TOKEN_PREFIX);
        MedioDePagoController medioController = new MedioDePagoController();
        DocumentoComercialController documentoController = new DocumentoComercialController();

        PresupuestoRestController presupuestoRestController = new PresupuestoRestController(tokenService, TOKEN_PREFIX);
        ItemsController itemsController = new ItemsController(tokenService, TOKEN_PREFIX);

        FilesController filesController = new FilesController(tokenService, TOKEN_PREFIX);


        /****  Verificacion de credenciales  ******/
        Spark.before("/api/*",authFilter);

        /****  AuthController          *********/
        Spark.post("/api/auth/logout", authController::logout);
        Spark.post("/api/auth/login",authController::login );
        Spark.get("/api/auth/me", authController::me);
        Spark.post("/api/auth/token", authController::refresh);

        /****  DireccionPostalController *******/
        Spark.get("/api/pais",direccionController::listadoDePaises);
        Spark.get("/api/pais/:clavePais/provincia",direccionController::listadoDeProvincias);
        Spark.get("/api/pais/:clavePais/provincia/:claveProvincia/ciudad",direccionController::listadoDeCiudades);
        Spark.get("/api/monedas",direccionController::listadoDeMonedas);

        /****  ProveedorController     ********/
        Spark.post("/api/proveedor",proveedorController::crearProveedor);
        Spark.get("/api/proveedores",proveedorController::listadoProveedores);

        /****  DocumentoComercialController   ********/
        Spark.get("/api/tipodocumento",documentoController::listadoTiposDocumento);
        
        
        /****  MedioDePagoController    ********/
        Spark.get("/api/medios",medioController::listadoMediosDePago);

        /****  BandejaDeMensajeController *******/
        Spark.get("/api/bandeja",bandejaDeMensajesRestController::mostrarMensajes);
        Spark.get("/api/bandeja/configuracion/:organizacionId",bandejaDeMensajesRestController::mostrarConfiguracion);
        Spark.post("/api/admin/bandeja/configurar", bandejaDeMensajesRestController::configurar);
        Spark.post("/api/bandeja/visto", bandejaDeMensajesRestController::mensajeVisto);

        /****  CriteriosCategoriasController  ******/
        Spark.get("/api/criterios",criteriosCategoriasController::listadoCriterios);
        Spark.post("/api/admin/criterio",criteriosCategoriasController::crearCriterio);
        //Spark.post("/api/admin/darJerarquiaCriterio",categoriasController::darJerarquiaACriterio);

        /**** CategoriasEgresosController  ******/
        Spark.post("/api/admin/categoria",categoriasEgresosController::crearCategoria);
        Spark.post("/api/categorias/asociar",categoriasEgresosController::asociarCategoriaEgreso);

        /****  EgresosRestController       ********/
        Spark.post("/api/operaciones/egreso", egresosRestController::cargarNuevoEgreso);
        Spark.get("/api/operaciones/egresos", egresosRestController::listadoDeEgresos);
        Spark.get("/api/operaciones/egreso/:egresoId", egresosRestController::mostrarEgreso);
        Spark.post("/api/operaciones/egreso/cargarArchivos",egresosRestController::cargarArchivoDocumentoComercial);
        Spark.post("/api/operaciones/egreso/suscribirse",egresosRestController::suscribirse);
        Spark.post("/api/operaciones/egreso/suscribirse",egresosRestController::suscribirse);
        Spark.patch("/api/operaciones/egreso/modificarPathAdjunto", egresosRestController::modificarEgreso);

        /****  ItemsController    ********/
        Spark.get("/api/items",itemsController::listadoDeItems);
        Spark.get("/api/tipoItems",itemsController::listadoDeTipoItems);

        /****  IngresosRestController     ********/
        Spark.get("/api/operaciones/ingresos",ingresosRestController::listadoDeIngresos);
        Spark.post("/api/operaciones/ingreso",ingresosRestController::cargarNuevoIngreso);

        /****  AsociacionRestController    ********/
        Spark.post("/api/operaciones/asociarManualmente",asociacionOperacionesRestController::asociarManualmente);

        /****  PresupuestoRestController    ********/
        Spark.post("/api/operaciones/presupuesto", presupuestoRestController::cargarNuevoPresupuesto);

        /****  OrganizacionController    ********/
        Spark.post("/api/admin/entidadJuridica/osc",organizacionController::crearEntidadJuridicaOsc);
        Spark.post("/api/admin/entidadJuridica/empresa",organizacionController::crearEntidadJuridicaEmpresa);
        Spark.post("/api/admin/entidadBase",organizacionController::crearEntidadBase);
        /****  EntidadBaseRestController ********/
        Spark.get("/api/entidades", entidadRestController::listarEntidades);
        /*** Descargar archivos               ***/
        Spark.get("/files/:filename", filesController::descargarArchivo);

        /**** Cierre de entityManager    ********/
        Spark.after("/api/*",(request, response) -> {
             if(EntityManagerHelper.getEntityManagerRecent() != null && EntityManagerHelper.getEntityManagerRecent().isOpen()){
                EntityManagerHelper.closeEntityManager();
             }
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
        // PERIODIC TOKENS CLEAN UP
        EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            System.out.println("Removing expired tokens");
            tokenService.removeExpired();
        }, 60, 60, TimeUnit.SECONDS); // every minute

    }
}
