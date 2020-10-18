package Domain.Entities.ApiPaises;

import java.util.List;
import java.util.Optional;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name= "pais")
public class Pais {
	
	@Id
	@GeneratedValue
	@Column(name="pais_id")
	private int clave;
	@Column(name="pais_code")
	public String id;
	@Column
	public String name;
	@Transient
	public String currency_id;
	@ManyToOne
	@JoinColumn(name = "moneda_id", referencedColumnName = "moneda_id")
	public Moneda moneda;
	@Transient
	private int idPais;
	@Transient
	private static int contadorPaises;
	

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	public int getIdPais(){
		return idPais;
	}
	public static void asignarIdAPaises(List<Pais> paises){
		paises.forEach(pais->pais.asignarId());
	}
	
	public void asignarId(){
		contadorPaises++;
		this.idPais=contadorPaises;
	}
	
	
	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public static Optional<Pais> paisConId(List<Pais> listaPaises,int idElegido){
		return listaPaises.stream().filter(p->p.idPais==idElegido).findFirst();
	}
}
