package Services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

import Domain.ApiPaises.*;

public interface GeorefServices {
	@GET("classified_locations/countries")
	Call<List<Pais>> getPaises();
	
	@GET("classified_locations/countries/{id}")
	Call<ListadoDeProvincias> getProvincias(@Path("id") String id);
	
	@GET("classified_locations/states/{id}")
	Call<ListadoDeCiudades> getCiudades(@Path("id") String id);
	
	@GET("currencies/{id}")
	Call<Moneda> getMoneda(@Path("id") String id);
}
