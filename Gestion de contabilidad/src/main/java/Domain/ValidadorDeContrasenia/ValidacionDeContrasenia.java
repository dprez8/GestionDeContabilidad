package Domain.ValidadorDeContrasenia;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Usuarios.Usuario;

import java.io.IOException;

public abstract class ValidacionDeContrasenia {
    public abstract void validarConstrasenia(Usuario usuario) throws repiteContraseniaEnMailOUsuario, contraseniaCorta, contraseniaMuyComun, IOException;
}
