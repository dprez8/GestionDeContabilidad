package Domain.Controllers;

import Domain.Controllers.DTO.Respuesta;
import com.google.gson.Gson;
import spark.Response;

public abstract class GenericController {

    protected Respuesta respuesta;
    protected Gson gson;

    public String respuesta(Response response, int opCode, String mensaje) {
        this.respuesta.setCode(opCode);
        this.respuesta.setMessage(mensaje);
        String jsonResponse;
        jsonResponse = this.gson.toJson(this.respuesta);
        response.body(jsonResponse);
        return response.body();
    }

    public String error(Response response, String mensajeError) {
        this.respuesta.setCode(400);
        this.respuesta.setMessage(mensajeError);
        String jsonResponse;
        jsonResponse = this.gson.toJson(this.respuesta);
        response.body(jsonResponse);
        return response.body();
    }

    public GenericController () {
        this.respuesta = new Respuesta();
    }
}
