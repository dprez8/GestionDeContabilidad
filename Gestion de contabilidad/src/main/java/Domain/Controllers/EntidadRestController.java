package Domain.Controllers;

import Domain.Controllers.jwt.TokenService;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import com.google.gson.GsonBuilder;

import com.google.gson.annotations.Expose;
import spark.Request;
import spark.Response;


public class EntidadRestController extends GenericController{

    public EntidadRestController(TokenService tokenService, String tokenPrefix) {
        super(tokenService,tokenPrefix);
    }

    public String listarEntidades(Request request, Response response){
        Usuario usuario = getUsuarioDesdeRequest(request);
        if(isAdmin(usuario))
            return respuesta(response,403,"No posees permisos de estandar");
        this.gson = new GsonBuilder()
                        .serializeNulls()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();

        Estandar estandar = (Estandar) usuario;
        Organizacion organizacion = estandar.getMiOrganizacion();
        OrganizacionResponse organizacionResponse = new OrganizacionResponse();
        organizacionResponse.code         = 200;
        organizacionResponse.message      = "Ok";
        organizacionResponse.organizacion = organizacion;

        this.jsonResponse = this.gson.toJson(organizacionResponse);
        response.body(this.jsonResponse);
        return response.body();
    }

    private class OrganizacionResponse {
        @Expose
        public int code;
        @Expose
        public String message;
        @Expose
        public Organizacion organizacion;
    }
}
