package Services.MercadoLibreServices;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

import Domain.ApiPaises.Ciudad;
import Domain.ApiPaises.ListadoDeCiudades;
import Domain.ApiPaises.ListadoDeProvincias;
import Domain.ApiPaises.Moneda;
import Domain.ApiPaises.Pais;
import Domain.ApiPaises.Provincia;

public class EleccionApi {
	
	private static EleccionApi instancia=null;
	private static Scanner scanner= new Scanner(System.in);
	
	public static EleccionApi instancia(){
	
		if(instancia==null){
			instancia= new EleccionApi();
		}
		return instancia;
	}
	
	
	public static void BajarDatosApiMercadoLibre(Properties prop) throws IOException{
		
		ServicioGeoref servicioGeoref = ServicioGeoref.instancia(prop.getProperty("URLML"));		
		Pais pais=EleccionPais(servicioGeoref);
		Moneda moneda = ObtenerMoneda(servicioGeoref, pais);
		Provincia provincia=EleccionProvincia(servicioGeoref, pais);
		Ciudad ciudad = EleccionCiudad(servicioGeoref, provincia);
		
		scanner.close();
		
		System.out.println("\nDireccion: " + pais.name + " | " + provincia.name+ " | " + ciudad.name);
		System.out.println("Moneda: "+moneda.description);
			
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
