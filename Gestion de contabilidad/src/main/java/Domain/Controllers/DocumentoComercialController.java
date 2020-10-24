package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.google.gson.Gson;

import Domain.Controllers.MedioDePagoController.MedioDePagoRespuesta;
import Domain.Entities.DatosDeOperaciones.MedioDePago;
import Domain.Entities.DatosDeOperaciones.TipoDocumento;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import spark.Request;
import spark.Response;

public class DocumentoComercialController {

	private Repositorio<TipoDocumento> repoTipo;
	public String listadoTiposDocumento(Request request, Response response){
		
		 	Gson gson = new Gson();
	        List<TipoDocumento> tiposDocumento=new ArrayList<>();
	        TipoDocumentoRespuesta tipoRespuesta = new TipoDocumentoRespuesta();
	   	 	this.repoTipo = new Repositorio<TipoDocumento>(new DaoHibernate<TipoDocumento>(TipoDocumento.class));
	      
	        try {
		        tiposDocumento= this.repoTipo.buscarTodos();
		        tipoRespuesta.code = 200;
		        tipoRespuesta.message = "Tipos de documento cargados exitosamente";
		        tipoRespuesta.tiposDocumento= tiposDocumento;
		        response.status(200);
	        }
	        catch (NullPointerException ex){
	        	tipoRespuesta  .code=404;
	            tipoRespuesta.message="No se logró cargar los tipos de documento";
	            response.status(404);
	         }
	        
	        catch(NoResultException nf){
	        	tipoRespuesta.code=404;
	            tipoRespuesta.message="Ningun tipo de documento registrado, por favor crearlo";
	            response.status(404);
	        }
	       
	       
	        String jsonTiposDocumento = gson.toJson(tipoRespuesta);
	       
	        response.type("application/json");
	        response.body(jsonTiposDocumento);

	        return response.body();
	}
	
	public class TipoDocumentoRespuesta{
		public int code;
		public String message;
		public List<TipoDocumento> tiposDocumento;
	}
}
