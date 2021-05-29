package tcc.com.br.tcc.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tcc.com.br.tcc.R;
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.retrofit.TccRetrofit;
import tcc.com.br.tcc.retrofit.service.RepresentanteService;
import tcc.com.br.tcc.ui.sms.ActEnviarMensagemSMS;
import tcc.com.br.tcc.workmanager.EstatisticasWorker;

public class MenuPrincipal extends AppCompatActivity {

    private TextView lblConectado;
    private boolean conexaoDispositivo;
    private ImageButton btnRepresentante;
    private ImageButton btnAgenda;
    private ImageButton btnGraficoBarras;
    RepresentanteService service = new TccRetrofit().getRepresentanteService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        setTitle("Menu Principal");
        //0chamaEnvioSMS();
        lblConectado = findViewById(R.id.menu_principal_lblConectado);
        btnRepresentante = findViewById(R.id.menu_principal_btnRepresentante);
        chamaActivityRepresentante();
        btnAgenda = findViewById(R.id.menu_principal_btnAgenda);
        chamaActivityAgenda();
        btnGraficoBarras = findViewById(R.id.menu_principal_imgGraficoBarras);
        lblConectado.setTextColor(Color.BLACK);
        lblConectado.setText("Conectando-se ao servidor");
        chamaActivityEstatisticas();
        verificaConexao();
    }

    private void chamaEnvioSMS() {
        PeriodicWorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(EstatisticasWorker.class, 2, TimeUnit.SECONDS)
                        // Constraints
                        .build();
        WorkManager.getInstance().enqueue(saveRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lblConectado.setTextColor(Color.BLACK);
        lblConectado.setText("Conectando-se ao servidor");
        verificaConexao();
    }

    private void preencheConexao() {
        if (conexaoDispositivo == true) {
            lblConectado.setTextColor(Color.parseColor("#228B22"));
            lblConectado.setText("Conectado no servidor");
        } else {
            lblConectado.setTextColor(Color.RED);
            lblConectado.setText("Falha ao conectar");
        }
    }

    private void verificaConexao() {
        Call<List<Representante>> call = service.buscaTodosRep();
        call.enqueue(new Callback<List<Representante>>() {
            @Override
            public void onResponse(Call<List<Representante>> call, Response<List<Representante>> response) {
                if (response.isSuccessful()) {
                        conexaoDispositivo = true;
                        preencheConexao();
                    }
                else {
                    conexaoDispositivo = false;
                    preencheConexao();
                }
            }
            @Override
            public void onFailure(Call<List<Representante>> call, Throwable t) {
                conexaoDispositivo = false;
                preencheConexao();
            }
        });
    }

    private void chamaActivityEstatisticas() {
        btnGraficoBarras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ActEstatisticas.class);
                startActivity(intent);
            }
        });
    }

    private void chamaActivityAgenda() {
        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ActAgenda.class);
                startActivity(intent);
            }
        });
    }

    private void chamaActivityRepresentante() {
        btnRepresentante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ActRepresentante.class);
                startActivity(intent);
            }
        });
    }
}