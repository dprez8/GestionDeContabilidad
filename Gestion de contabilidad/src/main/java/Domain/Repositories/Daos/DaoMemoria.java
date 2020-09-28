package Domain.Repositories.Daos;

import java.util.ArrayList;
import java.util.List;

public class DaoMemoria<T> implements IDao<T> {
    private List<T> datos;

    public DaoMemoria(){
        this.datos = new ArrayList<T>();
    }
    @Override
    public void agregar(T object) {
        this.datos.add(object);
    }

    @Override
    public void eliminar(T object) {
        this.datos.remove(object);
    }

    @Override
    public void modificar(T object) {
        return;
    }

    @Override
    public boolean existe(int id) {
        return false;
    }

    @Override
    public T buscar(int id) {
        return null;
    }

    @Override
    public List<T> buscarTodos() {
        return (List<T>) this.datos;
    }
}
