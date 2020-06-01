package Organizacion;

public class Categoria {
    private String categoria;
    private Double montoMin = 0.0;
    private Double montoMax;
    private String actividad;

    public Categoria(String categoria, Double montoMin, Double montoMax, String actividad){
        this.categoria = categoria;
        this.montoMin = montoMin;
        this.montoMax = montoMax;
        this.actividad = actividad;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getActividad() {
        return actividad;
    }

    public Boolean dentroDelMinMax(int valor){
        if(montoMin < valor && valor >= montoMax) return true;
        else return false;
    }

    public int nivelCategoria(){
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
        return nivel;
    }
}
