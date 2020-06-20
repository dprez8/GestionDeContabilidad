package CategorizadorDeEmpresas;

import Organizacion.Empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Categorizador {
    private List<Categoria> categorias = new ArrayList<>();

    public Optional<Categoria> obtenerCategoriaDe(Empresa unaEmpresa){
        return categorias.stream().filter(unaCategoria->unaCategoria.categorizarA(unaEmpresa)).findFirst();
    }
}
