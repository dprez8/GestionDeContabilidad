package Validador;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ControladorUsuario {
    static int LONGITUDMINIMA = 8;
    public enum EstadoContrasenia{CORTA, MUYCOMUN, INSUFICIENTE, VALIDA}

    public EstadoContrasenia validarConstrasenia(String password,String user,String mail){

        if(longitudCorta(password))
            return CORTA;
        if(estaEnElTopDiezK(password))
            return MUYCOMUN;
    }
    protected boolean longitudCorta(String password) {
        return password.length() < LONGITUDMINIMA;
    }
    protected boolean estaEnElTopDiezK(String password){
        import
    }
}
