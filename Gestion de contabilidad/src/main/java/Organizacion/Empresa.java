package Organizacion;


import CategorizadorDeEmpresas.*;

public class Empresa extends CategoriaEntidadJuridica{
    private int cantidadDePersonal;
    private double ventasAnuales;
    private Categoria categoria;
    private Sector sector;
    private String actividad;

    public Empresa (int cantidadDePersonal,double ventasAnuales,String actividad,Sector sector){
        this.cantidadDePersonal = cantidadDePersonal;
        this.ventasAnuales      = ventasAnuales;
        this.actividad          = actividad;
        this.sector             = sector;
    }

    public void cacularCategoria(){
        this.categoria = this.sector.obtenerCategoriaDe(this);
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

    public Categoria getCategoria() {
        return categoria;
    }
}
