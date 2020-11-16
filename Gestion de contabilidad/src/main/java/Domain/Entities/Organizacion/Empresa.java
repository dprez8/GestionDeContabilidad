package Domain.Entities.Organizacion;


import Domain.Entities.CategorizadorDeEmpresas.CategorizadorDeEmpresas;
import Domain.Entities.CategorizadorDeEmpresas.*;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@DiscriminatorValue("empresa")
public class Empresa extends CategoriaEntidadJuridica{

    @Expose
    @Column(name = "cantidad_personal")
    private int cantidadDePersonal;

    @Expose
    @Column(name = "ventas_anuales")
    private double ventasAnuales;

    @Expose
    @Column
    private String actividad;

    @Expose
    @ManyToOne
    @JoinColumn(name="categoria_id", referencedColumnName = "categoria_id")
    private CategoriaEmpresa categoriaEmpresa;

    @Expose
    @ManyToOne
    @JoinColumn(name = "sector_id",referencedColumnName = "sector_id")
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

    public CategoriaEmpresa getCategoriaEmpresa() {
        return categoriaEmpresa;
    }
}
