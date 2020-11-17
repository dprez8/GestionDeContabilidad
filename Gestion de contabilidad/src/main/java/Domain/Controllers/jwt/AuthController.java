package Domain.Controllers.jwt;

import Domain.Controllers.DTO.Respuesta;
import Domain.Controllers.DTO.UsuarioResponse;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class AuthController extends AbstractTokenController{
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER_NAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";

    private Gson gson;
    private TokenService tokenService;
    private Respuesta respuesta;
    private Repositorio<Usuario> repoUsuarios;

    public AuthController(TokenService tokenService) {
        super(tokenService);
        this.tokenService = tokenService;
        this.respuesta    = new Respuesta();
    }

    public String login(Request request, Response response) throws IOException {

        this.gson = new Gson();
        String json = request.raw().getReader().lines().collect(Collectors.joining());
        JsonObject jsonRequest = this.gson.fromJson(json, JsonObject.class);
        Usuario usuario;
        //Hasheo la contrasenia que recibo por post para comprobar que sea igual a la que estaria en la  BD
        String passwordHash = Hashing.sha256()
                    .hashString(jsonRequest.get(PASSWORD_PROPERTY).getAsString(), StandardCharsets.UTF_8)
                    .toString();
        if (validatePost(jsonRequest)) {
            try {
                usuario = (Usuario) EntityManagerHelper
                        .createQuery("from Usuario where username = :username and contrasenia = :password")
                        .setParameter("username", jsonRequest.get(USER_NAME_PROPERTY).getAsString())
                        .setParameter("password", passwordHash)
                        .getSingleResult();

                this.respuesta.setCode(200);
                this.respuesta.setMessage("Se inicio sesion exitosamente");
                response.header(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + tokenService.newToken(usuario));

            } catch (NoResultException ex) {
                this.respuesta.setCode(404);
                this.respuesta.setMessage("No coincide el usuario con la contraseña");
            }
        }

        String jsonLogin = this.gson.toJson(this.respuesta);

        response.body(jsonLogin);

        return response.body();
    }

    public String logout(Request request, Response response) {
        String authorizationHeader = request.headers(AUTHORIZATION_HEADER);
        tokenService.revokeToken(authorizationHeader.replace(TOKEN_PREFIX, ""));
        return "";
    }

    public String me(Request request, Response response) {
        Usuario user = getUserDesdeToken(request);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.code            = 200;
        usuarioResponse.message         = "Ok";
        usuarioResponse.nombre          = user.getNombre();
        usuarioResponse.username        = user.getUsername();
        usuarioResponse.apellido        = user.getApellido();
        usuarioResponse.email           = user.getMail();

        String jsonLogin = gson.toJson(usuarioResponse);

        response.body(jsonLogin);
        return response.body();
    }

    public String refresh(Request request, Response response) {
        String authorizationHeader = request.headers(AUTHORIZATION_HEADER);
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");

        Usuario user = getUserDesdeToken(request);
        tokenService.revokeToken(token);
        String refreshedToken = tokenService.newToken(user);
        response.header(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + refreshedToken);
        return "";
    }

    public String listadoUsuarios(Request request, Response response) {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        Administrador administrador = (Administrador) getUserDesdeToken(request);

        //List<UsuariosResponse> usuariosResponse = asignarUsuariosDesde(administrador.getJuridicas());
        return response.body();
    }

    private boolean isAdmin(Usuario usuario) {
        return usuario.getClass().equals(Administrador.class);
    }

    /*
    private List<UsuariosResponse> asignarUsuariosDesde(List<EntidadJuridica> entidadesJuridicas) {
        List<UsuariosResponse> usuariosResponse = entidadesJuridicas.stream().map(entidad->mapearUsuarios(entidad.getUsuarios()));
    }

    private UsuariosResponse mapearUsuarios(List<>)
    /*
    private String register(Request request, Response response) throws IOException {
        String json = request.raw().getReader().lines().collect(Collectors.joining());
        JsonObject jsonRequest = gson.fromJson(json, JsonObject.class);
        try {
            if (validatePost(jsonRequest)) {
                userService.register(jsonRequest.get(USER_NAME_PROPERTY).getAsString(),
                        BCrypt.hashpw(jsonRequest.get(PASSWORD_PROPERTY).getAsString(), BCRYPT_SALT),
                        jsonRequest.has(FIRST_NAME_PROPERTY) ? jsonRequest.get(FIRST_NAME_PROPERTY).getAsString() : null,
                        jsonRequest.has(LAST_NAME_PROPERTY) ? jsonRequest.get(LAST_NAME_PROPERTY).getAsString() : null);
                return "";
            } else {
                response.status(400);
            }
        } catch (IllegalArgumentException e) {
            response.status(400);
        }
        return "";
    }

    private void createAdminUser() {
        userService.register("admin", BCrypt.hashpw("admin", BCRYPT_SALT), null, null); //ADMIN USER
        User admin = userService.get("admin");
        admin.assignRole(Role.ADMIN);
        userService.update(admin);
    }
    */

    private boolean validatePost(JsonObject jsonRequest) {
        return jsonRequest != null && jsonRequest.has(USER_NAME_PROPERTY) && jsonRequest.has(PASSWORD_PROPERTY);
    }

    private class UsuariosResponse{
        public Integer code;
        public String message;
        public Usuario usuario;

    }

}
