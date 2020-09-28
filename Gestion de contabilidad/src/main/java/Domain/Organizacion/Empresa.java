package Domain.Organizacion;


import Domain.CategorizadorDeEmpresas.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("empresa")
public class Empresa extends CategoriaEntidadJuridica{

    @Column(name = "cantidad_personal")
    private int cantidadDePersonal;

    @Column(name = "ventas_anuales")
    private double ventasAnuales;

    @Column
    private String actividad;

    @Transient
    private CategoriaEmpresa categoriaEmpresa;

    @Transient
    private Sector sector;

    /*******************Setters & Getters******************/
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
    public void cacularCategoria(){
        this.categoriaEmpresa = this.sector.obtenerCategoriaDe(this);
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

    public CategoriaEmpresa getCategoriaEmpresa() {
        return categoriaEmpresa;
    }
}
