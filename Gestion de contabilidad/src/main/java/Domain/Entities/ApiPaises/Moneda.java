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
	
	
	public int getClave() {
		return clave;
	}


	public void setClave(int clave) {
		this.clave = clave;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public String getDecimal_places() {
		return decimal_places;
	}


	public void setDecimal_places(String decimal_places) {
		this.decimal_places = decimal_places;
	}


	public void setId(String id) {
		this.id = id;
	}


	public static Optional<Moneda> buscarMoneda(List<Moneda> listaMonedas,String currency_id){
		return listaMonedas.stream().filter(p-> p.getId().equals(currency_id)).findFirst();
	}
}


