package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
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

        Gson gson2 = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();

        Respuesta errorResponse = new Respuesta();
        Usuario usuario;
        Estandar estandar = null;
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
                    .setParameter("password",passwordHash)
                    .getSingleResult();

             if(usuario != null) {
                 request.session(true);
                 request.session().attribute("id", usuario.getId());
                 estandar = (Estandar) usuario;
             }
             errorResponse.setCode(200);
             errorResponse.setMessage("Se inicio sesion exitosamente");
        }
        catch (NoResultException ex){
            errorResponse.setCode(404);
            errorResponse.setMessage("No coincide el usuario con la contraseña");
        }

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.code            = errorResponse.getCode();
        usuarioResponse.message         = errorResponse.getMessage();
        usuarioResponse.organizacion    = null;
        if(estandar != null){
            usuarioResponse.organizacion    = estandar.getMiOrganizacion();
            usuarioResponse.nombre          = estandar.getNombre();
        }
        String jsonLogin = gson2.toJson(usuarioResponse);

        response.type("application/json");
        response.body(jsonLogin);

        return response.body();
    }

    public String sessionStatus(Request request, Response response) {
        response.type("application/json");
        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.code            = 200;
        usuarioResponse.message         = "Ok";
        usuarioResponse.organizacion    = usuario.getMiOrganizacion();
        usuarioResponse.nombre          = usuario.getNombre();
        String jsonLogin = gson.toJson(usuarioResponse);

        response.body(jsonLogin);
        return response.body();
    }

    private class UsuarioRequest {
        public String username;
        public String password;
    }

    private class UsuarioResponse {
        @Expose
        public Integer code;
        @Expose
        public String message;
        @Expose
        public Organizacion organizacion;
        @Expose
        public String nombre;
    }
}
