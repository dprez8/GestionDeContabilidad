package Domain.Controllers;

import Domain.DTO.OperacionesDTO;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class OperacionesRestController {

    public Response obtenerOperaciones(Request request, Response response) {

        Gson gson = new Gson();

        OperacionesDTO operacionesDTO = gson.fromJson(request.body(), OperacionesDTO.class);

        System.out.println(operacionesDTO.getEgresos());

        return response;
    }

    public String retornarOperaciones(Request request, Response response) {

        Gson gson = new Gson();

        //String unaOperacion = gson.toJson(egreso);
        response.type("application/json");

        return  "";
    }
}
