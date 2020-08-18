package Domain.ApiPaises;

import java.util.List;
import java.util.Optional;

public class Ciudad {
	public String id;
	public String name;
	private int idCiudad;
	private static int contadorCiudades;
	
	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public int getIdCiudad(){
		return idCiudad;
	}
	public static void asignarIdACiudades(List<Ciudad> ciudades){
		ciudades.forEach(ciudad->ciudad.asignarId());
	}
	
	public void asignarId(){
		contadorCiudades++;
		this.idCiudad=contadorCiudades;
	}
	
	public static Optional<Ciudad> ciudadConId(List<Ciudad> listaCiudades,int idElegido){
		return listaCiudades.stream().filter(p->p.idCiudad==idElegido).findFirst();
	}
}
