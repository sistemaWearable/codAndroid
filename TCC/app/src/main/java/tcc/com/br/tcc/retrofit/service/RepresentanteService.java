package tcc.com.br.tcc.retrofit.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tcc.com.br.tcc.model.Representante;

public interface RepresentanteService {

    @GET("representante")
    Call<List<Representante>> buscaTodosRep();

    @POST("representante")
    Call<Representante> salva(@Body Representante representante);

    @PATCH("representante/{id_rep}")
    Call<Representante> edita(@Path("id_rep") int id_rep, @Body Representante representanteRecebido);

    @DELETE("representante/{id_rep}")
    Call<Void> remove(@Path("id_rep") int id_rep);
}
