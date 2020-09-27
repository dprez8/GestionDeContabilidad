package Domain.CategorizadorDeEmpresas;


import javax.persistence.*;

@Entity
@Table(name="Categoria_x_Sector")
public class CategoriaPorSector{
    
	@EmbeddedId
	CategoriaSectorKey id=new CategoriaSectorKey();
	
	@ManyToOne
	@MapsId("categoriaId")
	@JoinColumn(name="categoria_id")
    public CategoriaEmpresa categoriaEmpresa;
 
	@ManyToOne
	@MapsId("sectorId")
    @JoinColumn(name = "sector_id")
    public Sector sector;

	
	
	public CategoriaPorSector(CategoriaEmpresa categoriaEmpresa, Sector sector) {
		super();
		this.categoriaEmpresa = categoriaEmpresa;
		this.sector = sector;
	}

	public CategoriaEmpresa getCategoriaEmpresa() {
		return categoriaEmpresa;
	}

	public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa) {
		this.categoriaEmpresa = categoriaEmpresa;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

}