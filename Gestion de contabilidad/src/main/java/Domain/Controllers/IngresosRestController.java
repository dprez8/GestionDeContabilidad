package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.IngresoResponse;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.ApiVinculador.IngresoAEnviar;
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

    private IngresoResponse mapearIngresos(Ingreso ingreso) {
        IngresoResponse ingresoAEnviar = new IngresoResponse();
        ingresoAEnviar.id              = ingreso.getId();
        ingresoAEnviar.fechaOperacion  = ingreso.getFechaOperacion();
        ingresoAEnviar.descripcion     = ingreso.getDescripcion();
        ingresoAEnviar.montoTotal      = ingreso.montoSobrante();
        ingresoAEnviar.egresos         = ingreso.getEgresos()
                                                .stream()
                                                .map(unEgreso->unEgreso.getId())
                                                .collect(Collectors.toList());
        return ingresoAEnviar;
    }

    private class ListadoIngresos {
        public int code;
        public String message;
        public List<IngresoResponse> ingresos;
    }
}
