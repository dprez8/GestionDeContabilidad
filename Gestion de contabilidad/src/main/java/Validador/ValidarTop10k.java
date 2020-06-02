package Validador;

import Exceptions.contraseniaMuyComun;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ValidarTop10k extends ControladorUsuario{
    private String rutaArchivo10KPeoresContras;

    public ValidarTop10k(String pathDeArchivo){
        rutaArchivo10KPeoresContras = pathDeArchivo;
    }

    public void validarConstrasenia(String contrasenia, String mail, String usuarioNombre) throws contraseniaMuyComun, IOException {
        if(estaEnElTopDiezK(contrasenia))
            throw new contraseniaMuyComun("La contrasenia es muy comun, dentro del top10k peores, pensar algo mas complejo");
    }

    public boolean estaEnElTopDiezK(String miContrasenia) throws IOException {
        String unaContrasenia;
        FileReader archivo = new FileReader(rutaArchivo10KPeoresContras); //crea la ruta al archivo
        BufferedReader entrada = new BufferedReader(archivo);             //lee la entrada de texto
        int verificador=0;

        while((unaContrasenia = entrada.readLine()) != null) { //Vamos leyendo una linea del archivo. Verificar como funciona readLine()
            if(miContrasenia.equals(unaContrasenia))
                verificador=1;
        }
        entrada.close();

        if(verificador==1) { //realicé esta verificacion para poder cerrar el Buffer.
            return true;
        }

        return false;
    }
}
