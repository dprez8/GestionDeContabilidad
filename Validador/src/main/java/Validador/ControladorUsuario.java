package Validador;
import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ControladorUsuario {
    private static String rutaArchivo10KPeoresContras = "src/main/resources/10k-worst-passwords.txt";
    private static int LONGITUDMINIMA = 8;

    /**
     *
     * @param contrasenia
     * @param user
     * @param mail
     * @return
     * @throws IOException
     */
    static public void validarConstrasenia(String contrasenia, String user, String mail) throws IOException, contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario {

        if(longitudCorta(contrasenia))
            throw new contraseniaCorta();
        if(estaEnElTopDiezK(contrasenia))
            throw new contraseniaMuyComun();
        if(esIgualAMailOUsuario(contrasenia, mail, user))
            throw new repiteContraseniaEnMailOUsuario();
    }
    static protected boolean longitudCorta(String contrasenia) {
        return contrasenia.length() < LONGITUDMINIMA;
    }
    static protected boolean estaEnElTopDiezK(String miContrasenia) throws IOException {
        String unaContrasenia;
        FileReader archivo = new FileReader(rutaArchivo10KPeoresContras); //crea la ruta al archivo
        BufferedReader entrada = new BufferedReader(archivo);             //lee la entrada de texto
        while((unaContrasenia = entrada.readLine()) != null) { //Vamos leyendo una linea del archivo. Verificar como funciona readLine()
            if(miContrasenia.equals(unaContrasenia))
                return true;
        }
        return false;
    }
    static protected boolean esIgualAMailOUsuario(String contrasenia,String user, String mail){
        return false;
    }
}
