package tcc.com.br.tcc.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import tcc.com.br.tcc.model.Estatistica;
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.retrofit.TccRetrofit;
import tcc.com.br.tcc.retrofit.service.EstatisticaService;
import tcc.com.br.tcc.retrofit.service.RepresentanteService;
import tcc.com.br.tcc.ui.ActRepresentante;
import tcc.com.br.tcc.ui.sms.ActEnviarMensagemSMS;

public class EstatisticasWorker extends Worker {

    EstatisticaService service = new TccRetrofit().getEstatisticaService();
    private ActRepresentante.DadosCarregadosCallback callback;
    private List<Representante> todosRepresentantes;
    RepresentanteService serviceRep = new TccRetrofit().getRepresentanteService();

    public EstatisticasWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private boolean pegaEstatisticas(){
        Call<List<Estatistica>> call = service.buscaUltimasEstatisticas();
        try {
            Response<List<Estatistica>> resposta = call.execute();
            if(resposta.isSuccessful()) {
                List<Estatistica> body = resposta.body();
                for (Estatistica est:
                     body) {
                    int valorConvertido = Integer.parseInt(est.getValor_ocorrencia());
                    if(est.getCod_ocorrencia() == 1 && (valorConvertido > 80 || valorConvertido < 55)) {
                        new ActEnviarMensagemSMS().enviarMensagemSMS("CUIDADO, o batimento cardiaco está em: "
                                +est.getValor_ocorrencia()+" bpm", pegaTodosRep());
                    }
                    else if(est.getCod_ocorrencia() == 2 && (valorConvertido > 37 || valorConvertido < 35)){
                        new ActEnviarMensagemSMS().enviarMensagemSMS("CUIDADO, a temperatura está em: "
                                +est.getValor_ocorrencia()+" graus", pegaTodosRep());
                    }
                }
                return true;
            }


        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    private List<Representante> pegaTodosRep(){
        Call<List<Representante>> call = serviceRep.buscaTodosRep();
        try{
            Response<List<Representante>> respostaRep = call.execute();
            if(respostaRep.isSuccessful()){

                return respostaRep.body();
            }
        }catch (IOException e){
            e.printStackTrace();

        }
        return null;
    }

    @NonNull
    @Override
    public Result doWork() {
        if(pegaEstatisticas() == true) {
            Log.e("est", "success");
            return Result.success();

        }
        else{
            Log.e("est", "failed");
            return Result.failure();
        }
    }
}