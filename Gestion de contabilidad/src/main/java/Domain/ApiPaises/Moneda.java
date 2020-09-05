package Domain.ApiPaises;

import javax.persistence.Entity;

@Entity
public class Moneda {
	public String id;
	public String description;
	public String symbol;
	public String decimal_places;
}
