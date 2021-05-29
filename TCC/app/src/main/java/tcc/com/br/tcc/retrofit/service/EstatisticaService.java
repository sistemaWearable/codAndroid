package tcc.com.br.tcc.retrofit.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tcc.com.br.tcc.model.Estatistica;

public interface EstatisticaService {

    @GET("estatisticas")
    Call<List<Estatistica>> buscaTodasEstatisticas();

    @GET("estatisticas?limit=10")
    Call<List<Estatistica>> buscaUltimasEstatisticas();
}
