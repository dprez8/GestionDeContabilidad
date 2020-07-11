package Domain.ValidadorDeContrasenia;

import Domain.Exceptions.contraseniaCorta;

public class ValidarLongitudCorta extends ValidacionDeContrasenia {
    private int longitudMinima;

    public ValidarLongitudCorta(int unaLongitudMinima){
        longitudMinima = unaLongitudMinima;
    }

    public void validarConstrasenia(String contrasenia, String mail, String usuarioNombre) throws contraseniaCorta {
        if(contrasenia.length() < this.longitudMinima)
            throw new contraseniaCorta("La contrasenia ingresada es corta");
    }
}
