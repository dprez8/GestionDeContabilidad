package Domain.CategorizadorDeEmpresas;

import Domain.Organizacion.Empresa;

public class Categoria {
    private String nombre;
    private double montoMin;
    private double montoMax;
    private int personalMin;
    private int personalMax;

    public Categoria(String categoria, Double montoMin, Double montoMax, int personalMin, int personalMax){
        this.nombre      = categoria;
        this.montoMin    = montoMin;
        this.montoMax    = montoMax;
        this.personalMin = personalMin;
        this.personalMax = personalMax;
    }

//    public boolean dentroDelMinMaxPersonal(Empresa unaEmpresa){
//        return this.personalMin < unaEmpresa.getCantidadDePersonal() && unaEmpresa.getCantidadDePersonal() <= this.personalMax;
//    }
//
//    public boolean dentroDelMinMaxMonto(Empresa unaEmpresa){
//        return this.montoMin < unaEmpresa.getVentasAnuales() && unaEmpresa.getVentasAnuales() <= this.montoMax;
//    }

    public Boolean dentroDelMinMax(Empresa unaEmpresa){
        if((montoMin < unaEmpresa.getVentasAnuales() && unaEmpresa.getVentasAnuales() <= montoMax)
                || (personalMin < unaEmpresa.getCantidadDePersonal() && unaEmpresa.getCantidadDePersonal() <= personalMax)) return true;
        else return false;
    }

    public String getNombre(){
        return nombre;
    }
}
