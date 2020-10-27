package Domain.Controllers;


import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.ConfigSchedulerRequest;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.BandejaDeMensajes.Mensaje;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.ValidadorTransparencia.Scheduler;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class BandejaDeMensajesRestController {
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Mensaje> repoMensajes;
    private Repositorio<Scheduler> repoScheduler;
    private Respuesta codeResponse;
    private String jsonResponse;

    public BandejaDeMensajesRestController() {
        this.repoUsuarios = new Repositorio<>(new DaoHibernate<Usuario>(Usuario.class));
        this.repoMensajes = new Repositorio<>(new DaoHibernate<Mensaje>(Mensaje.class));
        this.repoScheduler= new Repositorio<>(new DaoHibernate<>(Scheduler.class));
        this.codeResponse = new Respuesta();
    }

    public String mostrarMensajes(Request request, Response response) {
        /**anotar los campos que se debe serializar con @Expose**/
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        response.type("application/json");

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }
        BandejaResponse bandejaResponse;
        //Estandar usuario = (Estandar) asignarUsuarioPorRequestParams(request);

        filtrarMensajesDeUsuario(usuario);
        bandejaResponse  = asignarDatosABandejaResponse(usuario);
        this.jsonResponse = gson.toJson(bandejaResponse);

        response.body(jsonResponse);

        return response.body();
    }

    public String configurar(Request request, Response response) {
        response.type("application/json");
        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

        Gson gson = new Gson();
        ConfigSchedulerRequest configSchedulerRequest = gson.fromJson(request.body(),ConfigSchedulerRequest.class);

        Scheduler scheduler = usuario.getMiOrganizacion().getScheduler();

        if(!configuracionExistosa(configSchedulerRequest,scheduler)) {
            this.codeResponse.setCode(400);
            this.codeResponse.setMessage("Error en los datos de envio");
            this.jsonResponse = gson.toJson(this.codeResponse);
            return jsonResponse;
        }

        this.repoScheduler.modificar(scheduler);

        this.codeResponse.setCode(200);
        this.codeResponse.setMessage("Ok");
        this.jsonResponse = gson.toJson(this.codeResponse);
        return response.body();
    }

    public String mostrarConfiguracion(Request request, Response response) {
        response.type("application/json");
        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Scheduler scheduler = usuario.getMiOrganizacion().getScheduler();

        ConfigResponse configResponse = new ConfigResponse();
        configResponse.code           = 200;
        configResponse.message        = "Ok";
        configResponse.scheduler      = scheduler;

        this.jsonResponse = gson.toJson(configResponse);
        response.body(jsonResponse);
        return response.body();
    }

    public String mensajeVisto(Request request, Response response) {
        response.type("application/json");
        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }
        Gson gson = new Gson();
        MensajeId idMensaje = gson.fromJson(request.body(),MensajeId.class);

        try {
            Mensaje mensaje = this.repoMensajes.buscar(idMensaje.id);
            mensaje.setLeido(true);
            this.repoMensajes.modificar(mensaje);
        }
        catch (NoResultException ex) {
            this.codeResponse.setCode(404);
            this.codeResponse.setMessage("El mensaje no existe");
            this.jsonResponse = gson.toJson(this.codeResponse);
            return jsonResponse;
        }
        this.codeResponse.setCode(200);
        this.codeResponse.setMessage("Mensaje modificado");
        this.jsonResponse = gson.toJson(this.codeResponse);
        response.body(jsonResponse);
        return response.body();
    }

    private void filtrarMensajesDeUsuario(Estandar usuario) {
        List<Mensaje> mensajes;
        mensajes = this.repoMensajes.buscarTodos();
        if(mensajes.isEmpty()){
            this.codeResponse.setCode(404);
            this.codeResponse.setMessage("Recurso no existente");
            return;
        }
        List<Mensaje> mensajesUsuario = mensajes.stream()
                                                .filter(unMensaje-> usuario.getId() == unMensaje.getUsuario().getId())
                                                .collect(Collectors.toList());
        usuario.getBandejaDeMensajes().setMensajes(mensajesUsuario);
        this.codeResponse.setCode(200);
        this.codeResponse.setMessage("Ok");
        return;
    }
    
    private BandejaResponse asignarDatosABandejaResponse(Estandar estandar) {
        BandejaResponse bandejaResponse = new BandejaResponse();
        bandejaResponse.code        = codeResponse.getCode();
        bandejaResponse.message     = codeResponse.getMessage();
        bandejaResponse.usuarioId   = estandar.getId();
        bandejaResponse.mensajes    = estandar.getBandejaDeMensajes().getMensajes();
        return bandejaResponse;
    }

    private Usuario asignarUsuarioPorRequestParams(Request request) {
        try {
            Usuario usuario =  this.repoUsuarios.buscar(new Integer(request.params("usuarioId")));
            return usuario;
        }
        catch (NullPointerException ex) {
            return null;
        }
    }

    private boolean configuracionExistosa(ConfigSchedulerRequest configSchedulerRequest,Scheduler scheduler) {
        if(configSchedulerRequest.dias.isEmpty() || configSchedulerRequest.horaInicio == null || configSchedulerRequest.minutoInicio == null){
            return false;
        }
        scheduler.setHoraInicio(configSchedulerRequest.horaInicio);
        scheduler.setMinutoInicio(configSchedulerRequest.minutoInicio);
        scheduler.setDias(configSchedulerRequest.dias);
        return true;
    }

    private class BandejaResponse {
        @Expose
        public int code;
        @Expose
        public String message;
        @Expose
        public int usuarioId;
        @Expose
        public List<Mensaje> mensajes;
    }

    private class ConfigResponse {
        @Expose
        public int code;
        @Expose
        public String message;
        @Expose
        public Scheduler scheduler;
    }
    private class MensajeId {
        public Integer id;
    }
}
