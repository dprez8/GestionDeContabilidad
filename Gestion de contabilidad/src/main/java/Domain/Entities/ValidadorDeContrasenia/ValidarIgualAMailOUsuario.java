package Domain.Entities.ValidadorDeContrasenia;

import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Entities.Usuarios.Usuario;

public class ValidarIgualAMailOUsuario extends ValidacionDeContrasenia {

    @Override
    public boolean validarConstrasenia(Usuario usuario) throws repiteContraseniaEnMailOUsuario {
        if(usuario.getContrasenia().equals(usuario.getMail())) //rehacer este para que solo tome lo anterior al @ del mail para comparar
            throw new repiteContraseniaEnMailOUsuario("La contraseña es igual al mail");
        if(usuario.getContrasenia().equals(usuario.getNombre()))
            throw new repiteContraseniaEnMailOUsuario("La contraseña es igual al nombre del usuario");
        
        return true;
    }
}