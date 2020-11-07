package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import com.google.gson.Gson;

import Domain.Controllers.MedioDePagoController.MedioDePagoRespuesta;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ClasesParciales.CategoriaDato;
import Domain.Entities.ClasesParciales.CriterioDato;
import Domain.Entities.ClasesParciales.ProveedorDato;
import Domain.Entities.ClasesParciales.ProveedorNuevo;
import Domain.Entities.DatosDeOperaciones.MedioDePago;
import Domain.Entities.Operaciones.CategoriaOperacion;
import Domain.Entities.Operaciones.CriterioOperacion;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

public class CriteriosCategoriasController {


	private Repositorio<CriterioOperacion> repoCriterio;
	private Repositorio<CategoriaOperacion> repoCategoria;
	private Repositorio<Egreso> repoEgreso;
	private Repositorio<Presupuesto> repoPresupuesto;
	
	
	public String listadoCriterios(Request request, Response response){
		
		 	Gson gson = new Gson();
	        List<CriterioOperacion> criterios=new ArrayList<>();
	        CriterioRespuesta criterioRespuesta= new CriterioRespuesta();
	   	 	this.repoCriterio = new Repositorio<CriterioOperacion>(new DaoHibernate<CriterioOperacion>(CriterioOperacion.class));
	   	 	
	        try {
		        criterios= this.repoCriterio.buscarTodos();
		        
		        List<CriterioDato> criteriosAEnviar = criterios.stream().map(this::mapCriterio).collect(Collectors.toList());
		        
		        criterioRespuesta.code = 200;
		        criterioRespuesta.message = "Criterios y categorias cargados exitosamente";
		        criterioRespuesta.criterios= criteriosAEnviar;
		        response.status(200);
	        }
	        catch (NullPointerException ex){
	            criterioRespuesta.code=404;
	            criterioRespuesta.message="No se logró cargar los criterios";
	            response.status(404);
	         }
	        
	        catch(NoResultException nf){
	        	criterioRespuesta.code=404;
	            criterioRespuesta.message="Ninguna categoria registradoa, por favor crearla";
	            response.status(404);
	        }
	       
	       
	        String jsonCriterios = gson.toJson(criterioRespuesta);
	       
	        response.type("application/json");
	        response.body(jsonCriterios);

	        return response.body();
	}

	
	public String asociarCategoriaEgreso(Request request, Response response){
		
	 	Gson gson = new Gson();
        Respuesta respuesta= new Respuesta();        
        CategoriaRequest categoriaRequest= new CategoriaRequest();
        categoriaRequest = gson.fromJson(request.body(),CategoriaRequest.class);
   	 	this.repoCategoria = new Repositorio<CategoriaOperacion>(new DaoHibernate<CategoriaOperacion>(CategoriaOperacion.class));
   	 	
   	 	
   	 	try {
	       Egreso egreso= this.repoEgreso.buscar(categoriaRequest.id);
	       
	       if(!categoriaRequest.categorias.isEmpty()) {
		       for(Integer categoriaId: categoriaRequest.categorias){
		    	  CategoriaOperacion categoria = this.repoCategoria.buscar(categoriaId);
		    	  egreso.addCategorias(categoria);
		       }
	       }
	       
	        respuesta.setCode(200);
	        respuesta.setMessage("Categorias asociadas exitosamente");
	        response.status(200);
        }
        catch (NullPointerException ex){
        	respuesta.setCode(200);
	        respuesta.setMessage("Categorias no pudiron cargarse");
            response.status(404);
         }
        
        catch(NoResultException nf){
        	respuesta.setCode(200);
	        respuesta.setMessage("Categorias no pudiron cargarse");
            response.status(404);
        }
       
       
        String jsonCategorias = gson.toJson(respuesta);
       
        response.type("application/json");
        response.body(jsonCategorias);

        return response.body();
	}
	
public String crearCategoria(Request request, Response response){
		
	 	Gson gson = new Gson();     
        CategoriaNueva categoriaRequest= new CategoriaNueva();
        categoriaRequest = gson.fromJson(request.body(),CategoriaNueva.class);
   	 	this.repoCategoria = new Repositorio<CategoriaOperacion>(new DaoHibernate<CategoriaOperacion>(CategoriaOperacion.class));
   	 	
   	 	CategoriaResponse catResponse= new CategoriaResponse();
   	 	
   	 	try {
	       CriterioOperacion criterio= this.repoCriterio.buscar(categoriaRequest.criterioId);
	       
	       CategoriaOperacion categoria = new CategoriaOperacion();
	       
	       categoria.setCriterio(criterio);
	       categoria.setDescripcion(categoriaRequest.nombre);
	       
	       repoCategoria.agregar(categoria);
	       
	        catResponse.code=200;
	        catResponse.message="Categoria creada exitosamente";
	        catResponse.categoriaId= categoria.getId();
	        response.status(200);
        }
        catch (NullPointerException ex){
        	catResponse.code=404;
	        catResponse.message="Error al crear la Categoria";
            response.status(404);
         }
        

        String jsonCategoria = gson.toJson(catResponse);
       
        response.type("application/json");
        response.body(jsonCategoria);
        
   	 return response.body();
}	

public String crearCriterio(Request request, Response response){
	
 	Gson gson = new Gson();     
    CriterioNuevo criterioRequest= new CriterioNuevo();
    criterioRequest = gson.fromJson(request.body(),CriterioNuevo.class);
	 	this.repoCriterio = new Repositorio<CriterioOperacion>(new DaoHibernate<CriterioOperacion>(CriterioOperacion.class));
	 	
	 	CriterioResponse criResponse= new CriterioResponse();
	 	
	 	try {
       CriterioOperacion criterio=mapCriterioNuevo(criterioRequest);
  
       repoCriterio.agregar(criterio);
       
        criResponse.code=200;
        criResponse.message="Criterio creado exitosamente";
        criResponse.criterioId= criterio.getId();
        response.status(200);
    }
    catch (NullPointerException ex){
    	criResponse.code=404;
        criResponse.message="Error al crear el Criterio";
        response.status(404);
     }

        String jsonCriterio = gson.toJson(criResponse);
       
        response.type("application/json");
        response.body(jsonCriterio);
	 	
	 return response.body();
}	
   		@SuppressWarnings("unchecked")
   		public CriterioDato mapCriterio(CriterioOperacion criterio){
   			
   			CriterioDato criterioDato= new CriterioDato();
   			
   			List<CategoriaOperacion> categorias= new ArrayList<>();
   			
   			categorias = EntityManagerHelper.createQuery("SELECT c FROM CategoriaOperacion c WHERE c.criterio.criterioId= :code")
   			        .setParameter("code",criterio.getId()).getResultList();
   	      

   			criterioDato.id=criterio.getId();
   			criterioDato.name= criterio.getDescripcion();
   			
   			if(criterio.getCriterioPadre()!=null)
   				criterioDato.idCriterioPadre=criterio.getCriterioPadre().getId();
   			
	   		if(!categorias.isEmpty()) {
	   			List<CategoriaDato> categoriasAEnviar = categorias.stream().map(this::mapCategoria).collect(Collectors.toList());
	   			criterioDato.categorias=categoriasAEnviar;
	   		}
   		
   			return criterioDato;
   		}
   		
