package CategorizadorDeEmpresas;

import Organizacion.Empresa;

import java.util.*;

public class Categorizador {
    private List<Categoria> categorias = new ArrayList<>();

    public Optional<Categoria> obtenerCategoriaDe(Empresa unaEmpresa){
        return categorias.stream().filter(unaCategoria->categorizarA(unaEmpresa,unaCategoria)).findFirst();
    }

    public boolean categorizarA(Empresa unaEmpresa,Categoria unaCategoria){
        if(unaCategoria.getSector().equals(unaEmpresa.getSector())) {
            if (!unaCategoria.estaDentroDeLasActividadesExceptuadas(unaEmpresa)) {
                if (unaCategoria.dentroDelMinMaxMonto(unaEmpresa)) {
                    return unaCategoria.dentroDelMinMaxPersonal(unaEmpresa);
                }
            }else{
                return unaCategoria.dentroDelMinMaxPersonal(unaEmpresa);
            }
        }
        return false;
    }

    public void agregarCategorias(Categoria ... categorias){
        Collections.addAll(this.categorias,categorias);
    }
}

