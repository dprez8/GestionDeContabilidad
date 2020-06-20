package CategorizadorDeEmpresas;

import Organizacion.Empresa;

import java.util.*;

public class Categorizador {
    private List<Categoria> categorias = new ArrayList<>();

    public Optional<Categoria> obtenerCategoriaDe(Empresa unaEmpresa){
        return categorias.stream().filter(unaCategoria->unaCategoria.categorizarA(unaEmpresa)).findFirst();
    }

    public void agregarCategorias(Categoria ... categorias){
        Collections.addAll(this.categorias,categorias);
    }
}

