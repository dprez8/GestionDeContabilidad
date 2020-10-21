package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.ClasesParciales.ProveedorNuevo;
import Domain.Entities.DatosDeOperaciones.Proveedor;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import spark.Request;
import spark.Response;

public class ProveedorController {
	
	private Repositorio<Proveedor> repoProveedor;
	private Repositorio<Pais> repoPais;
	private Repositorio<Provincia> repoProvincia;
	private Repositorio<Ciudad> repoCiudad;
	
	
	public String crearProveedor(Request request,Response response){
		
		Gson gson2 = new Gson();
		//Proveedor proveedor= new Proveedor();
		this.repoProveedor = new Repositorio<Proveedor>(new DaoHibernate<Proveedor>(Proveedor.class));
		ProveedorNuevo proveedorNuevo = gson2.fromJson(request.body(),ProveedorNuevo.class);
		Proveedor proveedor=mapProveedor(proveedorNuevo);
		ProveedorRespuesta p= new ProveedorRespuesta();
		try{
	
           repoProveedor.agregar(proveedor);
             p.code = 200;
             p.message = "Proveedor Agregado";
             p.id=proveedor.getProveedorId();
             response.status(200);
		}
        catch (NullPointerException ex){
        	 p.code = 404;
        	 p.message =  "No se logro crear el proveedor: "+proveedor.getProveedorId();
            response.status(404);
        }
		

        String jsonProveedor = gson2.toJson(p);
       
        response.type("application/json");
        response.body(jsonProveedor);

        return response.body();
	}
	public Proveedor mapProveedor(ProveedorNuevo proveedorNuevo) {
		this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
		this.repoProvincia = new Repositorio<Provincia>(new DaoHibernate<Provincia>(Provincia.class));
		this.repoCiudad = new Repositorio<Ciudad>(new DaoHibernate<Ciudad>(Ciudad.class));
		
		Pais paisElegido = repoPais.buscar(proveedorNuevo.paisId);
		Provincia provinciaElegida = repoProvincia.buscar(proveedorNuevo.provinciaId);
		Ciudad ciudadElegida = repoCiudad.buscar(proveedorNuevo.ciudadId);
		

		Proveedor proveedor= new Proveedor(proveedorNuevo.nombre,proveedorNuevo.documento);
		
		proveedor.setPais(paisElegido);
		proveedor.setProvincia(provinciaElegida);
		proveedor.setCiudad(ciudadElegida);
		proveedor.setCalle(proveedorNuevo.calle);
		proveedor.setAltura(proveedorNuevo.altura);
		proveedor.setPiso(proveedorNuevo.piso);
		proveedor.setZipcode(proveedorNuevo.zipcode);
		
		return proveedor;
	}
	
	public class ProveedorRespuesta{
		int code;
		String message;
		int id;
	}

}
