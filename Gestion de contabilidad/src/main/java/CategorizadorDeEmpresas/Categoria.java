package Organizacion;

import java.util.HashMap;

public class Categoria {
    private String categoria;  //quizas es mejor usar una especie de enum?
    private double montoMin = 0.0;
    private double montoMax;
    private int personalMin = 0;
    private int personalMax;
    private String sector;
    private static HashMap<String, Integer> categoriasExistentes = new HashMap<String, Integer>();

    public Categoria(String categoria, Double montoMin, Double montoMax, int personalMin, int personalMax, String sector){
        this.categoria = categoria;
        this.montoMin = montoMin;
        this.montoMax = montoMax;
        this.personalMin = personalMin;
        this.personalMax = personalMax;
        this.sector = sector;
    }

    public String getCategoria(){
        return categoria;
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
        return categoriasExistentes.get(categoria);
    }
}
