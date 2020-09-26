package Services.ProcesoVinculacionServices;

import Domain.ApiVinculador.ConfigurarVinculadorRequest;
import Domain.ApiVinculador.VincularRequest;
import Domain.ApiVinculador.VincularResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VinculacionServices {
    @POST("api/vincular")
    Call<VincularResponse> vincular(@Body VincularRequest vincularRequest);

    @POST("api/conficurar")
    Call<Void> configurar(@Body ConfigurarVinculadorRequest configurarVinculadorRequest);
}
