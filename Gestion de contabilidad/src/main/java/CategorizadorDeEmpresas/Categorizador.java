package CategorizadorDeEmpresas;

import Organizacion.Empresa;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Categorizador {
    private List<Categoria> categorias = new ArrayList<>();

    /**Guardo el stream filtrado en categoriaFiltrada y luego creo otra variable local de tipo
       lista para "castear" el stream a una lista tipo categoria y asi poder entender el mensaje get(indice)
        ya que la lista stream no entiende ese mensaje*/
    public Categoria obtenerCategoriaDe(Empresa unaEmpresa){
        Stream<Categoria> categoriaFiltrada = categorias.stream().filter(unaCategoria->categorizarA(unaEmpresa,unaCategoria));
        List<Categoria> categoriaFilter = categoriaFiltrada.collect(Collectors.toList());
        return categoriaFilter.get(0); //Se supone es esta lista solo tiene un elemento o ninguno, ya que una empresa no podria tener 2 categorias
    }

    public boolean categorizarA(Empresa unaEmpresa,Categoria unaCategoria){
        if(unaCategoria.getSector().equals(unaEmpresa.getSector())) {
            return unaCategoria.dentroDelMinMaxMonto(unaEmpresa) && unaCategoria.dentroDelMinMaxPersonal(unaEmpresa);
        }
        return false;
    }

    public void agregarCategorias(Categoria ... categorias){
        Collections.addAll(this.categorias,categorias);
    }
}

