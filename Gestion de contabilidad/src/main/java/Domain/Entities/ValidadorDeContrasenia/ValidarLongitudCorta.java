package Domain.Entities.ValidadorDeContrasenia;

import Domain.Exceptions.contraseniaCorta;
import Domain.Entities.Usuarios.Usuario;

public class ValidarLongitudCorta extends ValidacionDeContrasenia {
    private int longitudMinima;

    public ValidarLongitudCorta(int unaLongitudMinima){
        longitudMinima = unaLongitudMinima;
    }

    @Override
    public void validarConstrasenia(Usuario usuario) throws contraseniaCorta {
        if(usuario.getContrasenia().length() < this.longitudMinima)
            throw new contraseniaCorta("La contrasenia ingresada es corta");
    }
}
