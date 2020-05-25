package Validador;
import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ControladorUsuario {
    private static String rutaArchivo10KPeoresContras;
    private static int LONGITUDMINIMA;

    public ControladorUsuario(String _rutaArchivo10KPeoresContras, int longitudMininaContrasenia){
        rutaArchivo10KPeoresContras = _rutaArchivo10KPeoresContras;
        LONGITUDMINIMA = longitudMininaContrasenia;
    }

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
            throw new contraseniaCorta("La contrasenia ingresada es corta");
        if(estaEnElTopDiezK(contrasenia))
            throw new contraseniaMuyComun("La contrasenia es muy comun, dentro del top10k peores, pensar algo mas complejo");
        if(esIgualAMailOUsuario(contrasenia, mail, user))
            throw new repiteContraseniaEnMailOUsuario("Repite contraseña en mail o en el usuario");
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
