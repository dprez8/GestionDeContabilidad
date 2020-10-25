package Domain.Controllers;

import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.Usuarios.Usuario;
import Domain.Exceptions.FueraDeSesion;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class PermisosRestController {
    private static Repositorio<Usuario> repoUsuarios = new Repositorio<>(new DaoHibernate<Usuario>(Usuario.class));
    private static Respuesta respuesta = new Respuesta();


    public static Usuario verificarSesion(Request request, Response response) {
        Gson json = new Gson();
        try {
            Usuario usuario = asignarUsuarioSiEstaLogueado(request);
            return usuario;
        }
        catch (FueraDeSesion ex) {
            respuesta.setCode(200);
            respuesta.setMessage(ex.getMessage());
            String jsonResponseError = json.toJson(respuesta);
            response.body(jsonResponseError);
            return null;
        }
    }

    /**Decido modelar una excepcion en caso de no estar logueado o no exista el usuario,
     * ya que considero que este control es muy importante para todas las request,
     * sin este requisito, se deberia cortar el flujo de informacion al sistema**/

    private static Usuario asignarUsuarioSiEstaLogueado(Request request) throws FueraDeSesion {
        if(!request.session().isNew() && request.session().attribute("id") != null) {
            Usuario usuario =  repoUsuarios.buscar(request.session().attribute("id"));
            return usuario;
        }
        throw new FueraDeSesion("Usuario fuera de sesion o no existente");
    }


}
