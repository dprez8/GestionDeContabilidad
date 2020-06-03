package Organizacion;

public class Categoria {
    private String categoria;  //quizas es mejor usar una especie de enum?
    private int nivelCategoria;
    private double montoMin = 0.0;
    private double montoMax;
    private int personalMin = 0;
    private int personalMax;
    private String sector;

    public Categoria(String categoria, Double montoMin, Double montoMax, int personalMin, int personalMax, String sector){
        this.categoria = categoria;
        this.setNivelCategoria();
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

    private void setNivelCategoria(){
        int nivel = 0;
        switch (this.categoria){
            case "Micro":
                nivel = 1;
                break;
            case "Pequenia":
                nivel = 2;
                break;
            case "Mediana T1":
                nivel = 3;
                break;
            case "Mediana T2":
                nivel = 4;
                break;
        }
        this.nivelCategoria = nivel;
    }

    public int getNivelCategoria() {
        return nivelCategoria;
    }
}
