package Domain.Entities.ValidadorDeContrasenia;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Entities.Usuarios.Usuario;

import java.io.IOException;

public abstract class ValidacionDeContrasenia {
    public abstract boolean validarConstrasenia(Usuario usuario) throws repiteContraseniaEnMailOUsuario, contraseniaCorta, contraseniaMuyComun, IOException;
}
