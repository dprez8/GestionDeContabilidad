package Services.ProcesoVinculacionServices;

import Domain.Entities.ApiVinculador.ConfigurarVinculadorRequest;
import Domain.Entities.ApiVinculador.VincularRequest;
import Domain.Entities.ApiVinculador.VincularResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VinculacionServices {
    @POST("api/vincular")
    Call<VincularResponse> vincular(@Body VincularRequest vincularRequest);

    @POST("api/configurar")
    Call<Void> configurar(@Body ConfigurarVinculadorRequest configurarVinculadorRequest);
}
