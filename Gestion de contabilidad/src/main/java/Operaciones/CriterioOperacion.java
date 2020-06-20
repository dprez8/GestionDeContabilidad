package Operaciones;

import Organizacion.Categoria;

import java.util.List;

public abstract class CriterioOperacion {
    private List<Categoria> categorias;
    private CriterioOperacion criterioPadre;
    private CriterioOperacion criterioHijo;

    public void aplicarCriterio(){} /**DESARROLLAR**/
}
