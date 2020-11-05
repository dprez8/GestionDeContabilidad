
package Server;

import Domain.Controllers.*;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.ValidadorTransparencia.ValidadorDeTransparencia;
import Domain.Entities.ValidadorTransparencia.ValidarCantidadMinima;
import Domain.Entities.ValidadorTransparencia.ValidarConPresupuesto;
import Domain.Entities.ValidadorTransparencia.ValidarMenorValor;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import org.hibernate.Hibernate;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.List;

public class Router {
    private static HandlebarsTemplateEngine engine;
    private static Repositorio<Organizacion> repoOrganizaciones = new Repositorio<>(new DaoHibernate<>(Organizacion.class));
    private static ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima(1);
    private static ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
    private static ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

    private static ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMinima,validacionPresupuesto,validacionMenorValor);

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
        CategoriasEgresosController categoriasController = new CategoriasEgresosController();
        IngresosRestController ingresosRestController = new IngresosRestController();
        AsociacionOperacionesRestController asociacionOperacionesRestController = new AsociacionOperacionesRestController();

        Spark.post("/api/login",loginRestController::login);
        Spark.get("/api/login",loginRestController::sessionStatus);
        Spark.get("/api/pais",direccionController::listadoDePaises);
        Spark.get("/api/pais/:clavePais/provincia",direccionController::listadoDeProvincias);
        Spark.get("/api/pais/:clavePais/provincia/:claveProvincia/ciudad",direccionController::listadoDeCiudades);
        Spark.post("/api/proveedor",proveedorController::crearProveedor);
        Spark.get("/api/proveedores",proveedorController::listadoProveedores);
        Spark.get("/api/medios",medioController::listadoMediosDePago);
        Spark.get("/api/bandeja",bandejaDeMensajesRestController::mostrarMensajes);
        Spark.get("/api/bandeja/configuracion",bandejaDeMensajesRestController::mostrarConfiguracion);
        Spark.post("/api/bandeja/configurar", bandejaDeMensajesRestController::configurar);
        Spark.post("/api/bandeja/visto", bandejaDeMensajesRestController::mensajeVisto);
        Spark.get("/api/categorias",categoriasController::listadoCriterios);
        Spark.post("/api/operaciones/egreso", egresosRestController::cargarNuevoEgreso);
        Spark.get("/api/operaciones/egresos", egresosRestController::listadoDeEgresos);
        Spark.get("/api/operaciones/egreso/:egresoId", egresosRestController::mostrarEgreso);
        Spark.get("/api/operaciones/ingresos",ingresosRestController::listadoDeIngresos);
        Spark.post("/api/operaciones/asociarManualmente",asociacionOperacionesRestController::asociarManualmente);
        Spark.post("/api/categorias/asociar",categoriasController::asociarCategoriaEgreso);
        Spark.post("/api/operaciones/ingreso",ingresosRestController::cargarNuevoIngreso);
        Spark.post("/api/operaciones/egreso/cargar-archivos",egresosRestController::cargarArchivoDocumentoComercial);
    }

    private static void verificarTareasProgramadas() {
        List<Organizacion> organizaciones = repoOrganizaciones.buscarTodos();
        organizaciones.forEach(unaOrg->{
                unaOrg.getScheduler().setValidadorDeTransparencia(validador);
                unaOrg.getScheduler().arrancarTarea();
        });
    }
}
