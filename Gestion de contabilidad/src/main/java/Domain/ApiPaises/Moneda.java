package Domain.ApiPaises;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Moneda {
	
	@Column(name="country_id")
	public String id;
	@Column
	public String description;
	@Column
	public String symbol;
	public String decimal_places;
}
