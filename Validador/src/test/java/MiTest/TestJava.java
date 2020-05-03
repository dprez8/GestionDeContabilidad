package MiTest;

import org.junit.Assert;
import org.junit.Test;

import Validador.ControladorUsuario;

import java.io.IOException;

public class TestJava {
    @Test
    public void testValidador() {

        String contrasenia = "fortune12";
        try {
            Assert.assertEquals(
                    "Hay que mejorar la contraseña",
                    "VALIDA",
                    ControladorUsuario.validarConstrasenia(contrasenia, "", "").toString());
        } catch (IOException e) {
            System.out.println("Oops!");
           // e.printStackTrace();
        }
    }
}
