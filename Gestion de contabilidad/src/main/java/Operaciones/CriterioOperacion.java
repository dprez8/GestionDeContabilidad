package Operaciones;

import CategorizadorDeEmpresas.Categoria;

import java.util.List;

public abstract class CriterioOperacion {
    private List<Categoria> categorias;
    private CriterioOperacion criterioHijo;

    public void aplicarCriterio(){} /**DESARROLLAR**/
}
