package Domain.Controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.ApiVinculador.EgresoAEnviar;
import Domain.Entities.ApiVinculador.IngresoAEnviar;
import Domain.Entities.ClasesParciales.PaisDato;
import Domain.Entities.ClasesParciales.ProvinciaDato;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
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
	
	public String listadoDePaises(Request request, Response response) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
        Gson gson = new Gson();
        Respuesta respuesta=new Respuesta();
        List<Pais> paises=new ArrayList<>();

   	 	this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
        paises= this.repoPais.buscaTodos();
        VinculadorPais vinculadorPais = new VinculadorPais();
        
        
        try {
        List<PaisDato> paisesAEnviar = paises.stream().map(this::mapPais).collect(Collectors.toList());
       
        vinculadorPais.paisesDato=paisesAEnviar;
        
        vinculadorPais.code = 200;
        vinculadorPais.message = "Paises cargados exitosamente";
        response.status(200);
        }
        catch (NoResultException ex){
            respuesta.code = 404;
            respuesta.message =  "No se logró cargar los paises";
            response.status(404);
            }
       
       
        String jsonPaises = gson.toJson(vinculadorPais);
       
        response.type("application/json");
        response.body(jsonPaises);

        return response.body();
    }
	@SuppressWarnings("unchecked")
	public String listadoDeProvincias(Request request, Response response){
		Gson gson2 = new Gson();
		Pais paisBuscado= new Pais();
		this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
		paisBuscado = repoPais.buscar(new Integer(request.params("clave")));
		List<Provincia> provincias= new ArrayList<>();
		VinculadorProvincia vinculadorProvincia = new VinculadorProvincia();
		
		try{
			provincias = EntityManagerHelper.createQuery("SELECT c FROM Provincia c WHERE c.pais.clave= :code")
			        .setParameter("code",paisBuscado.getClave()).getResultList();
	      
             List<ProvinciaDato> provinciasAEnviar = provincias.stream().map(this::mapProvincia).collect(Collectors.toList());
            
             vinculadorProvincia.provinciasDato=provinciasAEnviar;
             

             vinculadorProvincia.code = 200;
             vinculadorProvincia.message = "Provincias cargadas";
             response.status(200);
		}
        catch (NoResultException ex){
        	 vinculadorProvincia.code = 404;
        	 vinculadorProvincia.message =  "No se logro cargarlas provincias";
            response.status(404);
        }
		

        String jsonProvincias = gson2.toJson(vinculadorProvincia);
       
        response.type("application/json");
        response.body(jsonProvincias);

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
	
	private class VinculadorPais{
		int code;
		String message;
		List<PaisDato> paisesDato= new ArrayList<>();
	}
	private class VinculadorProvincia{
		int code;
		String message;
		List<ProvinciaDato> provinciasDato= new ArrayList<>();
	}
}
