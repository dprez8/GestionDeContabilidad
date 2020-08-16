package Domain.ValidadorDeContrasenia;

import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Usuarios.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidadorDeContrasenia {
    private static List<ValidacionDeContrasenia> validaciones = new ArrayList<>();

    public ValidadorDeContrasenia(ValidacionDeContrasenia... validaciones) {
        addValidaciones(validaciones);
    }

    public void addValidaciones(ValidacionDeContrasenia... nuevasValidaciones){
        Collections.addAll(this.validaciones, nuevasValidaciones);
    }

    public void validarContrasenia(Usuario usuario) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
        for(ValidacionDeContrasenia validador : validaciones){
            validador.validarConstrasenia(usuario);
        }
    }
}
