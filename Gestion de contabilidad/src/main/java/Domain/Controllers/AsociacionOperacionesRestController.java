package Domain.Controllers;

import Domain.Controllers.DTO.Respuesta;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.ApiVinculador.ConfiguracionVinculador;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Estandar;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import Services.ProcesoVinculacionServices.VinculacionApi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class AsociacionOperacionesRestController extends GenericController{
    private Repositorio<Ingreso> repoIngresos;
    private Repositorio<Egreso> repoEgresos;
    private Repositorio<Organizacion> repoOrganizaciones;
    private Respuesta respuesta;
    private Gson gson;
    private String jsonRespose;

    public AsociacionOperacionesRestController(TokenService tokenService, String tokenPrefix) {
        super(tokenService,tokenPrefix);
        this.repoIngresos        = new Repositorio<>(new DaoHibernate<>(Ingreso.class));
        this.repoEgresos         = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoOrganizaciones  = new Repositorio<>(new DaoHibernate<>(Organizacion.class));
        this.respuesta           = new Respuesta();
    }

    public String asociarManualmente(Request request, Response response) {
        this.gson = new Gson();
        AsociacionRequest asociacionRequest=null;
        try {
        asociacionRequest = gson.fromJson(request.body(),AsociacionRequest.class);
        }
        catch(Exception ex){
        	 this.respuesta.setCode(404);
             this.respuesta.setMessage("No se logro mapear el json");
             this.jsonRespose = gson.toJson(this.respuesta);
             return jsonRespose;
        }
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

    public String asociarAutomaticamente(Request request, Response response) {
        this.gson = new Gson();
        this.respuesta = new Respuesta();
        Estandar estandar = (Estandar) getUsuarioDesdeRequest(request);

        List<Egreso> egresos = estandar.getMiOrganizacion().getEgresos();
        List<Ingreso> ingresos = estandar.getMiOrganizacion().getIngresos();
        if(egresos.isEmpty() || ingresos.isEmpty()) {
            return respuesta(response,404,"No hay operaciones para vincular");
        }
        if(periodoDeAceptabilidadNulo(ingresos))
            return error(response,"Faltan ingresos con periodo de aceptabilidad");

        try {
            //LLAMO AL ARCHIVO DE CONFIG PARA OBTENER LA URL A LA API DE VINCULADOR
            Properties prop=new Properties();
            prop.load(new FileReader("src/main/resources/config/config.properties"));

            VinculacionApi vinculacionApi = new VinculacionApi();

            ConfiguracionVinculador configuracionVinculador = estandar.getMiOrganizacion().getConfiguracionVinculador();
            if(configuracionVinculador.getCriterios().size() == 1){
                vinculacionApi.configurarVinculador(configuracionVinculador.getCriterios().get(0), prop);
            }
            else {
                vinculacionApi.configurarVinculador(configuracionVinculador.getCriterios(),prop);
            }
            vinculacionApi.vincularEgresosIngresos(ingresos, egresos, prop);
            printTest(ingresos,egresos);
            modificarEnBDOperaciones(egresos,ingresos);
            return respuesta(response,200,"Las operaciones se vincularon con exito");
        }catch (Exception ex) {
            return error(response,"Error al abrir el archivo");
        }
    }

    public String configurarVinculador(Request request, Response response) {
        this.gson = new Gson();
        try {
            CriteriosRequest criteriosRequest = this.gson.fromJson(request.body(),CriteriosRequest.class);
            Organizacion organizacion = this.repoOrganizaciones.buscar(criteriosRequest.organizacionId);
            ConfiguracionVinculador configuracionVinculador = organizacion.getConfiguracionVinculador();
            configuracionVinculador.setCriterios(criteriosRequest.criterios);
            this.repoOrganizaciones.modificar(organizacion);
        }catch (Exception ex) {
            return error(response,"Error en los datos recibidos");
        }
        return respuesta(response,200,"Las configuraciones se guardaron con exito");
    }

    /********* Private methods ************************************/

    private void modificarEnBDOperaciones(List<Egreso> egresos, List<Ingreso> ingresos) {
        egresos.forEach(egreso -> this.repoEgresos.modificar(egreso));
        ingresos.forEach(ingreso -> this.repoIngresos.modificar(ingreso));
    }

    private static void printTest(List<Ingreso> ingresos, List<Egreso> egresos) {
        egresos.forEach(unEgreso -> {
            Integer numeroEgreso = unEgreso.getId();
            String fechaEgreso = unEgreso.getFechaOperacion().toString();
            Double valorEgreso = unEgreso.getValorTotal();

            if(unEgreso.getIngresoAsociado() != null) {
                Integer numeroIngreso = unEgreso.getIngresoAsociado().getId();
                String fechaIngreso = unEgreso.getIngresoAsociado().getFechaOperacion().toString();
                Double valorIngreso = unEgreso.getIngresoAsociado().montoSobrante() +
                        unEgreso.getIngresoAsociado().montoEnUso();

                System.out.println(
                        "Egreso N°"+numeroEgreso+":\n"+
                                "   Fecha: "+fechaEgreso+"\n"+
                                "   Monto: $"+valorEgreso+"\n"+
                                "   Ingreso asociado: -> {N°"+numeroIngreso+" | "+fechaIngreso+" | $"+valorIngreso+"}"
                );
            } else {
                System.out.println(
                        "Egreso N°"+numeroEgreso+":\n"+
                                "   Fecha: "+fechaEgreso+"\n"+
                                "   Monto: $"+valorEgreso+"\n"+
                                "   Ingreso asociado: -> {null}"
                );
            }
        });
    }

    private boolean periodoDeAceptabilidadNulo(List<Ingreso> ingresos) {
        return ingresos.stream().anyMatch(ingreso -> ingreso.getFechaAceptacionEgreso() == null);
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

    private class CriteriosRequest {
        public List<String> criterios;
        public Integer organizacionId;
    }
}
