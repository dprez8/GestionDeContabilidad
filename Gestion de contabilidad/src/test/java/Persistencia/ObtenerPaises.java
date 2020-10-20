package Persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;

import com.google.gson.Gson;

import Domain.Controllers.Respuesta;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import db.EntityManagerHelper;


public class ObtenerPaises {
	private Repositorio<Pais> repoPais;
		@Test
		public void listadoDePaises() {
	      
	        List<Pais> paises=new ArrayList<>();
	
	        	 this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
	             paises= this.repoPais.buscaTodos();
	             
	             for(Pais pais: paises) {
	            	 System.out.println("Pais: "+pais.name);
	             }   
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void listadoDeProvincias() {
	      
			Pais paisBuscado = new Pais();
			this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
			paisBuscado=repoPais.buscar(1);
			System.out.println("Pais: "+paisBuscado.getName());
			List<Provincia> provincias= new ArrayList<>();
	           
			provincias = EntityManagerHelper.createQuery("SELECT c FROM Provincia c WHERE c.pais.clave= :code")
			        .setParameter("code",paisBuscado.getClave()).getResultList();
	      
	             for(Provincia provincia: provincias) {
	            	 System.out.println("Provincia: "+provincia.name);
	             } 
		}
}