package Domain.Repositories.Daos;

import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;

public class RepositorioUsuarios extends Repositorio<Usuario> {

    public RepositorioUsuarios(IDao<Usuario> dao) {
        super(dao);
    }

    public Usuario buscarPorQuery(String query) {
        return this.dao.buscarPorQuery(query);
    }
}
