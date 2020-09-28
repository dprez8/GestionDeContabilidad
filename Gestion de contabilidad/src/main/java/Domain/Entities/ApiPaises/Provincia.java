package Domain.Entities.ApiPaises;

import java.util.List;
import java.util.Optional;
import javax.persistence.*;

@Entity
@Table
public class Provincia {
	@Id
	@Column(name="provincia_id")
	public String id;
	@Column
	public String name;
	@Transient
	private int idProvincia;
	@ManyToOne
	private Pais pais;
	
	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

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
