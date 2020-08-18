package Domain.Operaciones;

import java.util.ArrayList;
import java.util.List;

public class CriterioOperacion {
    private List<CategoriaOperacion> categorias;
    private List<CriterioOperacion> criteriosHijo;
    private CriterioOperacion criterioPadre;
    private String descripcion;

    public CriterioOperacion(String descrip){
        descripcion = descrip;
        categorias = new ArrayList<>();
        criteriosHijo = new ArrayList<>();
    }

    public void setCriterioHijo(CriterioOperacion critOperacion){
        criteriosHijo.add(critOperacion);
    }

    public void setCriterioPadre(CriterioOperacion critOperacion){
        criterioPadre = critOperacion;
    }
    
    public void addCategoria(Categoria categoria){
    	categorias.add(categoria);
    }
}
