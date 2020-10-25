package Domain.Controllers;


import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.BandejaDeMensajes.Mensaje;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class BandejaDeMensajesRestController {
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Mensaje> repoMensajes;
    private Respuesta codeResponse;

    public BandejaDeMensajesRestController() {
        this.repoUsuarios = new Repositorio<>(new DaoHibernate<Usuario>(Usuario.class));
        this.repoMensajes = new Repositorio<>(new DaoHibernate<Mensaje>(Mensaje.class));
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
        String jsonBandeja = gson.toJson(bandejaResponse);

        response.body(jsonBandeja);

        return response.body();
    }

    private void filtrarMensajesDeUsuario(Estandar usuario) {
        List<Mensaje> mensajes;
        mensajes = this.repoMensajes.buscarTodos();
        if(mensajes.isEmpty()){
            this.codeResponse.setCode(204);
            this.codeResponse.setMessage("No Content");
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
}
