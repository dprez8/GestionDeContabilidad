package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateTimeAdapter;
import Domain.Controllers.DTO.ConfigSchedulerRequest;
import Domain.Controllers.DTO.Respuesta;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.BandejaDeMensajes.Mensaje;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.ValidadorTransparencia.Tarea;
import Domain.Entities.ValidadorTransparencia.SchedulerInit;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.stream.Collectors;


public class BandejaDeMensajesRestController extends GenericController{
    private Repositorio<Mensaje> repoMensajes;
    private Repositorio<SchedulerInit> repoScheduler;
    private Respuesta codeResponse;
    private String jsonResponse;
    private Repositorio<Organizacion> repoOrganizacion;

    public BandejaDeMensajesRestController(TokenService tokenService, String tokenPrefix) {
        super(tokenService,tokenPrefix);
        this.repoMensajes = new Repositorio<>(new DaoHibernate<Mensaje>(Mensaje.class));
        this.repoScheduler= new Repositorio<>(new DaoHibernate<>(SchedulerInit.class));
        this.repoOrganizacion = new Repositorio<>(new DaoHibernate<>(Organizacion.class));
        this.codeResponse = new Respuesta();
    }

    public String mostrarMensajes(Request request, Response response) {
        /**anotar los campos que se debe serializar con @Expose**/
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
                .create();

        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
        BandejaResponse bandejaResponse;

        filtrarMensajesDeUsuario(usuario);
        bandejaResponse  = asignarDatosABandejaResponse(usuario);
        this.jsonResponse = gson.toJson(bandejaResponse);

        response.body(jsonResponse);

        return response.body();
    }

    public String configurar(Request request, Response response) {
        this.gson = new Gson();
        Organizacion organizacion;
        ConfigSchedulerRequest configSchedulerRequest;
        try {
            configSchedulerRequest = this.gson.fromJson(request.body(),ConfigSchedulerRequest.class);
            organizacion = this.repoOrganizacion.buscar(configSchedulerRequest.organizacionId);
        }catch (Exception ex) {
            return error(response, ex.getMessage());
        }

        SchedulerInit schedulerInit = organizacion.getSchedulerInit();

        if(!configuracionExistosa(configSchedulerRequest, schedulerInit)) {
            this.codeResponse.setCode(400);
            this.codeResponse.setMessage("Error en los datos de envio");
            this.jsonResponse = this.gson.toJson(this.codeResponse);
            return jsonResponse;
        }

        Tarea tarea = obtenerTareaYActualizar(schedulerInit.getId());
        Timer timer = obtenerTimerYActualizar(schedulerInit.getId());

        schedulerInit.setTarea(tarea);
        schedulerInit.setTimer(timer);

        this.repoScheduler.modificar(schedulerInit);

        schedulerInit.arrancarTarea();

        this.codeResponse.setCode(200);
        this.codeResponse.setMessage("Ok");
        this.jsonResponse = gson.toJson(this.codeResponse);
        response.body(this.jsonResponse);
        return response.body();
    }

    public String mostrarConfiguracion(Request request, Response response) {
        Usuario usuario = getUsuarioDesdeRequest(request);
        if(isAdmin(usuario)) {
            mostrarConfiguracionDeAdministrador(request, response,(Administrador) usuario);
        }
        else
            mostrarConfiguracionEstandar(response,(Estandar) usuario);
        return response.body();
    }

    public String mensajeVisto(Request request, Response response) {
        Gson gson = new Gson();
        MensajeId idMensaje = null;
        try {
        	 idMensaje = gson.fromJson(request.body(),MensajeId.class);
            }
            catch(Exception ex){
            	 this.respuesta.setCode(404);
                 this.respuesta.setMessage("No se logro mapear el mensaje json");
                 this.jsonResponse = gson.toJson(this.respuesta);
                 response.body(this.jsonResponse);
                 return response.body();
            }
       
        
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

    /*************** Private methods***************************************/

    private void mostrarConfiguracionEstandar(Response response, Estandar estandar) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        SchedulerInit schedulerInit = estandar.getMiOrganizacion().getSchedulerInit();

        ConfigResponse configResponse = new ConfigResponse();
        configResponse.code           = 200;
        configResponse.message        = "Ok";
        configResponse.schedulerInit = schedulerInit;

        this.jsonResponse = gson.toJson(configResponse);
        response.body(jsonResponse);
    }

    private String mostrarConfiguracionDeAdministrador(Request request, Response response, Administrador administrador) {
        Gson gson1 = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        this.gson = new Gson();
        Organizacion organizacion;
        try{
             List<EntidadJuridica> entidadJuridicas = administrador.getJuridicas();
             organizacion = this.repoOrganizacion.buscar(new Integer(request.params("organizacionId")));
             if(!(entidadJuridicas.stream().anyMatch(entidad->entidad.getId() == organizacion.getId()))){
                 return respuesta(response,403,"No posees permisos para ver esta configuracion");
             }
        }catch (Exception ex) {
            return respuesta(response,404,"No existe la organizacion");
        }
        SchedulerInit schedulerInit = organizacion.getSchedulerInit();

        ConfigResponse configResponse = new ConfigResponse();
        configResponse.code           = 200;
        configResponse.message        = "Ok";
        configResponse.schedulerInit  = schedulerInit;

        this.jsonResponse = gson1.toJson(configResponse);
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

    private boolean configuracionExistosa(ConfigSchedulerRequest configSchedulerRequest, SchedulerInit schedulerInit) {
        if(configSchedulerRequest.dias.isEmpty() || configSchedulerRequest.horaInicio == null || configSchedulerRequest.minutoInicio == null){
            return false;
        }

        schedulerInit.setHoraInicio(configSchedulerRequest.horaInicio);
        schedulerInit.setMinutoInicio(configSchedulerRequest.minutoInicio);
        schedulerInit.setDias(configSchedulerRequest.dias);
        return true;
    }

    private Timer obtenerTimerYActualizar(Integer id) {
        Timer timer = TimersController.instancia().getTimer(id);
        timer.cancel();
        TimersController.instancia().removeTimer(id);
        timer = new Timer();
        TimersController.instancia().setTimer(id,timer);
        return timer;
    }

    private Tarea obtenerTareaYActualizar(Integer id) {
        Tarea tarea = TimersController.instancia().getTarea(id);
        tarea.cancel();
        TimersController.instancia().removeScheduler(id);
        tarea = new Tarea();
        TimersController.instancia().setScheduler(id,tarea);
        return tarea;
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
        public SchedulerInit schedulerInit;
    }
    private class MensajeId {
        public Integer id;
    }
}
