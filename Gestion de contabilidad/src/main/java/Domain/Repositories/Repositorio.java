package Domain.Repositories;

import Domain.Repositories.Daos.IDao;

import java.util.List;

public class Repositorio<T> {
    protected IDao<T> dao;

    public Repositorio(IDao<T> dao){
        this.dao = dao;
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

    public List<T> buscarTodos() {
        return this.dao.buscarTodos();
    }

    public void modificar(T object){
        this.dao.modificar(object);
    }

    public boolean existe(int id){
        return this.dao.existe(id);
    }

}