   	public CategoriaDato mapCategoria(CategoriaOperacion categoria){
   		CategoriaDato categoriaDato= new CategoriaDato();
   		
   		categoriaDato.id=categoria.getId();
   		categoriaDato.name=categoria.getDescripcion();
   		return categoriaDato;
   		}
   	
		
	public CriterioOperacion mapCriterioNuevo(CriterioNuevo criterioNuevo){
		CriterioOperacion criterio= new CriterioOperacion();
		CriterioOperacion criterioPadre= new CriterioOperacion();
	
		if(criterioNuevo.criterioPadreId!= 0) {
			
			criterioPadre= repoCriterio.buscar(criterioNuevo.criterioPadreId);
			criterio.setCriterioPadre(criterioPadre);
		}
		criterio.setDescripcion(criterioNuevo.nombre);
		return criterio;
		}
   		
   	 	
	public class CategoriaRequest{
		public int id;
		public List<Integer> categorias;
	}
	
	public class CategoriaNueva{
		public int criterioId;
		public  String nombre;
	}
	
	public class CategoriaResponse{
		public int code;
		public String message;
		public int categoriaId;
	}
	
	public class CriterioRespuesta{
		public int code;
		public String message;
		public List<CriterioDato> criterios;
	}
	
	public class CriterioNuevo{
		public int criterioPadreId;
		public  String nombre;
	}
	
	public class CriterioResponse{
		public int code;
		public String message;
		public int criterioId;
	}
}
