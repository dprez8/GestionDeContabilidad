package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import com.google.gson.Gson;

import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ClasesParciales.MedioDato;
import Domain.Entities.ClasesParciales.PaisDato;
import Domain.Entities.DatosDeOperaciones.MedioDePago;
import Domain.Entities.DatosDeOperaciones.TipoPago;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import javassist.NotFoundException;
import spark.Request;
import spark.Response;

public class MedioDePagoController {

	private Repositorio<MedioDePago> repoMedio;
	public String listadoMediosDePago(Request request, Response response){
		
		 	Gson gson = new Gson();
	        List<MedioDePago> medios=new ArrayList<>();
	        MedioDePagoRespuesta medioRespuesta= new MedioDePagoRespuesta();
	   	 	this.repoMedio = new Repositorio<MedioDePago>(new DaoHibernate<MedioDePago>(MedioDePago.class));
	      
	        try {
		        medios= this.repoMedio.buscarTodos();
		        List<MedioDato> mediosAEnviar = medios.stream().map(this::mapMedios).collect(Collectors.toList());
		       
		        medioRespuesta.code = 200;
		        medioRespuesta.message = "Medios de pago cargados exitosamente";
		        medioRespuesta.mediosDePago= mediosAEnviar;
		        response.status(200);
	        }
	        catch (NullPointerException ex){
	            medioRespuesta.code=404;
	            medioRespuesta.message="No se logró cargar los medios de pago";
	            response.status(404);
	         }
	        
	        catch(NoResultException nf){
	        	medioRespuesta.code=404;
	            medioRespuesta.message="Ningun medio de pago registrado, por favor crearlo";
	            response.status(404);
	        }
	       
	       
	        String jsonMedios = gson.toJson(medioRespuesta);
	       
	        response.type("application/json");
	        response.body(jsonMedios);

	        return response.body();
	}
	
	private Repositorio<TipoPago> repoTipoPago;
	public String listadoTiposDePago(Request request, Response response){
		
		 	Gson gson = new Gson();
	        List<TipoPago> tiposPago=new ArrayList<>();
	        TipoDePagoRespuesta tiposRespuesta= new TipoDePagoRespuesta();
	   	 	this.repoTipoPago= new Repositorio<TipoPago>(new DaoHibernate<TipoPago>(TipoPago.class));
	      
	        try {
		        tiposPago= this.repoTipoPago.buscarTodos();
		        tiposRespuesta.code = 200;
		        tiposRespuesta.message = "Tipos de pago cargados exitosamente";
		        tiposRespuesta.tiposDePago= tiposPago;
		        response.status(200);
	        }
	        catch (NullPointerException ex){
	            tiposRespuesta.code=404;
	            tiposRespuesta.message="No se logró cargar los tipos de pago";
	            response.status(404);
	         }
	        
	        catch(NoResultException nf){
	        	tiposRespuesta.code=404;
	            tiposRespuesta.message="Ningun tipo de pago registrado, por favor crearlo";
	            response.status(404);
	        }
	       
	       
	        String jsonTipos = gson.toJson(tiposRespuesta);
	       
	        response.type("application/json");
	        response.body(jsonTipos);

	        return response.body();
	}
	
	
	public MedioDato mapMedios(MedioDePago medio) { 
        MedioDato medioDato=new MedioDato();
		medioDato.id= medio.getId();
        medioDato.medioDePago = medio.getMedioDePago();
        
       return medioDato;
    }

	public class TipoDePagoRespuesta{
		public int code;
		public String message;
		public List<TipoPago> tiposDePago;
	}
	
	public class MedioDePagoRespuesta{
		public int code;
		public String message;
		public List<MedioDato> mediosDePago;
	}
}
