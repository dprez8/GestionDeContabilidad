package Domain.Controllers;

import Domain.Entities.BandejaDeMensajes.Mensaje;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;


public class BandejaDeMensajesRestController {
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Mensaje> repoMensajes;
    private CodeResponse codeResponse;

    public BandejaDeMensajesRestController() {
        this.repoUsuarios = new Repositorio<>(new DaoHibernate<Usuario>(Usuario.class));
        this.repoMensajes = new Repositorio<>(new DaoHibernate<Mensaje>(Mensaje.class));
        this.codeResponse = new CodeResponse();
    }

    public String mostrarMensajes(Request request, Response response) {

        Estandar usuario = (Estandar) asignarUsuarioSiEstaLogueado(request);

        filtrarMensajesDeUsuario(usuario);

        response.type("application/json");

        return "";
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
            this.codeResponse.setCode(404);
            return;
        }
        List<Mensaje> mensajesUsuario = mensajes.stream()
                                                .filter(unMensaje-> usuario.getId() == unMensaje.getUsuario().getId())
                                                .collect(Collectors.toList());
        usuario.getBandejaDeMensajes().setMensajes(mensajesUsuario);
        return;
    }

    private class BandejaResponse {

    }
}
