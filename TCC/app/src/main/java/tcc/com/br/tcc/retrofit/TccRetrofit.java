package tcc.com.br.tcc.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tcc.com.br.tcc.retrofit.service.AgendaService;
import tcc.com.br.tcc.retrofit.service.EstatisticaService;
import tcc.com.br.tcc.retrofit.service.RepresentanteService;

public class TccRetrofit {

    public static final String URL_BASE = "http://192.168.0.106:4000/";
    private final RepresentanteService representanteService;
    private final AgendaService agendaService;
    private final EstatisticaService estatisticaService;

    public TccRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_BASE).
                addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
        representanteService = retrofit.create(RepresentanteService.class);
        agendaService = retrofit.create(AgendaService.class);
        estatisticaService = retrofit.create(EstatisticaService.class);

    }

    public RepresentanteService getRepresentanteService() {
        return representanteService;
    }

    public AgendaService getAgendaService() { return agendaService;  }

    public EstatisticaService getEstatisticaService(){return estatisticaService; }

}
