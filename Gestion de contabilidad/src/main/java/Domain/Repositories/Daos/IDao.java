package Domain.Repositories.Daos;

import java.util.List;

public interface IDao<T> {
    void agregar(T object);

    void eliminar(T object);

    void modificar(T object);

    boolean existe(int id);

    T buscar(int id);

    List<T> buscarTodos();
}
