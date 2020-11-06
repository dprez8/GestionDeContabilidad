package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.AdaptersJson.LocalDateTimeAdapter;
import Domain.Controllers.DTO.IngresoRequest;
import Domain.Controllers.DTO.IngresoResponse;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.ClasesParciales.ProveedorNuevo;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Organizacion.EntidadJuridica;
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

public class IngresosRestController {
    private Repositorio<Ingreso> repoIngresos;
    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Respuesta respuesta;
    private Gson gson;

    public IngresosRestController() {
        this.repoIngresos        = new Repositorio<>(new DaoHibernate<>(Ingreso.class));
        this.repoEntidadJuridica = new Repositorio<>(new DaoHibernate<>(EntidadJuridica.class));
        this.respuesta           = new Respuesta();
    }

    public String listadoDeIngresos(Request request, Response response) {
        response.type("application/json");
        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }
        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
                .create();
        EntidadJuridica entidadJuridica= this.repoEntidadJuridica.buscar(usuario.getMiOrganizacion().getId());
        List<IngresoResponse> ingresosAEnviar;
        String jsonResponse;

        ingresosAEnviar = entidadJuridica.getIngresos()
                .stream()
                .map(this::mapearIngresos)
                .collect(Collectors.toList());

        if (ingresosAEnviar.isEmpty()) {
            this.respuesta.setCode(404);
            this.respuesta.setMessage("No hay ingresos cargados");
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }

        ListadoIngresos listadoIngresos = new ListadoIngresos();
        listadoIngresos.code    = 200;
        listadoIngresos.message = "Ok";
        listadoIngresos.ingresos = ingresosAEnviar;

        jsonResponse = this.gson.toJson(listadoIngresos);
        response.body(jsonResponse);
        return response.body();
    }

    public String cargarNuevoIngreso(Request request, Response response) {
        response.type("application/json");
        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }
        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();
        String jsonResponse;

        IngresoRequest ingresoRequest = null;

        try {
            ingresoRequest = this.gson.fromJson(request.body(),IngresoRequest.class);
        } catch (Exception exception) {
            this.respuesta.setCode(400);
            this.respuesta.setMessage("Formato incorrecto en datos del ingreso");
            String jsonRespuesta = this.gson.toJson(this.respuesta);
            response.body(jsonRespuesta);
            return response.body();
        }


        EntidadJuridica entidadJuridica= this.repoEntidadJuridica.buscar(usuario.getMiOrganizacion().getId());

        Ingreso ingreso = asignarIngresoDesde(ingresoRequest, entidadJuridica);

        if(ingreso == null) {
            this.respuesta.setCode(400);
            this.respuesta.setMessage("Problema al cargar el ingreso");
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }
        CargaIngresoResponse cargaIngresoResponse = new CargaIngresoResponse();
        cargaIngresoResponse.code                 = 200;
        cargaIngresoResponse.message              = "Ok";
        cargaIngresoResponse.id                   = ingreso.getId();

        jsonResponse = this.gson.toJson(cargaIngresoResponse);
        response.body(jsonResponse);
        return response.body();
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

    private Ingreso asignarIngresoDesde(IngresoRequest ingresoRequest, EntidadJuridica entidadJuridica) {
        if(ingresoRequest.descripcion == null || ingresoRequest.fechaOperacion == null || ingresoRequest.montoTotal == null){
            return null;
        }
        Ingreso ingreso = new Ingreso();
        ingreso.setDescripcion(ingresoRequest.descripcion);
        ingreso.setFechaOperacion(ingresoRequest.fechaOperacion);
        ingreso.setOrganizacion(entidadJuridica);
        ingreso.setMontoTotal(ingresoRequest.montoTotal);

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
