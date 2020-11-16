package Domain.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import Domain.Controllers.jwt.TokenService;
import com.google.gson.Gson;

import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.CategorizadorDeEmpresas.Sector;
import Domain.Entities.ClasesParciales.EntidadBaseDato;
import Domain.Entities.ClasesParciales.EntidadBaseNueva;
import Domain.Entities.ClasesParciales.EntidadJuridicaDato;
import Domain.Entities.ClasesParciales.EntidadJuridicaNueva;
import Domain.Entities.ClasesParciales.OrganizacionPropia;
import Domain.Entities.ClasesParciales.SectorDato;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Estandar;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

public class OrganizacionController extends GenericController{

	private Repositorio<Sector> repoSector;
	private Repositorio<EntidadJuridica> repoEntidad;
	private Repositorio<EntidadBase> repoEntidadBase;
	private Repositorio<Pais> repoPais;
	private Repositorio<Provincia> repoProvincia;
	private Repositorio<Ciudad> repoCiudad;

	public OrganizacionController(TokenService tokenService, String tokenPrefix) {
		super(tokenService,tokenPrefix);
		this.repoSector = new Repositorio<Sector>(new DaoHibernate<Sector>(Sector.class));
		this.repoEntidad = new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
		this.repoEntidadBase= new Repositorio<EntidadBase>(new DaoHibernate<EntidadBase>(EntidadBase.class));
		this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
		this.repoProvincia = new Repositorio<Provincia>(new DaoHibernate<Provincia>(Provincia.class));
		this.repoCiudad = new Repositorio<Ciudad>(new DaoHibernate<Ciudad>(Ciudad.class));
	}

	public String listadoSectores(Request request, Response response){
		 	Gson gson = new Gson();
	        List<Sector> listadoSectores;
	        SectorRespuesta sectorRespuesta= new SectorRespuesta();
	   	 	
	        try {
		        listadoSectores= this.repoSector.buscarTodos();
		        sectorRespuesta.code = 200;
		        sectorRespuesta.message = "Sectores cargados exitosamente";
		        
		        List<SectorDato> sectoresAEnviar = listadoSectores.stream().map(this::mapSector).collect(Collectors.toList());      
		        sectorRespuesta.sectores= sectoresAEnviar;
	        }
	        catch (NullPointerException ex){
	            sectorRespuesta.code=404;
	            sectorRespuesta.message="No se logró cargar los sectores";
	         }
	        
	        catch(NoResultException nf){
	        	sectorRespuesta.code=404;
	            sectorRespuesta.message="Ningun sector registrado, por favor crearlo";
	        }
	       
	       
	        String jsonSectores= gson.toJson(sectorRespuesta);
	       
	        response.body(jsonSectores);

	        return response.body();
	}
	
	public String listadoJuridicas(Request request, Response response){
	 	Gson gson = new Gson();
        List<EntidadJuridica> listadoJuridicas;
        JuridicaRespuesta juridicaRespuesta= new JuridicaRespuesta();
        try {
	        listadoJuridicas= this.repoEntidad.buscarTodos();
	        juridicaRespuesta.code = 200;
	        juridicaRespuesta.message = "Entidades juridicas cargadas exitosamente";
	        
	        List<EntidadJuridicaDato> juridicasAEnviar = listadoJuridicas.stream().map(this::mapJuridica).collect(Collectors.toList());      
	        juridicaRespuesta.juridicas= juridicasAEnviar;
        }
        catch (NullPointerException ex){
        	juridicaRespuesta.code=404;
        	juridicaRespuesta.message="No se logró cargar las entidades";
         }
        
        catch(NoResultException nf){
        	juridicaRespuesta.code=404;
        	juridicaRespuesta.message="Ninguna entidad registrada, por favor crearla";
        }
       
       
        String jsonJuridicas= gson.toJson(juridicaRespuesta);
       
        response.body(jsonJuridicas);

        return response.body();
}

