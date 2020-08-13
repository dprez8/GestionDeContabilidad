package Services;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

import Domain.ApiPaises.*;

public interface GeorefServices {
	@GET("classified_locations/countries")
	Call<List<Pais>> getPaises();
}
