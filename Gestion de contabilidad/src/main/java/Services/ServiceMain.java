package Services;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

import Domain.ApiPaises.*;

public class ServiceMain {

	public static void main(String[] args) throws IOException {
		//LLAMO AL ARCHIVO DE CONFIG PARA OBTENER LA URL A LA API DE ML
		Properties prop=new Properties();
		prop.load(new FileReader("src/main/resources/config.properties"));
		
		EleccionApi.instancia();
		EleccionApi.BajarDatosApiMercadoLibre(prop);
	}
}
