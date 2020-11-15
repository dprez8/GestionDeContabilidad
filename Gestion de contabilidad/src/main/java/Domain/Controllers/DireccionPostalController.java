package Domain.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.Usuarios.Estandar;
import com.google.gson.Gson;

import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.Moneda;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.ClasesParciales.CiudadDato;
import Domain.Entities.ClasesParciales.MonedaDato;
import Domain.Entities.ClasesParciales.PaisDato;
import Domain.Entities.ClasesParciales.ProvinciaDato;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

public class DireccionPostalController {

	private Repositorio<Pais> repoPais;
	private Repositorio<Provincia> repoProvincia;
	private Repositorio<Moneda> repoMonedas;
	
	public String listadoDePaises(Request request, Response response) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
        Gson gson = new Gson();
        Respuesta respuesta=new Respuesta();
        List<Pais> paises;

   	 	this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
       
        VinculadorPais vinculadorPais = new VinculadorPais();
        
        
        try {
	        
	        paises= this.repoPais.buscarTodos();
	        List<PaisDato> paisesAEnviar = paises.stream().map(this::mapPais).collect(Collectors.toList());
	       
	        
	        vinculadorPais.code = 200;
	        vinculadorPais.message = "Paises cargados exitosamente";
	        vinculadorPais.data=paisesAEnviar;
	        response.status(200);
        }
        catch (NullPointerException ex){
            respuesta.setCode(404);
            respuesta.setMessage("No se logró cargar los paises");
            response.status(404);
            }
       
       
        String jsonPaises = gson.toJson(vinculadorPais);

        response.body(jsonPaises);

        return response.body();
    }
	@SuppressWarnings("unchecked")
	public String listadoDeProvincias(Request request, Response response){
		Gson gson2 = new Gson();
		Pais paisBuscado;
		this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
		
		VinculadorProvincia vinculadorProvincia = new VinculadorProvincia();
		
		try{
			
			paisBuscado = repoPais.buscar(new Integer(request.params("clavePais")));
			List<Provincia> provincias;
			provincias = EntityManagerHelper.createQuery("SELECT c FROM Provincia c WHERE c.pais.clave= :code")
			        .setParameter("code",paisBuscado.getClave()).getResultList();
	      
             List<ProvinciaDato> provinciasAEnviar = provincias.stream().map(this::mapProvincia).collect(Collectors.toList());
      

             vinculadorProvincia.code = 200;
             vinculadorProvincia.message = "Provincias cargadas";
             vinculadorProvincia.data=provinciasAEnviar;
             response.status(200);
		}
        catch (NullPointerException ex){
        	 vinculadorProvincia.code = 404;
        	 vinculadorProvincia.message =  "No se logro cargarlas provincias";
            response.status(404);
        }
		

        String jsonProvincias = gson2.toJson(vinculadorProvincia);
       
        response.type("application/json");
        response.body(jsonProvincias);

        return response.body();
	}

	@SuppressWarnings("unchecked")
	public String listadoDeCiudades(Request request, Response response){
		Gson gson2 = new Gson();
		Provincia provinciaBuscada= new Provincia();
		this.repoProvincia = new Repositorio<Provincia>(new DaoHibernate<Provincia>(Provincia.class));
		
		VinculadorCiudad vinculadorCiudad = new VinculadorCiudad();
		
		try{
			
			provinciaBuscada = repoProvincia.buscar(new Integer(request.params("claveProvincia")));
			List<Ciudad> ciudades= new ArrayList<>();
			ciudades = EntityManagerHelper.createQuery("SELECT c FROM Ciudad c WHERE c.provincia.clave= :code")
			        .setParameter("code",provinciaBuscada.getClave()).getResultList();
	      
             List<CiudadDato> ciudadesAEnviar = ciudades.stream().map(this::mapCiudad).collect(Collectors.toList());
            
             vinculadorCiudad.code = 200;
             vinculadorCiudad.message = "Ciudades cargadas";
             vinculadorCiudad.data=ciudadesAEnviar;
             response.status(200);
		}
        catch (NullPointerException ex){
        	 vinculadorCiudad.code = 404;
        	 vinculadorCiudad.message =  "No se logro cargarlas ciudades";
            response.status(404);
        }
		

        String jsonCiudades = gson2.toJson(vinculadorCiudad);
		response.body(jsonCiudades);

        return response.body();
	}
	
	public String listadoDeMonedas(Request request, Response response) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
        Gson gson = new Gson();
        Respuesta respuesta=new Respuesta();
        List<Moneda> monedas;
        this.repoMonedas = new Repositorio<Moneda>(new DaoHibernate<Moneda>(Moneda.class));
        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

      
        VinculadorMoneda vinculadorMoneda = new VinculadorMoneda();
        
        
        try {
	        monedas= this.repoMonedas.buscarTodos();
	        List<MonedaDato> monedasAEnviar = monedas.stream().map(this::mapMoneda).collect(Collectors.toList());
	          
	        vinculadorMoneda.code = 200;
	        vinculadorMoneda.message = "Monedas cargados exitosamente";
	        vinculadorMoneda.data=monedasAEnviar;
	        response.status(200);
        }
        catch (NullPointerException ex){
            respuesta.setCode(404);
            respuesta.setMessage("No se logró cargar las monedas");
            response.status(404);
            }
       
       
        String jsonMonedas = gson.toJson(vinculadorMoneda);
       
        response.type("application/json");
        response.body(jsonMonedas);

        return response.body();
    }


	public PaisDato mapPais(Pais pais) { 
        PaisDato paisDato=new PaisDato();
		paisDato.clave = pais.getClave();
        paisDato.name = pais.getName();
        
       return paisDato;
    }
	
	public ProvinciaDato mapProvincia(Provincia provincia) { 
        ProvinciaDato provinciaDato=new ProvinciaDato();
		provinciaDato.clave = provincia.getClave();
        provinciaDato.name = provincia.getName();
        
       return provinciaDato;
    }
	
	public CiudadDato mapCiudad(Ciudad ciudad) { 
        CiudadDato ciudadDato=new CiudadDato();
		ciudadDato.clave = ciudad.getClave();
        ciudadDato.name = ciudad.getName();
        
       return ciudadDato;
    }
	public MonedaDato mapMoneda(Moneda moneda) { 
        MonedaDato monedaDato=new MonedaDato();
		monedaDato.clave = moneda.getClave();
        monedaDato.name = moneda.getDescription();
        
       return monedaDato;
    }
	
	
	private class VinculadorPais{
		int code;
		String message;
		List<PaisDato> data= new ArrayList<>();
	}
	private class VinculadorProvincia{
		int code;
		String message;
		List<ProvinciaDato> data= new ArrayList<>();
	}
	private class VinculadorCiudad{
		int code;
		String message;
		List<CiudadDato> data= new ArrayList<>();
	}
	private class VinculadorMoneda{
		int code;
		String message;
		List<MonedaDato> data= new ArrayList<>();
	}
}
