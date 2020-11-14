package Domain.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.ClasesParciales.ItemDato;
import Domain.Entities.DatosDeOperaciones.Item;
import Domain.Entities.DatosDeOperaciones.TipoItem;
import Domain.Entities.Usuarios.Estandar;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import spark.Request;
import spark.Response;

public class ItemsController {

private Repositorio<Item> repoItem;
private Repositorio<TipoItem> repoTipoItem;
	
	public String listadoDeItems(Request request, Response response) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
        Gson gson = new Gson();
        Respuesta respuesta=new Respuesta();
        List<Item> items;

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

   	 	this.repoItem = new Repositorio<Item>(new DaoHibernate<Item>(Item.class));
       
        VinculadorItem vinculadorItem = new VinculadorItem();
        
        
        try {
        	items= this.repoItem.buscarTodos();
        	List<ItemDato> itemsAEnviar = items.stream().map(this::mapItem).collect(Collectors.toList());
	       
	        vinculadorItem.code = 200;
	        vinculadorItem.message = "Items cargados exitosamente";
	        vinculadorItem.items=itemsAEnviar;
	        response.status(200);
        }
        catch (NullPointerException ex){
            respuesta.setCode(404);
            respuesta.setMessage("No se logró cargar los Items");
            response.status(404);
            }
       
       
        String jsonItems = gson.toJson(vinculadorItem);
       
        response.type("application/json");
        response.body(jsonItems);

        return response.body();
    }
	
	public String listadoDeTipoItems(Request request, Response response) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
        Gson gson = new Gson();
        Respuesta respuesta=new Respuesta();
        List<TipoItem> tipoItems;

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

   	 	this.repoItem = new Repositorio<Item>(new DaoHibernate<Item>(Item.class));
       
        VinculadorTipoItem vinculadorTipoItem = new VinculadorTipoItem();
        
        
        try {
        	tipoItems= this.repoTipoItem.buscarTodos();
        	
	        vinculadorTipoItem.code = 200;
	        vinculadorTipoItem.message = "TipoItems cargados exitosamente";
	        vinculadorTipoItem.tipoItems=tipoItems;
	        response.status(200);
        }
        catch (NullPointerException ex){
            respuesta.setCode(404);
            respuesta.setMessage("No se logró cargar los TipoItems");
            response.status(404);
            }
       
       
        String jsonTipoItems = gson.toJson(vinculadorTipoItem);
       
        response.type("application/json");
        response.body(jsonTipoItems);

        return response.body();
    }

	
	public ItemDato mapItem(Item item) { 
        ItemDato itemDato=new ItemDato();
		itemDato.id = item.getId();
        itemDato.descripcion = item.getDescripcion();
        itemDato.tipoItemId = item.getTipoItem().getId();
        itemDato.tipoItemNombre = item.getTipoItem().getNombre();
        
       return itemDato;
    }
	
	
	private class VinculadorItem{
		int code;
		String message;
		List<ItemDato> items= new ArrayList<>();
	}
	private class VinculadorTipoItem{
		int code;
		String message;
		List<TipoItem> tipoItems= new ArrayList<>();
	}
}
