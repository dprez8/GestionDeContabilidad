package Repositories;

public interface IDao<T> {
    public void agregar(T object);

    public void eliminar(T object);

    public T buscar(int id,Class<T> clase);

    public void modificar(T object);

    public boolean existe(int id);
}
