package Domain.ValidadorDeContrasenia;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;

import java.io.IOException;

public abstract class ValidacionDeContrasenia {
    public abstract void validarConstrasenia(String contrasenia, String mail, String usuarioNombre) throws repiteContraseniaEnMailOUsuario, contraseniaCorta, contraseniaMuyComun, IOException;
}
