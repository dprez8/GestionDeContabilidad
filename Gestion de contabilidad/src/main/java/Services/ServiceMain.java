package Services;

import java.io.IOException;
import java.util.List;

import Domain.ApiPaises.*;

public class ServiceMain {

	public static void main(String[] args) throws IOException {
		
		ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
		System.out.println("Seleccione el pais: ");
		List<Pais> paisesList = servicioGeoref.ListadoDePaises();
		for(Pais pais:paisesList){
			System.out.println(pais.id +" "+ pais.name +" "+pais.currency_id);
		} 
	}

}
