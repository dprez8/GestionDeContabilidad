package Services;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

import Domain.ApiPaises.*;

public class ServiceMain {

	public static void main(String[] args) throws IOException {
		//LLAMO AL ARCHIVO DE CONFIG PARA OBTENER LA URL A LA API DE ML
		Properties prop=new Properties();
		prop.load(new FileReader("src/main/resources/config.properties"));
		//SE HACE EL REQUEST DE LA LISTA DE PAISES
		ServicioGeoref servicioGeoref = ServicioGeoref.instancia(prop.getProperty("URLML"));
		System.out.println("Paises Disponibles: ");
		List<Pais> paisesList = servicioGeoref.ListadoDePaises();
		Pais.asignarIdAPaises(paisesList);
		
		for(Pais pais:paisesList){
			System.out.println(pais.getIdPais() +" "+ pais.name);
		}
		
		//SE LE SOLICITA AL USUARIO INGRESAR ALGUN PAIS POR SU ID NUMERICO
		System.out.print("\nSeleccione un pais por su id: ");
		Scanner scanner = new Scanner(System.in);
		int idPaisElegido = scanner.nextInt();
		Optional<Pais> posiblePais = Pais.paisConId(paisesList,idPaisElegido);
	
		
		//SI EXISTE SE MUESTRA SU MONEDA Y SUS PROVINCIAS
		if(posiblePais.isPresent()) {
			Pais pais= posiblePais.get();
			Moneda moneda = servicioGeoref.Moneda(pais);
			
			System.out.println("\nMoneda de "+pais.name+" :"+moneda.description+"\n");
			
			
			ListadoDeProvincias provincias = servicioGeoref.ListadoDeProvincias(pais);
			Provincia.asignarIdAProvincias(provincias.states);
			
			System.out.println("\nProvincias  de "+pais.name+" :");
			
			for(Provincia province:provincias.states){
				System.out.println(province.getIdProvincia() +" "+ province.name);
			}		
			
			System.out.print("\nSeleccione una provincia por su id: ");
			int idProvinciaElegida = scanner.nextInt();
			Optional<Provincia> posibleProvincia = Provincia.provinciaConId(provincias.states,idProvinciaElegida);
		
			//SI EXISTE LA PROVINCIA ELEGIDA SE MUESTRAN SUS CIUDADES
			if(posibleProvincia.isPresent()){

				Provincia provinciaElegida= posibleProvincia.get();
				ListadoDeCiudades ciudades = servicioGeoref.ListadoDeCiudades(provinciaElegida);
				Ciudad.asignarIdACiudades(ciudades.cities);
				
				System.out.println("\nCiudades  de "+provinciaElegida.name+" :");
				
				for(Ciudad ciudad:ciudades.cities){
					System.out.println(ciudad.getIdCiudad() +" "+ ciudad.name);
				}		
				
			}
			else {
				System.out.println("No existe la provincia seleccionada");		
			}
		}
		else {
			System.out.println("No existe el pais seleccionado");
		}
		scanner.close();
		
	}
}
