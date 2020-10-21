package Domain.Controllers;


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
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        BandejaResponse bandejaResponse;

        Estandar usuario = (Estandar) asignarUsuarioSiEstaLogueado(request);

        response.type("application/json");

        if(usuario == null) {
            this.codeResponse.setCode(200);
            this.codeResponse.setMessage("Usuario fuera de sesion");

            String JsonResponse = gson.toJson(this.codeResponse);

            return JsonResponse;
        }

        filtrarMensajesDeUsuario(usuario);
        bandejaResponse  = asignarDatosABandejaResponse(usuario);
        //Supongo que no es tan directa la relacion para mapear estos datos, aun debo resolver esto
        String jsonBandeja = gson.toJson(bandejaResponse);

        response.body(jsonBandeja);

        return response.body();
    }

    private Usuario asignarUsuarioSiEstaLogueado(Request request){
        if(!request.session().isNew() && request.session().attribute("id") != null) {
            Usuario usuario =  repoUsuarios.buscar(request.session().attribute("id"));
            return usuario;
        }
        return null;
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
        bandejaResponse.msg         = codeResponse.getMessage();
        bandejaResponse.usuarioId   = estandar.getId();
        bandejaResponse.mensajes    = estandar.getBandejaDeMensajes().getMensajes();
        return bandejaResponse;
    }

    private class BandejaResponse {
        @Expose
        public int code;
        @Expose
        public String msg;
        @Expose
        public int usuarioId;
        @Expose
        public List<Mensaje> mensajes;
    }
}
