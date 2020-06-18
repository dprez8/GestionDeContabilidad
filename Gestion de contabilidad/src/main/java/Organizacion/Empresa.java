package Organizacion;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Operaciones.Operacion;

public class Empresa extends EntidadJuridica{
    private int cantidadDePersonal;  
    private double ventasAnuales; 
    private Categoria categoria;
    private String rubro;

    
    public Empresa() {
		this.cantidadDePersonal = 2;//valores hardcodeados temporalmente para testeo
	  	this.ventasAnuales = 3000.0; //valores hardcodeados temporalmente para testeo hay que crear setter
		this.rubro = "Agropecuario";
	}
    

	public Empresa(String nombreFicticio,Operacion ... operaciones) {
		this.nombreFicticio=nombreFicticio;
		this.agregarOperaciones(operaciones);
	}



	public void cacularCategoria(List<Categoria> listaCategorias){
//        int promedio = this.promedioDeVentasAnuales(); //Todavia no podemos desarrollar esta funcion, usamos atributo ventasAnuales temporalmente
//        Categoria listaCategoriasOrdenado = listaCategorias.sort(); //Se podria sortear con un criterio propio, o suponer que se la pasa ordenada, o tomando los resultados ver cual es mayor.
        List<Categoria> listaCategoriasFiltrada = listaCategorias.stream().filter(a -> a.getSector().equals(this.rubro))
                .filter(a -> a.dentroDelMinMax(this.ventasAnuales, this.cantidadDePersonal)).collect(Collectors.toList());
        Categoria categoriaObtenida = listaCategoriasFiltrada.stream().max(Comparator.comparingInt(Categoria::getCategoriaLevel)).get();
        this.categoria = categoriaObtenida;
    }

    public int promedioDeVentasAnuales(){
        //desarrollar
        return 0;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
