package Domain.Entities.ApiPaises;

import java.util.List;
import java.util.Optional;

import javax.persistence.*;

@Entity
@Table(name = "moneda")
public class Moneda {
	
	@Id
	@GeneratedValue
	@Column(name="moneda_id")
	private int clave;
	@Column(name="moneda_code")
	public String id;
	@Column
	public String description;
	@Column
	public String symbol;
	@Column
	public String decimal_places;
	
	public String getId(){
		return id;
	}
	
	
	public static Optional<Moneda> buscarMoneda(List<Moneda> listaMonedas,String currency_id){
		return listaMonedas.stream().filter(p-> p.getId().equals(currency_id)).findFirst();
	}
}


