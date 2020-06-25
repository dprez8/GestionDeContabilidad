package CategorizadorDeEmpresas;

import Organizacion.Empresa;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Categorizador {
    private List<Categoria> categorias = new ArrayList<>();
    private HashMap<String, Integer> categoriasExistentes = new HashMap<String, Integer>();

    public Categoria obtenerCategoriaDe(Empresa unaEmpresa){
        List<Categoria> listaCategoriasFiltrada = this.categorias.stream().filter(a -> a.getSector().equals(unaEmpresa.getSector()))
                .filter(a -> a.dentroDelMinMax(unaEmpresa)).collect(Collectors.toList());
        Categoria categoriaObtenida = listaCategoriasFiltrada.stream().max(Comparator.comparingInt(a->categoriasExistentes.get(a.getNombre()))).get();
        return categoriaObtenida;
    }

    public void agregarCategorias(Categoria ... categorias){
        Collections.addAll(this.categorias,categorias);
    }

    public void addCategoriaExistente(String key, Integer value) {
        categoriasExistentes.put(key, value);
    }

    public void removeCategoriaExistente(String key){
        categoriasExistentes.remove(key);
    }
}

