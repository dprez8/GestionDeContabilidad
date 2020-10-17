package Domain.Controllers;

import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Daos.RepositorioUsuarios;
import spark.Request;
import spark.Response;

public class LoginRestController {
    private RepositorioUsuarios repositorioUsuarios;

    public LoginRestController() {
        repositorioUsuarios = new RepositorioUsuarios(new DaoHibernate<Usuario>(Usuario.class));
    }

    public String login(Request request, Response response) {


        request.body();

        Usuario usuario = repositorioUsuarios.buscarPorQuery("");
        return "";
    }
}
