package Domain.CategorizadorDeEmpresas;

import Domain.Organizacion.Empresa;

import java.util.*;
import java.util.stream.Collectors;

public class Sector {
    private String nombre;
    private List<Categoria> categorias;
    private HashMap<String, Integer> categoriasExistentes;

    public Sector(String nombre){
        this.categorias = new ArrayList<>();
        this.categoriasExistentes = new HashMap<String,Integer>();
        this.nombre = nombre;
    }

    public Categoria obtenerCategoriaDe(Empresa unaEmpresa){
        List<Categoria> listaCategoriasFiltrada = this.categorias.stream().filter(a -> a.getSector().equals(unaEmpresa.getSector()))
                .filter(a -> a.dentroDelMinMax(unaEmpresa)).collect(Collectors.toList());
        Categoria categoriaObtenida = listaCategoriasFiltrada.stream().max(Comparator.comparingInt(a->categoriasExistentes.get(a.getNombre()))).get();
        return categoriaObtenida;
    }
    public void addCategorias(Categoria ... categorias){
        Collections.addAll(this.categorias,categorias);
    }

    public void addCategoriaExistente(String key, Integer value) {
        categoriasExistentes.put(key, value);
    }

    public void removeCategoriaExistente(String key){
        categoriasExistentes.remove(key);
    }
}
