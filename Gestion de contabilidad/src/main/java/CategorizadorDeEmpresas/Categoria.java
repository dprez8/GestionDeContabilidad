package CategorizadorDeEmpresas;

import Organizacion.Empresa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Categoria {
    private String nombre;
    private double montoMin;
    private double montoMax;
    private int personalMin;
    private int personalMax;
    private Sector sector;
    private static List<String> actividadesExceptuadas = new ArrayList<>();
    //private static HashMap<String, Integer> categoriasExistentes = new HashMap<String, Integer>();

    public Categoria(String categoria, Double montoMin, Double montoMax, int personalMin, int personalMax, Sector sector){
        this.nombre      = categoria;
        this.montoMin    = montoMin;
        this.montoMax    = montoMax;
        this.personalMin = personalMin;
        this.personalMax = personalMax;
        this.sector      = sector;
    }

    public boolean estaDentroDeLasActividadesExceptuadas(Empresa unaEmpresa){
        return actividadesExceptuadas.stream().anyMatch(
                                               unaActividad->unaActividad.equalsIgnoreCase(unaEmpresa.getActividad()));

    }

    public static void agregarActivadadesExceptuadas(String ... actividades){
        Collections.addAll(actividadesExceptuadas, actividades);
    }

    public boolean dentroDelMinMaxPersonal(Empresa unaEmpresa){
        return this.personalMin < unaEmpresa.getCantidadDePersonal() && unaEmpresa.getCantidadDePersonal() <= this.personalMax;
    }

    public boolean dentroDelMinMaxMonto(Empresa unaEmpresa){
        return this.montoMin < unaEmpresa.getVentasAnuales() && unaEmpresa.getVentasAnuales() <= this.montoMax;
    }

    public Sector getSector() {
        return sector;
    }
/*
    public String getNombre(){
        return nombre;
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
