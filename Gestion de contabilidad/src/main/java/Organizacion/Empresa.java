package Organizacion;


import java.util.List;

public class Empresa extends EntidadJuridica{

    private int cantidadDePersonal;
    private int ventasAnuales;
    private Categoria categoria;

    public void cacularCategoria(List<Categoria> listaCategoriasPorMonto, List<Categoria> listaCategoriasPorPersonal){
        String actividad = this.getActividad();
        int promedio = this.promedioDeVentasAnuales();
        Categoria encontrado = listaCategoriasPorMonto.stream().filter(a -> a.getActividad().equals(actividad) && a.dentroDelMinMax(promedio)).findAny().orElse(null);
        Categoria encontrado2 = listaCategoriasPorPersonal.stream().filter(a -> a.getActividad().equals(actividad) && a.dentroDelMinMax(this.cantidadDePersonal)).findAny().orElse(null);
        if (encontrado.nivelCategoria() >= encontrado2.nivelCategoria()) this.categoria = encontrado;
        else this.categoria = encontrado2;
    }

    public int promedioDeVentasAnuales(){
        //desarrollar
        return 0;
    }
}
