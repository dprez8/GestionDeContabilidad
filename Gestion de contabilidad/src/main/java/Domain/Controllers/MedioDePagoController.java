package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.google.gson.Gson;

import Domain.Entities.DatosDeOperaciones.MedioDePago;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;

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
		        medioRespuesta.code = 200;
		        medioRespuesta.message = "Medios de pago cargados exitosamente";
		        medioRespuesta.data= medios;
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

	        response.body(jsonMedios);

	        return response.body();
	}

	public class MedioDePagoRespuesta{
		public int code;
		public String message;
		public List<MedioDePago> data;
	}
}
