package Domain.Controllers;

import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Daos.RepositorioUsuarios;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.nio.charset.StandardCharsets;

public class LoginRestController {

    public LoginRestController() {
    }

    public String login(Request request, Response response) {
        Gson gson = new Gson();
        Respuesta loginResponse = new Respuesta();
        Usuario usuario = null;

        //Obtengo por post el json de nombre de usuario y contraseña, transformandolo en un objeto de la innerclass UsuarioRequest
        UsuarioRequest usuarioRequest = gson.fromJson(request.body(),UsuarioRequest.class);

        //Hasheo la contrasenia que recibo por post para comprobar que sea igual a la que estaria en la  BD
        String passwordHash = Hashing.sha256()
                    .hashString(usuarioRequest.password, StandardCharsets.UTF_8)
                    .toString();
        try{
             usuario = (Usuario) EntityManagerHelper
                    .createQuery("from Usuario where nombre = :username and contrasenia = :password")
                    .setParameter("username",usuarioRequest.username)
                    .setParameter("password",usuarioRequest.password)
                    .getSingleResult();

             if(usuario != null) {
                 request.session(true);
                 request.session().attribute("id", usuario.getId());
             }
             loginResponse.code = 200;
             loginResponse.message = "Se inicio sesion exitosamente";
        }
        catch (NoResultException ex){
            loginResponse.code = 404;
            loginResponse.message =  "No coincide el usuario con la contraseña";
        }
        String jsonLogin = gson.toJson(loginResponse);

        response.type("application/json");
        response.body(jsonLogin);

        return response.body();
    }

    private class UsuarioRequest {
        public String username;
        public String password;
    }
}
