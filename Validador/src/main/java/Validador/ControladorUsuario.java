package Validador;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ControladorUsuario {
    static String rutaArchivo10KPeoresContras = "\\src\\main\\resources\\10k-worst-passwords.txt";
    static int LONGITUDMINIMA = 8;
    public enum EstadoContrasenia{CORTA, MUYCOMUN, INVALIDA, VALIDA}

    public EstadoContrasenia validarConstrasenia(String password,String user,String mail){

        if(longitudCorta(password))
            return EstadoContrasenia.CORTA;
        if(estaEnElTopDiezK(password))
            return EstadoContrasenia.MUYCOMUN;
        if(esIgualAMailOUsuario(password,mail,user))
            return EstadoContrasenia.INVALIDA;
        return EstadoContrasenia.VALIDA;
    }
    protected boolean longitudCorta(String password) {
        return password.length() < LONGITUDMINIMA;
    }
    protected boolean estaEnElTopDiezK(String miPassword){
        String unPassword;
        FileReader archivo = new FileReader(rutaArchivo10KPeoresContras); //crea la ruta al archivo
        BufferedReader entrada = new BufferedReader(archivo);             //lee la entrada de texto
        while((unPassword = entrada.readLine()) != null){ //Vamos leyendo una linea del archivo. Verificar como funciona readLine()
            if(miPassword == unPassword)
                return true;
        }
        return false;
    }
    protected boolean esIgualAMailOUsuario(String password,String mail, String user){
        return false;
    }
}
