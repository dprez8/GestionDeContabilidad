package Domain.Entities.CategorizadorDeEmpresas;
import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class CategoriaSectorKey implements Serializable {
    
	@Column(name = "categoria_id")
    public int categoriaId;
 
    @Column(name = "sector_id")
    public int sectorId;
 
}