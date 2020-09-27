package Domain.Organizacion;


import Domain.CategorizadorDeEmpresas.*;

public class Empresa extends CategoriaEntidadJuridica{
    private int cantidadDePersonal;
    private double ventasAnuales;
    private CategoriaEmpresa categoriaEmpresa;
    private Sector sector;
    private String actividad;

    public Empresa (int cantidadDePersonal,double ventasAnuales,String actividad,Sector sector){
        this.cantidadDePersonal = cantidadDePersonal;
        this.ventasAnuales      = ventasAnuales;
        this.actividad          = actividad;
        this.sector             = sector;
    }

    public void cacularCategoria(){
        this.categoriaEmpresa = CategorizadorDeEmpresas.obtenerCategoriaDe(this);
    }

    public Sector getSector() {
        return sector;
    }

    public int getCantidadDePersonal() {
        return cantidadDePersonal;
    }

    public double getVentasAnuales() {
        return ventasAnuales;
    }

    public String getActividad(){
        return actividad;
    }

    public int promedioDeVentasAnuales(){
        //desarrollar
        return 0;
    }

    public CategoriaEmpresa getCategoriaEmpresa() {
        return categoriaEmpresa;
    }

	public void setCantidadDePersonal(int cantidadDePersonal) {
		this.cantidadDePersonal = cantidadDePersonal;
	}

	public void setVentasAnuales(double ventasAnuales) {
		this.ventasAnuales = ventasAnuales;
	}

	public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa) {
		this.categoriaEmpresa = categoriaEmpresa;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
    
    
}
