package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.AdaptersJson.LocalDateTimeAdapter;
import Domain.Controllers.DTO.IngresoRequest;
import Domain.Controllers.DTO.IngresoResponse;
import Domain.Controllers.DTO.Respuesta;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.ApiPaises.Moneda;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Estandar;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class IngresosRestController extends GenericController {
    private Repositorio<Ingreso> repoIngresos;

    private Repositorio<Moneda> repoMoneda;
    private Respuesta respuesta;
    private Gson gson;

    public IngresosRestController(TokenService tokenService, String tokenPrefix) {
        super(tokenService,tokenPrefix);
        this.repoIngresos        = new Repositorio<>(new DaoHibernate<>(Ingreso.class));
        this.repoMoneda       	 = new Repositorio<>(new DaoHibernate<>(Moneda.class));
        this.respuesta           = new Respuesta();
    }

    public String listadoDeIngresos(Request request, Response response) {
        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
                .create();
        Organizacion organizacion = usuario.getMiOrganizacion();
        List<IngresoResponse> ingresosAEnviar;
        String jsonResponse;

        ingresosAEnviar = organizacion.getIngresos()
                .stream()
                .map(this::mapearIngresos)
                .collect(Collectors.toList());

        if (ingresosAEnviar.isEmpty()) {
            return error(response, "No hay ingresos cargados");
        }

        ListadoIngresos listadoIngresos = new ListadoIngresos();
        listadoIngresos.code    = 200;
        listadoIngresos.message = "Ok";
        listadoIngresos.ingresos = ingresosAEnviar;

        return toJson(response, listadoIngresos);
    }

    public String cargarNuevoIngreso(Request request, Response response) {

        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        IngresoRequest ingresoRequest    = this.gson.fromJson(request.body(), IngresoRequest.class);

        Ingreso ingreso = asignarIngresoDesde(request, ingresoRequest);

        if(ingreso == null) {
            return error(response, "Problema al cargar el ingreso");
        }

        CargaIngresoResponse cargaIngresoResponse = new CargaIngresoResponse();
        cargaIngresoResponse.code                 = 200;
        cargaIngresoResponse.message              = "Ok";
        cargaIngresoResponse.id                   = ingreso.getId();

        return toJson(response, cargaIngresoResponse);
    }

    private IngresoResponse mapearIngresos(Ingreso ingreso) {
        IngresoResponse ingresoAEnviar = new IngresoResponse();
        ingresoAEnviar.id              = ingreso.getId();
        ingresoAEnviar.fechaOperacion  = ingreso.getFechaOperacion();
        ingresoAEnviar.fechaCarga      = ingreso.getFechaCarga();
        ingresoAEnviar.descripcion     = ingreso.getDescripcion();
        ingresoAEnviar.montoTotal      = ingreso.montoSobrante();
        ingresoAEnviar.egresos         = ingreso.getEgresos()
                                                .stream()
                                                .map(unEgreso->unEgreso.getId())
                                                .collect(Collectors.toList());
        return ingresoAEnviar;
    }

    private Ingreso asignarIngresoDesde(Request request, IngresoRequest ingresoRequest) {
        if(ingresoRequest.descripcion == null || ingresoRequest.fechaOperacion == null || ingresoRequest.montoTotal == null){
            return null;
        }
        Ingreso ingreso = new Ingreso();
        ingreso.setDescripcion(ingresoRequest.descripcion);
        ingreso.setFechaOperacion(ingresoRequest.fechaOperacion);
        ingreso.setFechaAceptacionEgreso(ingresoRequest.fechaAceptacionEgresos);
        ingreso.setMontoTotal(ingresoRequest.montoTotal);

        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
        ingreso.setOrganizacion(usuario.getMiOrganizacion());
        
        if(ingresoRequest.moneda_id!=0) {
            Moneda moneda=repoMoneda.buscar(ingresoRequest.moneda_id);
        	ingreso.setMoneda(moneda);
        }

        this.repoIngresos.agregar(ingreso);

        return ingreso;
    }

    private class ListadoIngresos {
        public int code;
        public String message;
        public List<IngresoResponse> ingresos;
    }

    private class CargaIngresoResponse {
        public int code;
        public String message;
        public int id;
    }
}
