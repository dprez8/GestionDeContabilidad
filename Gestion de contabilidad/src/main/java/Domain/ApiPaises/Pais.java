package Domain.ApiPaises;

import java.util.List;
import java.util.Optional;
import javax.persistence.*;

@Entity
@Table(name= "Pais")
public class Pais {
	
	@Id
	@Column(name="pais_id")
	public String id;
	@Column
	public String name;
	@Column
	public String currency_id;
	@Transient
	private int idPais;
	@Column
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

	public static Optional<Pais> paisConId(List<Pais> listaPaises,int idElegido){
		return listaPaises.stream().filter(p->p.idPais==idElegido).findFirst();
	}
}
