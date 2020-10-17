package Domain.Controllers;

import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Daos.RepositorioUsuarios;
import Domain.Repositories.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private RepositorioUsuarios repoUsuarios;

    public LoginController () {
        this.repoUsuarios = new RepositorioUsuarios(new DaoHibernate<Usuario>(Usuario.class));
    }

    public ModelAndView inicio(Request request, Response response){
        //Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(null,"login.hbs");
    }

    public Response login(Request request, Response response){
        try{
            String nombreDeUsuario = request.queryParams("nombreDeUsuario");
            String contrasenia     = request.queryParams("contrasenia");

            Usuario usuario = repoUsuarios.buscarPorQuery("from Usuario where nombre = " + nombreDeUsuario +
                                                                            " and contrasenia = " + contrasenia);

            if(usuario != null){

                request.session(true);
                request.session().attribute("id", usuario.getId());

                response.redirect("/usuarios");
            }
            else{
                response.redirect("/");
            }
        }
        catch (Exception e){
            //Funcionalidad disponible solo con persistencia en Base de Datos
            response.redirect("/usuarios");
        }
        finally {
            return response;
        }
    }

    public Response logout(Request request, Response response){
        request.session().invalidate();
        response.redirect("/");
        return response;
    }
}

