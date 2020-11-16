package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import Domain.Controllers.jwt.TokenService;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Estandar;
import com.google.gson.Gson;

import Domain.Controllers.MedioDePagoController.MedioDePagoRespuesta;
import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.ClasesParciales.CiudadDato;
import Domain.Entities.ClasesParciales.PaisDato;
import Domain.Entities.ClasesParciales.ProveedorDato;
import Domain.Entities.ClasesParciales.ProveedorNuevo;
import Domain.Entities.DatosDeOperaciones.MedioDePago;
import Domain.Entities.DatosDeOperaciones.Proveedor;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import spark.Request;
import spark.Response;

public class ProveedorController extends GenericController {
	
	private Repositorio<Proveedor> repoProveedor;
	private Repositorio<Pais> repoPais;
	private Repositorio<Provincia> repoProvincia;
	private Repositorio<Ciudad> repoCiudad;

	public ProveedorController(TokenService tokenService, String tokenPrefix) {
		super(tokenService, tokenPrefix);
	}
	
	public String listadoProveedores(Request request, Response response){

		 	Gson gson = new Gson();
	        List<Proveedor> todosLosProveedores = new ArrayList<>();
			List<Proveedor> proveedores = new ArrayList<>();
	        ProveedorResponse proveedorRespuesta= new ProveedorResponse();

	   	 	this.repoProveedor = new Repositorio<Proveedor>(new DaoHibernate<Proveedor>(Proveedor.class));

			Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
			Organizacion organizacion = usuario.getMiOrganizacion();
	        try {
		        todosLosProveedores = this.repoProveedor.buscarTodos();
				proveedores = todosLosProveedores.stream().filter(proveedor -> proveedor.getOrganizacion().equals(organizacion)).collect(Collectors.toList());

		        List<ProveedorDato> proveedoresAEnviar = proveedores.stream().map(this::mapProveedor).collect(Collectors.toList());
		        proveedorRespuesta.code = 200;
		        proveedorRespuesta.message = "Proveedorescargados exitosamente";
		        proveedorRespuesta.data = proveedoresAEnviar;
		        response.status(200);
	        }
	        catch (NullPointerException ex){
	        	proveedorRespuesta.code=404;
	        	proveedorRespuesta.message="No se logró cargar los proveedores";
	            response.status(404);
	         }
	        
	        catch(NoResultException nf){
	        	proveedorRespuesta.code=404;
	        	proveedorRespuesta.message="Ningun proveedor registrado, por favor crearlo";
	            response.status(404);
	        }
	       
	       
	        String jsonProveedores = gson.toJson(proveedorRespuesta);

	        response.body(jsonProveedores);

	        return response.body();
	}

	public class ProveedorResponse{
		public int code;
		public String message;
		public List<ProveedorDato> data;
	}
	
	public ProveedorDato mapProveedor(Proveedor proveedor) { 
        ProveedorDato proveedorDato=new ProveedorDato();
		proveedorDato.id = proveedor.getProveedorId();
        proveedorDato.nombre = proveedor.getNombre();
        
       return proveedorDato;
    }
	
	
	public String crearProveedor(Request request,Response response){
		
		Gson gson2 = new Gson();
		//Proveedor proveedor= new Proveedor();

		this.repoProveedor = new Repositorio<Proveedor>(new DaoHibernate<Proveedor>(Proveedor.class));
		ProveedorNuevo proveedorNuevo = gson2.fromJson(request.body(),ProveedorNuevo.class);
		Proveedor proveedor = mapProveedor(request, proveedorNuevo);
		ProveedorRespuesta proveedorCreado= new ProveedorRespuesta();
		try{
	
           repoProveedor.agregar(proveedor);
             proveedorCreado.code = 200;
             proveedorCreado.message = "Proveedor Agregado";
             proveedorCreado.id=proveedor.getProveedorId();
		}
        catch (NullPointerException ex){
        	 proveedorCreado.code =  400;
        	 proveedorCreado.message =  "No se logro crear el proveedor: "+proveedor.getProveedorId();
        }
		

        String jsonProveedor = gson2.toJson(proveedorCreado);
		response.body(jsonProveedor);

        return response.body();
	}
	public Proveedor mapProveedor(Request request, ProveedorNuevo proveedorNuevo) {
		this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
		this.repoProvincia = new Repositorio<Provincia>(new DaoHibernate<Provincia>(Provincia.class));
		this.repoCiudad = new Repositorio<Ciudad>(new DaoHibernate<Ciudad>(Ciudad.class));
		
		Pais paisElegido = repoPais.buscar(proveedorNuevo.pais);
		Provincia provinciaElegida = repoProvincia.buscar(proveedorNuevo.provincia);
		Ciudad ciudadElegida = repoCiudad.buscar(proveedorNuevo.ciudad);

		Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
		Organizacion organizacion = usuario.getMiOrganizacion();
		Proveedor proveedor = new Proveedor(proveedorNuevo.nombre, proveedorNuevo.documento, organizacion);
		
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
