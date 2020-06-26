package ValidadorDeContrasenia;
import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;

import java.io.IOException;

public abstract class ValidacionDeContrasenia {
    public abstract void validarConstrasenia(String contrasenia, String mail, String usuarioNombre) throws repiteContraseniaEnMailOUsuario, contraseniaCorta, contraseniaMuyComun, IOException;
}
