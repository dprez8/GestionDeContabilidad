package Organizacion;


import CategorizadorDeEmpresas.*;

import java.util.Optional;

public class Empresa extends EntidadJuridica{
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

    public void cacularCategoria(Categorizador categorizador){
        this.categoria = categorizador.obtenerCategoriaDe(this);
    /*
//        int promedio = this.promedioDeVentasAnuales(); //Todavia no podemos desarrollar esta funcion, usamos atributo ventasAnuales temporalmente
//        Categoria listaCategoriasOrdenado = listaCategorias.sort(); //Se podria sortear con un criterio propio, o suponer que se la pasa ordenada, o tomando los resultados ver cual es mayor.
        List<Categoria> listaCategoriasFiltrada = listaCategorias.stream().filter(a -> a.getSector().equals(this.sector))
                .filter(a -> a.dentroDelMinMax(this.ventasAnuales, this.cantidadDePersonal)).collect(Collectors.toList());
        Categoria categoriaObtenida = listaCategoriasFiltrada.stream().max(Comparator.comparingInt(Categoria::getCategoriaLevel)).get();
        this.categoria = categoriaObtenida;

     */
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
