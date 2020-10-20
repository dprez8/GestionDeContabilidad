package Domain.Entities.ApiPaises;

import java.util.List;
import java.util.Optional;
import javax.persistence.*;

@Entity
@Table(name="provicia")
public class Provincia {
	@Id
	@GeneratedValue
	@Column(name="provincia_id")
	private int clave;
	@Column(name="provincia_code")
	public String id;
	@Column
	public String name;
	@Transient
	private int idProvincia;
	@ManyToOne
	@JoinColumn(name = "pais_id", referencedColumnName = "pais_id")
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
	
	
	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void asignarId(){
		contadorProvincias++;
		this.idProvincia=contadorProvincias;
	}
	
	public static Optional<Provincia> provinciaConId(List<Provincia> listaProvincias,int idElegido){
		return listaProvincias.stream().filter(p->p.idProvincia==idElegido).findFirst();
	}
}
