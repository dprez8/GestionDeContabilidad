package Domain.Entities.ApiPaises;

import javax.persistence.*;

@Entity
@Table(name = "moneda")
public class Moneda {
	
	@Id
	@Column(name="moneda_id")
	public String id;
	@Column
	public String description;
	@Column
	public String symbol;
	@Column
	public String decimal_places;
}
