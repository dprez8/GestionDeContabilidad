package Domain.Controllers.jwt;

import Domain.Controllers.DTO.Respuesta;
import com.google.gson.Gson;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.logging.Logger;

public class AuthFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AuthFilter.class.getName());

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/registration";
    private static final String HTTP_POST = "POST";

    private final String authEndpointPrefix;

    private TokenService tokenService;
    private Gson gson;
    private Respuesta respuesta;

    public AuthFilter(String authEndpointPrefix, TokenService tokenService) {
        this.authEndpointPrefix = authEndpointPrefix;
        this.tokenService = tokenService;
        this.gson         = new Gson();
        this.respuesta    = new Respuesta();
    }

    @Override
    public void handle(Request request, Response response) {
        if (!isLoginRequest(request) && !isRegistrationRequest(request)) {
            String authorizationHeader = request.headers("Authorization");
            response.type("application/json");
            if (authorizationHeader == null) {
                this.respuesta.setCode(403);
                this.respuesta.setMessage("Autorizacion de header perdido");
                String jsonResponseError = gson.toJson(this.respuesta);
                response.body(jsonResponseError);
                Spark.halt(200,response.body());

            } else if (!tokenService.validateToken(authorizationHeader.replace(TOKEN_PREFIX, ""))) {
                this.respuesta.setCode(401);
                this.respuesta.setMessage("Token vencido");
                String jsonResponseError = gson.toJson(this.respuesta);
                response.body(jsonResponseError);
                Spark.halt(200,response.body());
            }
        }
    }

    private boolean isLoginRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + LOGIN_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }

    private boolean isRegistrationRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + REGISTRATION_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }
}
