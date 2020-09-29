package Domain.Entities.ApiPaises;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "ciudad")
public class Ciudad{
	@Id
	@Column(name="ciudad_id")
	public String id;

	@Column
	public String name;

	@Transient
	private int idCiudad;

	@Transient
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
