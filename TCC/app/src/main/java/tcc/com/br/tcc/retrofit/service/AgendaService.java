package tcc.com.br.tcc.retrofit.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tcc.com.br.tcc.model.Agenda;

public interface AgendaService {

    @GET("agenda")
    Call<List<Agenda>> buscaTodasAgendas();

    @POST("agenda")
    Call<Agenda> salva(@Body Agenda agenda);

    @PATCH("agenda/{id_agenda}")
    Call<Agenda> edita(@Path("id_agenda") int id_agenda, @Body Agenda agendaRecebida);

    @DELETE("agenda/{id_agenda}")
    Call<Void> remove(@Path("id_agenda") int id_agenda);
}
