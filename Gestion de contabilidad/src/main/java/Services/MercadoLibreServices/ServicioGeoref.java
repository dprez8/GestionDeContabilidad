package Services.MercadoLibreServices;

import java.io.IOException;
import java.util.List;

import Domain.Entities.ApiPaises.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoref {
	private static ServicioGeoref instancia=null;
	private Retrofit retrofit;
	
	public static ServicioGeoref instancia(String URL){
	
		if(instancia==null){
			instancia= new ServicioGeoref(URL);
		}
		return instancia;
	}
	
	private ServicioGeoref(String URL){
		this.retrofit = new Retrofit.Builder().
				baseUrl(URL).
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
	
	public ListadoDeProvincias ListadoDeProvincias(Pais pais) throws IOException{
		GeorefServices georefservices=this.retrofit.create(GeorefServices.class);
		Call<ListadoDeProvincias> requestListadoProvincias = georefservices.getProvincias(pais.id);
		Response<ListadoDeProvincias> responseListadoDeProvincias = requestListadoProvincias.execute();
		ListadoDeProvincias provincias = responseListadoDeProvincias.body();
		return provincias;
	}
	
	public ListadoDeCiudades ListadoDeCiudades(Provincia provincia) throws IOException{
		GeorefServices georefservices=this.retrofit.create(GeorefServices.class);
		Call<ListadoDeCiudades> requestListadoCiudades = georefservices.getCiudades (provincia.id);
		Response<ListadoDeCiudades> responseListadoDeCiudades  = requestListadoCiudades.execute();
		ListadoDeCiudades ciudades = responseListadoDeCiudades.body();
		return ciudades;
	}
	
	
	public Moneda Moneda(Pais pais) throws IOException{
		GeorefServices georefservices=this.retrofit.create(GeorefServices.class);
		Call<Moneda> requestMoneda = georefservices.getMoneda(pais.currency_id);
		Response<Moneda> responseMoneda = requestMoneda.execute();
		Moneda moneda = responseMoneda.body();
		return moneda;
	}
	
}
