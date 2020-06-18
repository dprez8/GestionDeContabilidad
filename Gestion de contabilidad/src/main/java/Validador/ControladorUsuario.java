package Validador;
import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;
import Usuarios.Usuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class ControladorUsuario {
    public void validarConstrasenia(String contrasenia, String mail, String usuarioNombre) throws repiteContraseniaEnMailOUsuario, contraseniaCorta, contraseniaMuyComun, IOException {
    	
    }
}
