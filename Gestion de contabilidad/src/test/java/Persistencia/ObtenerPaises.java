package Persistencia;

import org.junit.Test;

import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;


public class ObtenerPaises {
	private Repositorio<Pais> repoPais;
	Repositorio<Provincia> repoProvincia;
		/*@Test
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
		@SuppressWarnings("unchecked")
		@Test
		public void listadoDeCiudades(){
			Provincia provinciaBuscada= new Provincia();
			this.repoProvincia = new Repositorio<Provincia>(new DaoHibernate<Provincia>(Provincia.class));
			provinciaBuscada = repoProvincia.buscar(398);
			List<Ciudad> ciudades= new ArrayList<>();

				ciudades = EntityManagerHelper.createQuery("SELECT c FROM Ciudad c WHERE c.provincia.clave= :code")
				        .setParameter("code",provinciaBuscada.getClave()).getResultList();
				
				for(Ciudad ciudad: ciudades) {
	            	 System.out.println("Provincia: "+ciudad.name);
	             } 
		}*/
		@Test
		public void moneda(){
			 this.repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
			 Pais venezuela=repoPais.buscar(23);
			 System.out.println("Moneda Descripcion: "+venezuela.moneda.description);
		}
}