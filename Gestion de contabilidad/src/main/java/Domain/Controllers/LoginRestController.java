package Domain.Controllers;

import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Daos.RepositorioUsuarios;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.nio.charset.StandardCharsets;

public class LoginRestController {
    private RepositorioUsuarios repositorioUsuarios;

    public LoginRestController() {
        repositorioUsuarios = new RepositorioUsuarios(new DaoHibernate<Usuario>(Usuario.class));
    }

    public String login(Request request, Response response) {
        Gson gson = new Gson();
        LoginResponse loginResponse = new LoginResponse();
        //Obtengo por post el json de nombre de usuario y contraseña, transformandolo en un objeto de la innerclass UsuarioRequest
        UsuarioRequest usuarioRequest = gson.fromJson(request.body(),UsuarioRequest.class);

        //Hasheo la contrasenia que recibo por post para comprobar que sea igual a la que estaria en la  BD
        String passwordHash = Hashing.sha256()
                    .hashString(usuarioRequest.password, StandardCharsets.UTF_8)
                    .toString();

        Usuario usuario = repositorioUsuarios
                                .buscarPorQuery("from usuario where nombre = " + usuarioRequest.username +
                                                " and contrasenia = " + passwordHash);

        if(usuario != null){
            loginResponse.code = 200;
            loginResponse.message = "Se inicio sesion exitosamente";
        }
        else{
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

    private class LoginResponse {
        public int code;
        public String message;
    }
}
