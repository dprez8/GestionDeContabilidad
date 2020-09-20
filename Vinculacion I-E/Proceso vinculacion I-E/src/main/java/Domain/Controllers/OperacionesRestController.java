package Domain.Controllers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class OperacionesRestController {

    public Response obtenerOperaciones(Request request, Response response){
        return response;
    }

    public String retornarOperaciones(Request request, Response response){

        Gson gson = new Gson();

        //String unaOperacion = gson.toJson(egreso);
        response.type("application/json");

        return  "";
    }
}
