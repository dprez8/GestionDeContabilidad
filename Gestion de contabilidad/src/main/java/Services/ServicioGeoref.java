package Services;

import java.io.IOException;
import java.util.List;

import Domain.ApiPaises.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoref {
	private static ServicioGeoref instancia=null;
	private Retrofit retrofit;
	
	public static ServicioGeoref instancia(){
	
		if(instancia==null){
			instancia= new ServicioGeoref();
		}
		return instancia;
	}
	
	private ServicioGeoref(){
		this.retrofit = new Retrofit.Builder().
				baseUrl("https://api.mercadolibre.com/").
				addConverterFactory(GsonConverterFactory.
				create()).build();
	}
	
	public List<Pais> ListadoDePaises() throws IOException{
		GeorefServices georefservices=this.retrofit.create(GeorefServices.class);
		Call<List<Pais>> requestListadoPaises = georefservices.getPaises();
		Response<List<Pais>> responseListadoDePaises = requestListadoPaises.execute();
		List<Pais> paises = responseListadoDePaises.body();
		return paises;
	}
}
