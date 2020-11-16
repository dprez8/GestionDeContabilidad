package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.Respuesta;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

public abstract class GenericController {

    private static String TOKEN_PREFIX;

    protected Respuesta respuesta;
    protected Gson gson;
    protected TokenService tokenService;
    protected String jsonResponse;

    protected String respuesta(Response response, int opCode, String mensaje) {
        this.respuesta.setCode(opCode);
        this.respuesta.setMessage(mensaje);
        String jsonResponse;
        jsonResponse = this.gson.toJson(this.respuesta);
        response.body(jsonResponse);
        return response.body();
    }

    protected String error(Response response, String mensajeError) {
        this.respuesta.setCode(400);
        this.respuesta.setMessage(mensajeError);
        String jsonResponse;
        jsonResponse = this.gson.toJson(this.respuesta);
        response.body(jsonResponse);
        return response.body();
    }

    protected Usuario getUsuarioDesdeRequest(Request request) {
        String authorizationHeader = request.headers("Authorization");
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        Usuario user = tokenService.getUser(token);
        return user;
    }

    protected boolean isAdmin(Usuario usuario) {
        return usuario.getClass().equals(Administrador.class);
    }

    protected String toJson(Response response, Object respuesta) {
        jsonResponse = this.gson.toJson(respuesta);
        response.body(jsonResponse);
        return response.body();
    }

    public GenericController (TokenService tokenService, String tokenPrefix) {
        this.respuesta = new Respuesta();
        this.tokenService = tokenService;
        TOKEN_PREFIX      = tokenPrefix;
        this.gson         = new Gson();
    }
}
