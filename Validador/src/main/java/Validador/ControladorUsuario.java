package Validador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ControladorUsuario {
    private static String rutaArchivo10KPeoresContras = "\\src\\main\\resources\\10k-worst-passwords.txt";
    private static int LONGITUDMINIMA = 8;
    public enum EstadoContrasenia{CORTA, MUYCOMUN, INVALIDA, VALIDA}

    public EstadoContrasenia validarConstrasenia(String contrasenia,String user,String mail) throws IOException{

        if(longitudCorta(contrasenia))
            return EstadoContrasenia.CORTA;
        if(estaEnElTopDiezK(contrasenia))
            return EstadoContrasenia.MUYCOMUN;
        if(esIgualAMailOUsuario(contrasenia,mail,user))
            return EstadoContrasenia.INVALIDA;
        return EstadoContrasenia.VALIDA;
    }
    protected boolean longitudCorta(String contrasenia) {
        return contrasenia.length() < LONGITUDMINIMA;
    }
    protected boolean estaEnElTopDiezK(String miContrasenia) throws IOException {
        String unaContrasenia;
        FileReader archivo = new FileReader(rutaArchivo10KPeoresContras); //crea la ruta al archivo
        BufferedReader entrada = new BufferedReader(archivo);             //lee la entrada de texto
        while((unaContrasenia = entrada.readLine()) != null){ //Vamos leyendo una linea del archivo. Verificar como funciona readLine()
            if(miContrasenia == unaContrasenia)
                return true;
        }
        return false;
    }
    protected boolean esIgualAMailOUsuario(String contrasenia,String user, String mail){
        return false;
    }
}
