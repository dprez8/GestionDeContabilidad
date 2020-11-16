package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Controllers.jwt.TokenService;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Estandar;
import com.google.gson.Gson;

import Domain.Controllers.DTO.Respuesta;

import Domain.Entities.DatosDeOperaciones.Item;
import Domain.Entities.DatosDeOperaciones.TipoItem;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import spark.Request;
import spark.Response;

public class ItemsController extends GenericController {

    private Repositorio<Item> repoItem;
    private Repositorio<TipoItem> repoTipoItem;

    public ItemsController(TokenService tokenService, String tokenPrefix) {
        super(tokenService, tokenPrefix);
    }

	public String listadoDeItems(Request request, Response response) {
        Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .serializeNulls()
                        .create();

        Respuesta respuesta=new Respuesta();
        List<Item> items;

   	 	this.repoItem = new Repositorio<>(new DaoHibernate<>(Item.class));

        VinculadorItem vinculadorItem = new VinculadorItem();

        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
        Organizacion organizacion = usuario.getMiOrganizacion();
        try {
        	items = this.repoItem.buscarTodos();
        	items = items.stream().filter(unItem -> unItem.getOrganizacion().equals(organizacion)).collect(Collectors.toList());

	        vinculadorItem.code    = 200;
	        vinculadorItem.message = "Items cargados exitosamente";
	        vinculadorItem.items   = items;
        }
        catch (Exception ex){
            respuesta.setCode(404);
            respuesta.setMessage("No se logró cargar los Items");
        }

        String jsonItems = gson.toJson(vinculadorItem);
        response.body(jsonItems);
        return response.body();
    }
	
	public String listadoDeTipoItems(Request request, Response response) {
        Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .serializeNulls()
                        .create();
        Respuesta respuesta=new Respuesta();
        List<TipoItem> tipoItems;

   	 	this.repoTipoItem = new Repositorio<>(new DaoHibernate<>(TipoItem.class));
       
        VinculadorTipoItem vinculadorTipoItem = new VinculadorTipoItem();

        try {
        	tipoItems= this.repoTipoItem.buscarTodos();
        	
	        vinculadorTipoItem.code = 200;
	        vinculadorTipoItem.message = "TipoItems cargados exitosamente";
	        vinculadorTipoItem.tipoItems=tipoItems;
        }
        catch (Exception ex){
            respuesta.setCode(404);
            respuesta.setMessage("No se logró cargar los TipoItems");
        }
        String jsonTipoItems = gson.toJson(vinculadorTipoItem);
        response.body(jsonTipoItems);

        return response.body();
    }
	
	private class VinculadorItem{
	    @Expose
		public int code;
	    @Expose
		public String message;
	    @Expose
		public List<Item> items= new ArrayList<>();
	}
	private class VinculadorTipoItem{
        @Expose
        public int code;
        @Expose
        public String message;
        @Expose
        public List<TipoItem> tipoItems= new ArrayList<>();
	}
}
