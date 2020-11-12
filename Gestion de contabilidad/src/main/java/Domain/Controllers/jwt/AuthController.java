package Domain.Controllers.jwt;

import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class AuthController extends AbstractTokenController{
    private static final String ROLE_PROPERTY = "role";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER_NAME_PROPERTY = "username";
    private static final String FIRST_NAME_PROPERTY = "firstName";
    private static final String LAST_NAME_PROPERTY = "lastName";
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
                        .createQuery("from Usuario where nombre = :username and contrasenia = :password")
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
        Integer userId = getUserIdDesdeToken(request);

        this.repoUsuarios = new Repositorio<>(new DaoHibernate<>(Usuario.class));

        Usuario user = this.repoUsuarios.buscar(userId);
        JsonObject userJson = new JsonObject();
        userJson.addProperty(USER_NAME_PROPERTY, user.getId());
        userJson.addProperty(FIRST_NAME_PROPERTY, user.getNombre());
        userJson.addProperty(LAST_NAME_PROPERTY, user.getMail());
        return userJson.toString();
    }

    public String refresh(Request request, Response response) {
        String authorizationHeader = request.headers(AUTHORIZATION_HEADER);
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        Integer userId = getUserIdDesdeToken(request);

        this.repoUsuarios = new Repositorio<>(new DaoHibernate<>(Usuario.class));
        Usuario user = this.repoUsuarios.buscar(userId);

        tokenService.revokeToken(token);
        String refreshedToken = tokenService.newToken(user);
        response.header(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + refreshedToken);
        return "";
    }


    //public void init() {

        // AUTH FILTER
        //before(new AuthFilter(AUTH_ENDPOINT_PREFIX, tokenService));

        // REGISTRATION ENDPOINT
        /**post(AUTH_ENDPOINT_PREFIX + "/registration", (request, response) -> register(request, response));*/

        // LOGIN ENDPOINT
        //post(AUTH_ENDPOINT_PREFIX + "/login", (request, response) -> login(request, response));

        // LOGOUT ENDPOINT
        //post(AUTH_ENDPOINT_PREFIX + "/logout", (request, response) -> logout(request));

        // REFRESH ENDPOINT
        //post(AUTH_ENDPOINT_PREFIX + "/token", (request, response) -> refresh(request, response));

        // ME ENDPOINT
        //get(AUTH_ENDPOINT_PREFIX + "/me", (request, response) -> me(request, response));

        // ASSIGN ROLE_PROPERTY
        /**post(AUTH_ENDPOINT_PREFIX + "/roles", (request, response) -> assignRole(request));

        // REVOKE ROLE_PROPERTY
        Spark.delete(AUTH_ENDPOINT_PREFIX + "/roles", (request, response) -> revokeRole(request));*/

   // }
    /*
    private String revokeRole(Request request) throws IOException {
        if (hasRole(request, new Role[]{Role.ADMIN})) {
            String json = request.raw().getReader().lines().collect(Collectors.joining());
            JsonObject jsonRequest = this.gson.fromJson(json, JsonObject.class);
            if (jsonRequest.has(USER_NAME_PROPERTY) && jsonRequest.has(ROLE_PROPERTY)) {
                Role role = Role.valueOf(jsonRequest.get(ROLE_PROPERTY).getAsString());
                if (role != null) {
                    User user = this.userService.get(jsonRequest.get(USER_NAME_PROPERTY).getAsString());
                    if (user != null) {
                        user.revokeRole(role);
                        this.userService.update(user);
                    }
                }
            }
        } else {
            halt(401);
        }

        return "";
    }

    private String assignRole(Request request) throws IOException {
        if (hasRole(request, new Role[]{Role.ADMIN})) {
            String json = request.raw().getReader().lines().collect(Collectors.joining());
            JsonObject jsonRequest = gson.fromJson(json, JsonObject.class);
            if (jsonRequest.has(USER_NAME_PROPERTY) && jsonRequest.has(ROLE_PROPERTY)) {
                Role role = Role.valueOf(jsonRequest.get(ROLE_PROPERTY).getAsString());
                if (role != null) {
                    User user = userService.get(jsonRequest.get(USER_NAME_PROPERTY).getAsString());
                    if (user != null) {
                        user.assignRole(role);
                        userService.update(user);
                    }
                }
            }
        } else {
            halt(401);
        }

        return "";
    }

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

}
