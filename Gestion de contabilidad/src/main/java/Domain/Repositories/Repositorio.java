package Domain.Repositories;

import Domain.Repositories.Daos.IDao;

import java.util.List;

public class Repositorio<T> {
    private IDao<T> dao;
    private Class<T> clase;

    public Repositorio(IDao<T> dao,Class<T> clase){
        this.dao = dao;
        this.clase = clase;
    }

    public void setDao(IDao<T> dao) {
        this.dao = dao;
    }

    public void agregar(T object){
        this.dao.agregar(object);
    }

    public void eliminar(T object){
        this.dao.eliminar(object);
    }

    public T buscar(int id){
        return this.dao.buscar(id);
    }

    public List<T> buscaTodos() {
        return this.dao.buscarTodos();
    }

    public void modificar(T object){
        this.modificar(object);
    }

    public boolean existe(int id){
        return this.dao.existe(id);
    }
}
