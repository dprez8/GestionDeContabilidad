package CategorizadorDeEmpresas;

import Organizacion.Empresa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Categoria {
    private String nombre;
    //private double montoMin = 0.0;
    private double montoMax;
    //private int personalMin = 0;
    private int personalMax;
    private Sector sector;
    private static List<String> actividadesExceptuadas = new ArrayList<>();
    //private static HashMap<String, Integer> categoriasExistentes = new HashMap<String, Integer>();

    public Categoria(String categoria, Double montoMin, Double montoMax, int personalMin, int personalMax, Sector sector){
        this.nombre      = categoria;
      //  this.montoMin    = montoMin;
        this.montoMax    = montoMax;
       // this.personalMin = personalMin;
        this.personalMax = personalMax;
        this.sector      = sector;
    }

    public boolean categorizarA(Empresa unaEmpresa){
        if(sector.equals(unaEmpresa.getSector())) {
            if (!estaDentroDeLasActividadesExceptuadas(unaEmpresa)) {
                if (unaEmpresa.getVentasAnuales() < montoMax) {
                    return unaEmpresa.getCantidadDePersonal() <= personalMax;
                }
            }else{
                return unaEmpresa.getCantidadDePersonal() <= personalMax;
            }
        }
        return false;
    }

    private boolean estaDentroDeLasActividadesExceptuadas(Empresa unaEmpresa){
        return actividadesExceptuadas.stream().anyMatch(
                                               unaActividad->unaActividad.equalsIgnoreCase(unaEmpresa.getActividad()));

    }

    public static void agregarActivadadesExceptuadas(String ... actividades){
        Collections.addAll(actividadesExceptuadas, actividades);
    }
/*
    public String getNombre(){
        return nombre;
    }

    public String getSector() {
        return sector;
    }

    public Boolean dentroDelMinMax(double monto, int personal){
        if((montoMin < monto && monto <= montoMax) || (personalMin < personal && personal <= personalMax)) return true;
        else return false;
    }

    public static void addCategoriaExistente(String key, Integer value) {
        categoriasExistentes.put(key, value);
    }

    public static void removeCategoriaExistente(String key){
        categoriasExistentes.remove(key);
    }

    public Integer getCategoriaLevel(){
        return categoriasExistentes.get(nombre);
    }*/
}
