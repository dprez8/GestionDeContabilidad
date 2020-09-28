package Domain.CategorizadorDeEmpresas;


import javax.persistence.*;

import Domain.Organizacion.Empresa;

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

	@Column(name="monto_min")
    private double montoMin;
    @Column(name="monto_max")
    private double montoMax;
    @Column(name="personal_min")
    private int personalMin;
    @Column(name="personal_max")
    private int personalMax;
	
	
	public CategoriaPorSector(CategoriaEmpresa categoriaEmpresa, Sector sector) {
		super();
		this.categoriaEmpresa = categoriaEmpresa;
		this.sector = sector;
	}

	 public CategoriaPorSector(Double montoMin, Double montoMax, int personalMin, int personalMax){
        this.montoMin    = montoMin;
        this.montoMax    = montoMax;
        this.personalMin = personalMin;
        this.personalMax = personalMax;
    }

	  public Boolean cumpleRequisitos(Empresa unaEmpresa){
	        if((montoMin < unaEmpresa.getVentasAnuales() 
	        	&& unaEmpresa.getVentasAnuales() <= montoMax)
	            || (personalMin < unaEmpresa.getCantidadDePersonal() 
	            && unaEmpresa.getCantidadDePersonal() <= personalMax)
	            && unaEmpresa.getSector().equals(this.sector)) 
	        	return true;
	        
	        else 
	        	return false;
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

	public double getMontoMin() {
		return montoMin;
	}

	public void setMontoMin(double montoMin) {
		this.montoMin = montoMin;
	}

	public double getMontoMax() {
		return montoMax;
	}

	public void setMontoMax(double montoMax) {
		this.montoMax = montoMax;
	}

	public int getPersonalMin() {
		return personalMin;
	}

	public void setPersonalMin(int personalMin) {
		this.personalMin = personalMin;
	}

	public int getPersonalMax() {
		return personalMax;
	}

	public void setPersonalMax(int personalMax) {
		this.personalMax = personalMax;
	}

	
}