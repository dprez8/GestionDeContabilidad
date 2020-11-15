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
    private static final String PREFIX_ADMIN = "/api/admin";
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";
    private static final String ESTANDAR  = "Estandar";
    private static final String ADMIN  = "Administrador";

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
        response.type("application/json");
        if (!isLoginRequest(request) && !isRegistrationRequest(request)) {
            String authorizationHeader = request.headers("Authorization");
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
            String token = authorizationHeader.replace(TOKEN_PREFIX, "");

            if(isEstandar(token)) {
                if(     !isProveedorRequest(request) && !isMensajesRequest(request) && !isOperacionesRequest(request) &&
                        !isCriteriosCategoriasRequest(request) && !isPresupuestoRequest(request)) {
                    this.respuesta.setCode(403);
                    this.respuesta.setMessage("No posees permisos de administrador");
                    String jsonResponseError = gson.toJson(this.respuesta);
                    response.body(jsonResponseError);
                    Spark.halt(200, response.body());
                }

            }else if(isAdmin(token)) {
                if(!isAdminRequests(request)) {
                    this.respuesta.setCode(403);
                    this.respuesta.setMessage("No posees permisos de estandar");
                    String jsonResponseError = gson.toJson(this.respuesta);
                    response.body(jsonResponseError);
                    Spark.halt(200, response.body());
                }
            }
        }
    }

    private boolean isLoginRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + LOGIN_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }

    private boolean isRegistrationRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + REGISTRATION_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }

    private boolean isEstandar(String token) {
        return tokenService.getRol(token).equals(ESTANDAR);
    }

    private boolean isAdmin(String token) {
        return tokenService.getRol(token).equals(ADMIN);
    }

    private boolean isAdminRequests(Request request) {
        return  request.uri().equals(PREFIX_ADMIN+"/bandeja/configurar") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals(PREFIX_ADMIN+"/organizacion") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals(PREFIX_ADMIN+"/categoria") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals(PREFIX_ADMIN+"/criterio") && request.requestMethod().equals(HTTP_POST) ;
    }

    private boolean isProveedorRequest(Request request) {
        return  request.uri().equals("/api/proveedor") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals("/api/proveedores") && request.requestMethod().equals(HTTP_GET);
    }

    private boolean isMensajesRequest(Request request) {
        return  request.uri().equals("/api/bandeja") && request.requestMethod().equals(HTTP_GET) ||
                request.uri().equals("/api/bandeja/visto") && request.requestMethod().equals(HTTP_POST);
    }

    private boolean isOperacionesRequest(Request request) {
        return  request.uri().equals("/api/operaciones/egreso") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals("/api/operaciones/egresos") && request.requestMethod().equals(HTTP_GET) ||
                request.uri().equals("/api/operaciones/ingresos") && request.requestMethod().equals(HTTP_GET)||
                request.uri().equals("/api/operaciones/ingreso") && request.requestMethod().equals(HTTP_POST)||
                request.uri().equals("/api/operaciones/asociarManualmente") && request.requestMethod().equals(HTTP_POST)||
                request.uri().equals("/api/operaciones/egreso/cargarArchivos") && request.requestMethod().equals(HTTP_POST);
    }

    private boolean isCriteriosCategoriasRequest(Request request) {
        return request.uri().equals("/api/categorias/asociar") && request.requestMethod().equals(HTTP_POST);
    }

    private boolean isPresupuestoRequest(Request request) {
        return request.uri().equals("/api/operaciones/presupuesto") && request.requestMethod().equals(HTTP_POST);
    }

}
