package Services.ProcesoVinculacionServices;

import Domain.ApiVinculador.ConfigurarVinculadorRequest;
import Domain.ApiVinculador.VincularRequest;
import Domain.ApiVinculador.VincularResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioVinculacion {
    private static ServicioVinculacion instancia = null;
    private Retrofit retrofit;

    public static ServicioVinculacion instancia(String URL) {
        if(instancia == null) {
            instancia = new ServicioVinculacion(URL);
        }
        return instancia;
    }

    private ServicioVinculacion(String URL) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public VincularResponse vincular(VincularRequest vincularRequest) throws IOException {
        VinculacionServices vinculacionServices = this.retrofit.create(VinculacionServices.class);
        Call<VincularResponse> requestVinculacion = vinculacionServices.vincular(vincularRequest);
        Response<VincularResponse> responseVinculacion = requestVinculacion.execute();

        VincularResponse vincularResponse = responseVinculacion.body();

        return vincularResponse;
    }

    public void configurar(ConfigurarVinculadorRequest configurarVinculadorRequest) throws IOException {
        VinculacionServices vinculacionServices = this.retrofit.create(VinculacionServices.class);
        Call<Void> requestConfiguracion = vinculacionServices.configurar(configurarVinculadorRequest);
        requestConfiguracion.execute();
    }
}
