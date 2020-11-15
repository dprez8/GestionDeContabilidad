package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateTimeAdapter;
import Domain.Controllers.DTO.ConfigSchedulerRequest;
import Domain.Controllers.DTO.Respuesta;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.BandejaDeMensajes.Mensaje;
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
import java.util.Timer;
import java.util.stream.Collectors;


public class BandejaDeMensajesRestController extends GenericController{
    private Repositorio<Mensaje> repoMensajes;
    private Repositorio<SchedulerInit> repoScheduler;
    private Respuesta codeResponse;
    private String jsonResponse;

    public BandejaDeMensajesRestController(TokenService tokenService, String tokenPrefix) {
        super(tokenService,tokenPrefix);
        this.repoMensajes = new Repositorio<>(new DaoHibernate<Mensaje>(Mensaje.class));
        this.repoScheduler= new Repositorio<>(new DaoHibernate<>(SchedulerInit.class));
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
        //Estandar usuario = (Estandar) asignarUsuarioPorRequestParams(request);

        filtrarMensajesDeUsuario(usuario);
        bandejaResponse  = asignarDatosABandejaResponse(usuario);
        this.jsonResponse = gson.toJson(bandejaResponse);

        response.body(jsonResponse);

        return response.body();
    }

    public String configurar(Request request, Response response) {
        //Administrador usuario = (Administrador) getUsuarioDesdeRequest(request);
        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
        Gson gson = new Gson();
        ConfigSchedulerRequest configSchedulerRequest = gson.fromJson(request.body(),ConfigSchedulerRequest.class);

        SchedulerInit schedulerInit = this.repoScheduler.buscar(usuario.getMiOrganizacion().getSchedulerInit().getId());

        if(!configuracionExistosa(configSchedulerRequest, schedulerInit)) {
            this.codeResponse.setCode(400);
            this.codeResponse.setMessage("Error en los datos de envio");
            this.jsonResponse = gson.toJson(this.codeResponse);
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
        //Usuario usuario = getUsuarioDesdeRequest(request);
        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        SchedulerInit schedulerInit = usuario.getMiOrganizacion().getSchedulerInit();

        ConfigResponse configResponse = new ConfigResponse();
        configResponse.code           = 200;
        configResponse.message        = "Ok";
        configResponse.schedulerInit = schedulerInit;

        this.jsonResponse = gson.toJson(configResponse);
        response.body(jsonResponse);
        return response.body();
    }

    public String mensajeVisto(Request request, Response response) {
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
        HashMap<Integer, Timer> timerHashMap = TimersController.instancia().getTimerHashMap();

        Timer timer = timerHashMap.get(id);
        timerHashMap.remove(id);

        timer.cancel();

        timer = new Timer();

        timerHashMap.put(id,timer);

        TimersController.instancia().setTimerHashMap(timerHashMap);

        return timer;
    }

    private Tarea obtenerTareaYActualizar(Integer id) {
        HashMap<Integer, Tarea> tareaHashMap = TimersController.instancia().getSchedulerHashMap();

        Tarea tarea = tareaHashMap.get(id);
        tareaHashMap.remove(id);

        tarea.cancel();

        tarea = new Tarea();

        tareaHashMap.put(id, tarea);

        TimersController.instancia().setSchedulerHashMap(tareaHashMap);

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
