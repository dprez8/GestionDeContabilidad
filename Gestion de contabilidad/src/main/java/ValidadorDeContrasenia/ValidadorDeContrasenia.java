package ValidadorDeContrasenia;

import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;

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

    public void validarContrasenia(String usuario, String mail, String contrasenia) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
        for(ValidacionDeContrasenia validador : validaciones){
            validador.validarConstrasenia(contrasenia, mail, usuario);
        }
    }
}
