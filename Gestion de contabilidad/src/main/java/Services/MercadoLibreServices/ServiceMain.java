package Services.MercadoLibreServices;

import retrofit2.http.GET;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServiceMain {

	public static void main(String[] args) throws IOException {
		//LLAMO AL ARCHIVO DE CONFIG PARA OBTENER LA URL A LA API DE ML
		Properties prop=new Properties();
		prop.load(new FileReader("src/main/resources/config/config.properties"));
		
		EleccionApi.instancia();
		
		//ESTA SE UTILIZA PARA QUE EL USUARIO ELIJA LOS DATOS PERO TODO DESDE LA API(COMO LO TENIAMOS ANTES)
		//EleccionApi.BajarDatosApiMercadoLibrePorId(prop);
		
		//ESTA SE UTILIZA PARA BAJAR TODOS LOS DATOS Y GUARDARLOS EN LA BASE DE DATOS
		EleccionApi.BajarDatosApiMercadoLibreABaseDeDatos(prop);
	}
}
