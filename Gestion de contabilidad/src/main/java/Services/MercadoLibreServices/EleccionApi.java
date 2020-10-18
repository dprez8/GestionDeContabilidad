package Services.MercadoLibreServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.ListadoDeCiudades;
import Domain.Entities.ApiPaises.ListadoDeProvincias;
import Domain.Entities.ApiPaises.Moneda;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;

public class EleccionApi {
	
	private static EleccionApi instancia=null;
	private static Scanner scanner= new Scanner(System.in);
	
	public static EleccionApi instancia(){
	
		if(instancia==null){
			instancia= new EleccionApi();
		}
		return instancia;
	}
	
	
	public static void BajarDatosApiMercadoLibrePorId(Properties prop) throws IOException{
		
		ServicioGeoref servicioGeoref = ServicioGeoref.instancia(prop.getProperty("URLML"));		
		Pais pais=EleccionPais(servicioGeoref);
		Moneda moneda = ObtenerMoneda(servicioGeoref, pais);
		Provincia provincia=EleccionProvincia(servicioGeoref, pais);
		Ciudad ciudad = EleccionCiudad(servicioGeoref, provincia);
		
		scanner.close();
		
		System.out.println("\nDireccion: " + pais.name + " | " + provincia.name+ " | " + ciudad.name);
		
		if(moneda!=null)
			System.out.println("Moneda: "+moneda.description);
		else 
			System.out.println("Moneda no disponible");
			
	}
public static void BajarDatosApiMercadoLibreABaseDeDatos(Properties prop) throws IOException{
		
		Repositorio<Pais>repoPais = new Repositorio<Pais>(new DaoHibernate<Pais>(Pais.class));
		Repositorio<Provincia>repoProvincia = new Repositorio<Provincia>(new DaoHibernate<Provincia>(Provincia.class));
		Repositorio<Ciudad>repoCiudad = new Repositorio<Ciudad>(new DaoHibernate<Ciudad>(Ciudad.class));
		Repositorio<Moneda>repoMoneda = new Repositorio<Moneda>(new DaoHibernate<Moneda>(Moneda.class));
		
		ServicioGeoref servicioGeoref = ServicioGeoref.instancia(prop.getProperty("URLML"));		
		List<Pais> paisesList = servicioGeoref.ListadoDePaises();
		List<Moneda> monedasList = new ArrayList<>();
		
		ListadoDeProvincias provincias;
		ListadoDeCiudades ciudades;
		Moneda moneda;
		System.out.println("Almacenando datos");
		
		for(Pais pais: paisesList){
			
			Optional<Moneda> posibleMoneda = Moneda.buscarMoneda(monedasList,pais.currency_id);
			
			if(posibleMoneda.isPresent()) {
				moneda= posibleMoneda.get();
				pais.setMoneda(moneda);
			}
			else {
				moneda = servicioGeoref.Moneda(pais);
				if(moneda!=null) {
					monedasList.add(moneda);
					repoMoneda.agregar(moneda);
				}
				pais.setMoneda(moneda);
			}	
			
			repoPais.agregar(pais);
			
			
			provincias = servicioGeoref.ListadoDeProvincias(pais);
			
			if(provincias.states.size()!=0){
				for(Provincia provincia: provincias.states){
					provincia.setPais(pais);
					repoProvincia.agregar(provincia);
					ciudades = servicioGeoref.ListadoDeCiudades(provincia);
					
					if(ciudades.cities.size()!=0) {
						for(Ciudad ciudad:ciudades.cities){
							ciudad.setProvincia(provincia);
							repoCiudad.agregar(ciudad);
						}
					}
					System.out.println("Guardando datos de: "+provincia.name);
				}
			}
		
		}
		
	
		
		System.out.println("Carga de Datos con exito");
	}
	
	
	public static Pais EleccionPais(ServicioGeoref servicioGeoref) throws IOException{
		
		System.out.println("Paises Disponibles: ");
		List<Pais> paisesList = servicioGeoref.ListadoDePaises();
		Pais.asignarIdAPaises(paisesList);
		
		for(Pais pais:paisesList){
			System.out.println(pais.getIdPais() +" "+ pais.name);
		}
		
		//SE LE SOLICITA AL USUARIO INGRESAR ALGUN PAIS POR SU ID NUMERICO
		System.out.print("\nSeleccione un pais por su id: ");
		//Scanner scanner = new Scanner(System.in);
		int idPaisElegido = scanner.nextInt();
		//scanner.close();
		Optional<Pais> posiblePais = Pais.paisConId(paisesList,idPaisElegido);
		
		if(posiblePais.isPresent()) {
			Pais pais= posiblePais.get();
			return pais;
		}
		
		else {
			System.out.println("No existe el pais seleccionado");
			return null;
		}	
	}
	

	public static Provincia EleccionProvincia(ServicioGeoref servicioGeoref,Pais pais) throws IOException{
			
		ListadoDeProvincias provincias = servicioGeoref.ListadoDeProvincias(pais);
		Provincia.asignarIdAProvincias(provincias.states);
		
		System.out.println("\nProvincias  de "+pais.name+" :");
		
		for(Provincia province:provincias.states){
			System.out.println(province.getIdProvincia() +" "+ province.name);
		}		
		//Scanner scanner = new Scanner(System.in);
		System.out.print("\nSeleccione una provincia por su id: ");
		int idProvinciaElegida = scanner.nextInt();
		//scanner.close();
		
		Optional<Provincia> posibleProvincia = Provincia.provinciaConId(provincias.states,idProvinciaElegida);
		
		if(posibleProvincia.isPresent()){
			Provincia provinciaElegida= posibleProvincia.get();
			return provinciaElegida;
		}
		else {
			System.out.println("No existe la provincia seleccionada");		
			return null;
		}
	}
	
	public static Ciudad EleccionCiudad(ServicioGeoref servicioGeoref,Provincia provinciaElegida) throws IOException{
		ListadoDeCiudades ciudades = servicioGeoref.ListadoDeCiudades(provinciaElegida);
		Ciudad.asignarIdACiudades(ciudades.cities);
		
		System.out.println("\nCiudades  de "+provinciaElegida.name+" :");
		
		for(Ciudad ciudad:ciudades.cities){
			System.out.println(ciudad.getIdCiudad() +" "+ ciudad.name);
		}	
		
		//Scanner scanner = new Scanner(System.in);
		System.out.print("\nSeleccione una ciudad por su id: ");
		int idCiudadElegida = scanner.nextInt();
		//scanner.close();
		
		Optional<Ciudad> posibleCiudad = Ciudad.ciudadConId(ciudades.cities,idCiudadElegida);
		
		if(posibleCiudad.isPresent()){
			Ciudad ciudad= posibleCiudad.get();
			return ciudad;
		}
		else {
			System.out.println("No existe la ciudad seleccionada");		
			return null;
		}
	
	}
	
	public static Moneda ObtenerMoneda(ServicioGeoref servicioGeoref,Pais pais) throws IOException{
		
		Moneda moneda = servicioGeoref.Moneda(pais);
		return moneda;
	}
	
}
