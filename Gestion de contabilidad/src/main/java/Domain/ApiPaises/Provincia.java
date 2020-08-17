package Domain.ApiPaises;

import java.util.List;
import java.util.Optional;

public class Provincia {
	public String id;
	public String name;
	private int idProvincia;
	private static int contadorProvincias;
	
	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public int getIdProvincia(){
		return idProvincia;
	}
	public static void asignarIdAProvincias(List<Provincia> provincias){
		provincias.forEach(provincia->provincia.asignarId());
	}
	
	public void asignarId(){
		contadorProvincias++;
		this.idProvincia=contadorProvincias;
	}
	
	public static Optional<Provincia> provinciaConId(List<Provincia> listaProvincias,int idElegido){
		return listaProvincias.stream().filter(p->p.idProvincia==idElegido).findFirst();
	}
}
