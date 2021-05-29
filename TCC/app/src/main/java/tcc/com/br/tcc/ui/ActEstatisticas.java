package tcc.com.br.tcc.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tcc.com.br.tcc.R;
import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomEstatisticaDAO;
import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Estatistica;
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.retrofit.TccRetrofit;
import tcc.com.br.tcc.retrofit.service.EstatisticaService;
import tcc.com.br.tcc.ui.recyclerview.graficos.actGraficoBatimento;
import tcc.com.br.tcc.ui.recyclerview.graficos.actGraficoTemperatura;

public class ActEstatisticas extends AppCompatActivity {

    private EditText campoPesquisa;
    private ImageButton btnCalendario;
    private Button btnGraficoTemp;
    private Button btnGraficoBatimento;
    private List<Estatistica> todasEstatisticas1 = new ArrayList<>();
    private List<Estatistica> todasEstatisticas;
    private List<Estatistica> todasEstatisticas2 = new ArrayList<>();
    private List<Representante> todosRep;
    private WearableDatabase database;
    private RoomEstatisticaDAO dao;
    private RoomRepresentanteDAO daoRep;
    EstatisticaService service = new TccRetrofit().getEstatisticaService();
    private ActRepresentante.DadosCarregadosCallback callback;
    private Calendar calendar;
    private DatePickerDialog datePickerSelecionarDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_estatisticas);
        iniciaComponentesBanco();
        insereEstatisticas();
        inicializaCampos();
        clickBotaoDePreencherData();
        chamaGraficoTemp();
        chamaGraficoBatimento();
    }

    private void chamaGraficoBatimento() {
        btnGraficoBatimento.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(ehDataValida(campoPesquisa.getText().toString())) {
                    boolean resposta2 = buscaEstatisticas2(DataFormatada(campoPesquisa.getText().toString()));
                    if(resposta2==true){
                    Intent intent = new Intent(ActEstatisticas.this, actGraficoBatimento.class);
                    intent.putExtra("est2", (Serializable) todasEstatisticas2);
                    startActivity(intent);
                    }
                    else{
                        Toast.makeText(ActEstatisticas.this, "A data posicionada não possui nenhuma estatistica, escolha uma data com dados",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(ActEstatisticas.this, "O campo data está em branco ou preenchido errado, preencha-o corretamente para continuar",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void chamaGraficoTemp() {
        btnGraficoTemp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(ehDataValida(campoPesquisa.getText().toString())) {
                    boolean resposta1 = buscaEstatisticas1(DataFormatada(campoPesquisa.getText().toString()));
                    if(resposta1==true){
                    Intent intent = new Intent(ActEstatisticas.this, actGraficoTemperatura.class);
                    intent.putExtra("est1", (Serializable) todasEstatisticas1);
                    startActivity(intent);
                    }
                    else{
                        Toast.makeText(ActEstatisticas.this, "A data posicionada não possui nenhuma estatistica, escolha uma data com dados",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(ActEstatisticas.this, "O campo data está em branco ou preenchido errado, preencha-o corretamente para continuar",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clickBotaoDePreencherData() {
        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                preencheData();
            }
        });
    }

    private void preencheData(){
        calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        datePickerSelecionarDia = new DatePickerDialog(ActEstatisticas.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mAno, int mMes, int mDia) {
                String dataMontada = "";
                mMes++;//tratamento pois o mes está vindo sempre com um a menos, provavel que seja algo da biblioteca padrão
                if(mMes<10 && mDia<10){
                    dataMontada = "0"+ mDia + "/0" + mMes + "/" + mAno;
                    campoPesquisa.setText(dataMontada);
                }
                else if(mMes<10 && mDia>10){
                    dataMontada = mDia + "/0" + mMes + "/" + mAno;
                    campoPesquisa.setText(dataMontada);
                }
                else if(mMes>10 && mDia<10){
                    dataMontada = "0"+ mDia + "/" + mMes + "/" + mAno;
                    campoPesquisa.setText(dataMontada);
                }
                else{
                    dataMontada = mDia + "/" + mMes + "/" + mAno;
                    campoPesquisa.setText(dataMontada);
                }
            }
        },ano, mes, dia);
        datePickerSelecionarDia.show();
    }

    public String DataFormatada(String data){
        String dataModificada = data.replaceAll("/","-");
        return dataModificada;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean ehDataValida(String data) {
        String formatoData = "dd/MM/uuuu";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(formatoData)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(data, dateTimeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void inicializaCampos() {
        campoPesquisa = findViewById(R.id.tela_estatistica_txtDataPesquisa);
        btnCalendario = findViewById(R.id.tela_estatistica_btnCalendario);
        btnGraficoTemp = findViewById(R.id.tela_estatistica_btnTemperatura);
        btnGraficoBatimento = findViewById(R.id.tela_estatistica_btnBatimento);
    }

    private boolean buscaEstatisticas1(String data) {
       // todasEstatisticas1 = dao.todasOcorrencias1(data);
        todasEstatisticas1.clear();
        for (Estatistica est :
                todasEstatisticas) {
            String newDate = est.getData_ocorrencia().substring(0,10).replaceAll("^(\\d{4})-(\\d{2})-(\\d{2})$", "$3-$2-$1");

            if(est.getCod_ocorrencia() == 1 && newDate.equals(data)){
               todasEstatisticas1.add(est);
            }
        }
        if (todasEstatisticas1.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
    }

    private boolean buscaEstatisticas2(String data) {
       // todasEstatisticas2 = dao.todasOcorrencias2(data);
        todasEstatisticas2.clear();
        for (Estatistica est :
                todasEstatisticas) {
            String newDate = est.getData_ocorrencia().substring(0,10).replaceAll("^(\\d{4})-(\\d{2})-(\\d{2})$", "$3-$2-$1");
            if(est.getCod_ocorrencia() == 2 && newDate.equals(data)){
                todasEstatisticas2.add(est);
            }
        }
        if (todasEstatisticas2.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
    }

    private void iniciaComponentesBanco() {
        database = Room.databaseBuilder(this, WearableDatabase.class, "wearable.db").allowMainThreadQueries().build();
        dao = database.getRoomEstatisticaDAO();
        daoRep = database.getRoomRepresentanteDAO();
        todosRep = daoRep.todos();
    }

    private void insereEstatisticas() {
        Call<List<Estatistica>> call = service.buscaTodasEstatisticas();
        call.enqueue(new Callback<List<Estatistica>>() {
            @Override
            public void onResponse(Call<List<Estatistica>> call, Response<List<Estatistica>> response) {
                if(response.isSuccessful()){
                    List<Estatistica> body = response.body();
                    todasEstatisticas = body;
                }
                else{
                    Toast.makeText(ActEstatisticas.this, "Não teve sucesso no retorno", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Estatistica>> call, Throwable t) {
                Toast.makeText(ActEstatisticas.this, "Não teve sucesso na conexão com a API", Toast.LENGTH_LONG).show();
            }
        });
    }
}