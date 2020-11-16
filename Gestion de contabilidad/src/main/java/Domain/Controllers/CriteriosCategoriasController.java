package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import com.google.gson.Gson;


import Domain.Controllers.DTO.Respuesta;

import Domain.Entities.ClasesParciales.CategoriaDato;
import Domain.Entities.ClasesParciales.CriterioDato;
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
	        List<CriterioOperacion> criterios;
	        CriterioRespuesta criterioRespuesta= new CriterioRespuesta();
	   	 	this.repoCriterio = new Repositorio<>(new DaoHibernate<>(CriterioOperacion.class));
	   	 	
	        try {
		        criterios= this.repoCriterio.buscarTodos();
		        
		        List<CriterioDato> criteriosAEnviar = criterios.stream().map(this::mapCriterio).collect(Collectors.toList());
		        
		        criterioRespuesta.code = 200;
		        criterioRespuesta.message = "Criterios y categorias cargados exitosamente";
		        criterioRespuesta.criterios= criteriosAEnviar;
	        }
	        catch (NullPointerException ex){
	            criterioRespuesta.code=404;
	            criterioRespuesta.message="No se logró cargar los criterios";
	         }
	        
	        catch(NoResultException nf){
	        	criterioRespuesta.code=404;
	            criterioRespuesta.message="Ninguna categoria registradoa, por favor crearla";
	        }
	       
	       
	        String jsonCriterios = gson.toJson(criterioRespuesta);

	        response.body(jsonCriterios);

	        return response.body();
	}

	public String crearCriterio(Request request, Response response){
	
 		Gson gson = new Gson();
    	CriterioNuevo criterioRequest;
    	criterioRequest = gson.fromJson(request.body(),CriterioNuevo.class);
	 	this.repoCriterio = new Repositorio<>(new DaoHibernate<>(CriterioOperacion.class));
	 	
	 	CriterioResponse criResponse= new CriterioResponse();
	 	
	 	try {
       		CriterioOperacion criterio=mapCriterioNuevo(criterioRequest);
  
      		repoCriterio.agregar(criterio);
       
        	criResponse.code=200;
        	criResponse.message="Criterio creado exitosamente";
        	criResponse.criterioId= criterio.getId();
    	}
    	catch (Exception ex) {
    		criResponse.code=404;
    	    criResponse.message="Error al crear el Criterio";
     	}

        String jsonCriterio = gson.toJson(criResponse);
        response.body(jsonCriterio);
	 	
	 	return response.body();
	}

	@SuppressWarnings("unchecked")
	public CriterioDato mapCriterio(CriterioOperacion criterio){
   			
   			CriterioDato criterioDato= new CriterioDato();
   			
   			List<CategoriaOperacion> categorias= new ArrayList<>();
   			
   			categorias = EntityManagerHelper.createQuery("SELECT c FROM CategoriaOperacion c WHERE c.criterio.id= :code")
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
		CriterioOperacion criterioPadre;
	
		if(criterioNuevo.criterioPadreId!= 0) {
			criterioPadre= repoCriterio.buscar(criterioNuevo.criterioPadreId);
			criterio.setCriterioPadre(criterioPadre);
		}
		criterio.setDescripcion(criterioNuevo.nombre);
		return criterio;
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