	public String crearEntidadBase(Request request,Response response){
		Gson gson2 = new Gson();

		EntidadBaseNueva entidadDato = gson2.fromJson(request.body(),EntidadBaseNueva.class);
		EntidadBase entidad;
		OrganizacionRespuesta organizacionCreada= new OrganizacionRespuesta();
		try{
			 entidad=mapEntidadBase(entidadDato);
			 repoEntidadBase.agregar(entidad);
             organizacionCreada.code = 200;
             organizacionCreada.message = "Entidad Base Creada";
             organizacionCreada.organizacionId=entidad.getId();
		}
        catch (NullPointerException ex){
        	 organizacionCreada.code =  400;
        	 organizacionCreada.message =  "No se logro crear la entidad base";
        }


        String jsonOrganizacion = gson2.toJson(organizacionCreada);

        response.body(jsonOrganizacion);

        return response.body();
	}
	
	public String crearEntidadJuridica(Request request,Response response){
		
		Gson gson2 = new Gson();

		this.repoEntidad= new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
		EntidadJuridicaNueva organizacionDato = gson2.fromJson(request.body(),EntidadJuridicaNueva.class);
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
        response.body(jsonOrganizacion);

        return response.body();
	}
	
//para mostrar la lista de organizaciones pertenecientes a un usuario
	@SuppressWarnings("unchecked")
	public String listarOrganizacionesPropias(Request request,Response response){
	
		List<EntidadBase> entidadesBase= new ArrayList<>();
		
		Gson gson2 = new Gson();

		OrganizacionPropia organizacionPropia= new OrganizacionPropia();
		Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
		EntidadJuridica entidadJuridica= (EntidadJuridica) usuario.getMiOrganizacion();
		
		organizacionPropia.juridica_id=entidadJuridica.getId();
		organizacionPropia.juridica_name=entidadJuridica.getNombre();
	
	
		entidadesBase = EntityManagerHelper.createQuery("SELECT c FROM EntidadBase c WHERE c.entidadJuridica.id= :code")
								.setParameter("code",entidadJuridica.getId()).getResultList();
	 
		if(!entidadesBase.isEmpty()) {
			List<EntidadBaseDato> basesAEnviar = entidadesBase.stream().map(this::mapBases).collect(Collectors.toList());
			organizacionPropia.bases=basesAEnviar;
		}

        String jsonOrganizacion = gson2.toJson(organizacionPropia);
        response.body(jsonOrganizacion);

        return response.body();
	}
	

	public EntidadBaseDato mapBases(EntidadBase entidadBase){
		EntidadBaseDato base= new EntidadBaseDato();
		base.id=entidadBase.getId();
		base.name=entidadBase.getNombre();
		
		return base;
	}
	
	public EntidadJuridicaDato mapJuridica(EntidadJuridica entidadJuridica){
		EntidadJuridicaDato juridica= new EntidadJuridicaDato();
		juridica.juridicaId=entidadJuridica.getId();
		juridica.nombreFicticio=entidadJuridica.getNombre();
		
		return juridica;
	}
	
	public EntidadBase mapEntidadBase(EntidadBaseNueva entidadBase){
		EntidadBase base= new EntidadBase();
		base.setNombre(entidadBase.nombre);
		EntidadJuridica juridica=repoEntidad.buscar(entidadBase.entidadJuridica);
		base.setEntidadJuridica(juridica);
		
		return base;
	}
	
	public EntidadJuridica mapOrganizacion(EntidadJuridicaNueva organizacionDato){
		EntidadJuridica entidad= new EntidadJuridica();
        
		entidad.setRazonSocial(organizacionDato.razonSocial);
        entidad.setCuit(organizacionDato.cuit);
        entidad.setNombre(organizacionDato.nombreFicticio);
        entidad.setCodigoDeInscripcionDefinitivaEnIGJ(organizacionDato.codigoDeInscripcionDefinitivaEnIGJ);
    
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
	
	public class JuridicaRespuesta{
		public int code;
		public String message;
		public List<EntidadJuridicaDato> juridicas;
	}
	
	
	public class OrganizacionRespuesta{
		public int code;
		public String message;
		public int organizacionId;
	}

}
