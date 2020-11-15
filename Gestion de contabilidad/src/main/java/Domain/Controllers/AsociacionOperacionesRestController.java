package Domain.Controllers;

import Domain.Controllers.DTO.Respuesta;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;


public class AsociacionOperacionesRestController extends GenericController{
    private Repositorio<Ingreso> repoIngresos;
    private Repositorio<Egreso> repoEgresos;
    private Respuesta respuesta;
    private Gson gson;
    private String jsonRespose;

    public AsociacionOperacionesRestController(TokenService tokenService, String tokenPrefix) {
        super(tokenService,tokenPrefix);
        this.repoIngresos        = new Repositorio<>(new DaoHibernate<>(Ingreso.class));
        this.repoEgresos         = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.respuesta           = new Respuesta();
    }

    public String asociarManualmente(Request request, Response response) {
        this.gson = new Gson();
        AsociacionRequest asociacionRequest = gson.fromJson(request.body(),AsociacionRequest.class);
        Ingreso ingreso;
        Egreso egreso;

        ingreso  = this.repoIngresos.buscar(asociacionRequest.ingresoId);
        egreso   = this.repoEgresos.buscar(asociacionRequest.egresoId);

        if(egreso == null || ingreso == null) {
            this.respuesta.setCode(404);
            this.respuesta.setMessage("Uno de los recursos no existe");
            this.jsonRespose = gson.toJson(this.respuesta);
            return jsonRespose;
        }

        if(!laAsociacionEsPosible(egreso.getValorTotal(),ingreso.montoSobrante())) {
            this.respuesta.setCode(400);
            this.respuesta.setMessage("No es posible asociar el egreso al ingreso");
            this.jsonRespose = gson.toJson(this.respuesta);
            return jsonRespose;
        }
        asociarOperaciones(egreso,ingreso);
        this.respuesta.setCode(200);
        this.respuesta.setMessage("Asociación exitosa");
        this.jsonRespose = gson.toJson(this.respuesta);

        response.body(this.jsonRespose);
        return response.body();
    }

    private boolean laAsociacionEsPosible(Double montoEgreso,Double montoIngreso) {
        return montoEgreso <= montoIngreso;
    }

    private void asociarOperaciones(Egreso egreso, Ingreso ingreso) {
        egreso.setIngresoAsociado(ingreso);
        ingreso.setEgresos(egreso);
        this.repoEgresos.modificar(egreso);
        this.repoIngresos.modificar(ingreso);
    }

    private class AsociacionRequest {
        public Integer egresoId;
        public Integer ingresoId;
    }
}
