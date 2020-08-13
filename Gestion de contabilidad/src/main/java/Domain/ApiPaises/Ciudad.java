package Domain.ApiPaises;

import java.util.List;

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
}
