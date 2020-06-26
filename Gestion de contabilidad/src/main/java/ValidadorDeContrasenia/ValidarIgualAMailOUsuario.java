package ValidadorDeContrasenia;

import Exceptions.repiteContraseniaEnMailOUsuario;

public class ValidarIgualAMailOUsuario extends ValidacionDeContrasenia {
    public void validarConstrasenia(String contrasenia, String mail, String usuarioNombre) throws repiteContraseniaEnMailOUsuario {
        if(contrasenia.equals(mail)) //rehacer este para que solo tome lo anterior al @ del mail para comparar
            throw new repiteContraseniaEnMailOUsuario("La contraseña es igual al mail");
        if(contrasenia.equals(usuarioNombre))
            throw new repiteContraseniaEnMailOUsuario("La contraseña es igual al usuario");
    }
}