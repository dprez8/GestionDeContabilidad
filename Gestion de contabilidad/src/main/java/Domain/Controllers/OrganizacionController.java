package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import com.google.gson.Gson;

import Domain.Controllers.ProveedorController.ProveedorRespuesta;
import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.CategorizadorDeEmpresas.Sector;
import Domain.Entities.ClasesParciales.CiudadDato;
import Domain.Entities.ClasesParciales.EntidadBaseDato;
import Domain.Entities.ClasesParciales.OrganizacionDato;
import Domain.Entities.ClasesParciales.OrganizacionPropia;
import Domain.Entities.ClasesParciales.ProveedorNuevo;
import Domain.Entities.ClasesParciales.SectorDato;
import Domain.Entities.DatosDeOperaciones.MedioDePago;
import Domain.Entities.DatosDeOperaciones.Proveedor;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Estandar;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import db.EntityManagerHelper;
import javassist.NotFoundException;
import spark.Request;
import spark.Response;

public class OrganizacionController {

	private Repositorio<Sector> repoSector;
	private Repositorio<EntidadJuridica> repoEntidad;
	private Repositorio<Pais> repoPais;
	private Repositorio<Provincia> repoProvincia;
	private Repositorio<Ciudad> repoCiudad;
	
	
	public String listadoSectores(Request request, Response response){
		
		 	Gson gson = new Gson();
	        List<Sector> listadoSectores=new ArrayList<>();
	        SectorRespuesta sectorRespuesta= new SectorRespuesta();
			Administrador usuario = (Administrador) PermisosRestController.verificarSesion(request,response);
			if(usuario == null) {
				return response.body();
			}
	   	 	this.repoSector = new Repositorio<Sector>(new DaoHibernate<Sector>(Sector.class));
	      
	        try {
		        listadoSectores= this.repoSector.buscarTodos();
		        sectorRespuesta.code = 200;
		        sectorRespuesta.message = "Sectores cargados exitosamente";
		        
		        List<SectorDato> sectoresAEnviar = listadoSectores.stream().map(this::mapSector).collect(Collectors.toList());      
		        sectorRespuesta.sectores= sectoresAEnviar;
		        response.status(200);
	        }
	        catch (NullPointerException ex){
	            sectorRespuesta.code=404;
	            sectorRespuesta.message="No se logró cargar los sectores";
	            response.status(404);
	         }
	        
	        catch(NoResultException nf){
	        	sectorRespuesta.code=404;
	            sectorRespuesta.message="Ningun sector registrado, por favor crearlo";
	            response.status(404);
	        }
	       
	       
	        String jsonSectores= gson.toJson(sectorRespuesta);
	       
	        response.type("application/json");
	        response.body(jsonSectores);

	        return response.body();
	}
	
	public String crearOrganizacion(Request request,Response response){
		
		Gson gson2 = new Gson();
	
		Administrador usuario = (Administrador) PermisosRestController.verificarSesion(request,response);
		if(usuario == null) {
			return response.body();
		}
		this.repoEntidad= new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
		OrganizacionDato organizacionDato = gson2.fromJson(request.body(),OrganizacionDato.class);
		EntidadJuridica entidad=mapOrganizacion(organizacionDato);
		OrganizacionRespuesta organizacionCreada= new OrganizacionRespuesta();
		try{
			 repoEntidad.agregar(entidad);
             organizacionCreada.code = 200;
             organizacionCreada.message = "Organizacion Creada";
             organizacionCreada.organizacionId=entidad.getId();
		}
        catch (NullPointerException ex){
        	 organizacionCreada.code =  400;
        	 organizacionCreada.message =  "No se logro crear la organizacion";
        }
		

        String jsonOrganizacion = gson2.toJson(organizacionCreada);
    	response.type("application/json");
        response.body(jsonOrganizacion);

        return response.body();
	}
	
//para mostrar la lista de organizaciones pertenecientes a un usuario
	@SuppressWarnings("unchecked")
	public String listarOrganizacionesPropias(Request request,Response response){
	
		List<EntidadBase> entidadesBase= new ArrayList<>();
		
		Gson gson2 = new Gson();
	
		Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
		if(usuario == null) {
			return response.body();
		}
		OrganizacionPropia organizacionPropia= new OrganizacionPropia();

		EntidadJuridica entidadJuridica=usuario.getMiOrganizacion();
		
		organizacionPropia.juridica_id=entidadJuridica.getId();
		organizacionPropia.juridica_name=entidadJuridica.getNombreFicticio();
	
	
		entidadesBase = EntityManagerHelper.createQuery("SELECT c FROM EntidadBase c WHERE c.entidadJuridica.id= :code")
								.setParameter("code",entidadJuridica.getId()).getResultList();
	 
		if(!entidadesBase.isEmpty()) {
			List<EntidadBaseDato> basesAEnviar = entidadesBase.stream().map(this::mapBases).collect(Collectors.toList());
			organizacionPropia.bases=basesAEnviar;
		}

        String jsonOrganizacion = gson2.toJson(organizacionPropia);
    	response.type("application/json");
        response.body(jsonOrganizacion);

        return response.body();
	}
	

	public EntidadBaseDato mapBases(EntidadBase entidadBase){
		EntidadBaseDato base= new EntidadBaseDato();
		base.id=entidadBase.getId();
		base.name=entidadBase.getNombreFicticio();
		
		return base;
	}
	
	public EntidadJuridica mapOrganizacion(OrganizacionDato organizacionDato){
		EntidadJuridica entidad= new EntidadJuridica();
        
		entidad.setRazonSocial(organizacionDato.razonSocial);
        entidad.setCuit(organizacionDato.cuit);
        entidad.setNombreFicticio(organizacionDato.nombreFicticio);
        entidad.setCodigoDeInscripcionDefinitivaEnIGJ(organizacionDato.codigoDeInscripcionDefinitivaEnIGJ);
    	
        this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
		this.repoProvincia = new Repositorio<Provincia>(new DaoHibernate<Provincia>(Provincia.class));
		this.repoCiudad = new Repositorio<Ciudad>(new DaoHibernate<Ciudad>(Ciudad.class));
		
		Pais paisElegido = repoPais.buscar(organizacionDato.pais);
		Provincia provinciaElegida = repoProvincia.buscar(organizacionDato.provincia);
		
		if(organizacionDato.ciudad!=0) {
			Ciudad ciudadElegida = repoCiudad.buscar(organizacionDato.ciudad);
			entidad.setCiudad(ciudadElegida);
		}	
		entidad.setPais(paisElegido);
		entidad.setProvincia(provinciaElegida);
		entidad.setCalle(organizacionDato.calle);
		entidad.setAltura(organizacionDato.altura);
		entidad.setPiso(organizacionDato.piso);
		entidad.setZipcode(organizacionDato.zipcode);
        
        return entidad;
	}
	
	public SectorDato mapSector(Sector sector){
		SectorDato sectorDato = new SectorDato();
		
		sectorDato.nombre=sector.getNombre();
		sectorDato.sectorId= sector.getSectorId();
		
		return sectorDato;
	}
	
	public class SectorRespuesta{
		public int code;
		public String message;
		public List<SectorDato> sectores;
	}
	
	public class OrganizacionRespuesta{
		public int code;
		public String message;
		public int organizacionId;
	}

}
