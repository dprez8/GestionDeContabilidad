package Domain.Entities.CategorizadorDeEmpresas;


import javax.persistence.*;

@Entity
@Table(name="Categoria_x_Sector")
public class CategoriaPorSector{
    
	@EmbeddedId
	CategoriaSectorKey id;
	
	@ManyToOne
	@MapsId("categoriaId")
	@JoinColumn(name="categoria_id")
    public CategoriaEmpresa categoriaEmpresa;
 
	@ManyToOne
	@MapsId("sectorId")
    @JoinColumn(name = "sector_id")
    public Sector sector;

}