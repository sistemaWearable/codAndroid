package tcc.com.br.tcc.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

import tcc.com.br.tcc.R;
import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomEstatisticaDAO;
import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Estatistica;
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.ui.sms.ActEnviarMensagemSMS;

public class ActEstatisticas extends AppCompatActivity {

    private EditText campoPesquisa;
    private ImageButton btnPesquisa;
    private TextView txtOcorrencia1;
    private TextView txtOcorrencia2;
    private TextView txtOcorrencia3;
    private TextView txtOcorrencia4;
    private TextView txtResultado1;
    private TextView txtResultado2;
    private TextView txtResultado3;
    private TextView txtResultado4;
    private List<Estatistica> todasEstatisticas1;
    private List<Estatistica> todasEstatisticas2;
    private List<Estatistica> todasEstatisticas3;
    private List<Estatistica> todasEstatisticas4;
    private List<Representante> todosRep;
    private WearableDatabase database;
    private RoomEstatisticaDAO dao;
    private RoomRepresentanteDAO daoRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_estatisticas);
        iniciaComponentesBanco();
        insereEstatisticasDeTeste();
        inicializaCampos();
        pesquisaNoBancoAsEstatisticas();
    }

    private void pesquisaNoBancoAsEstatisticas() {
        btnPesquisa.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (ehDataValida(String.valueOf(campoPesquisa.getText())) == true) {
                    boolean resposta1 = buscaEstatisticas1(campoPesquisa.getText().toString());
                    boolean resposta2 = buscaEstatisticas2(campoPesquisa.getText().toString());
                    boolean resposta3 = buscaEstatisticas3(campoPesquisa.getText().toString());
                    boolean resposta4 = buscaEstatisticas4(campoPesquisa.getText().toString());
                    if (resposta1 == true || resposta2 == true || resposta3 == true || resposta4 == true) {
                        if (resposta1 == true) {
                            preencheEstatisitica(todasEstatisticas1, txtOcorrencia1, txtResultado1);
                            //o trecho abaixo é teste para envio de SMS
                            //new EnviarMensagemSMS().enviarMensagemSMS(ActEstatisticas.this, todosRep, "A pressão está correta");
                            Intent intent = new Intent(ActEstatisticas.this, ActEnviarMensagemSMS.class);
                            intent.putExtra("mensagem", "A pressão está correta");
                            startActivity(intent);
                        }
                        if (resposta2 == true){
                            preencheEstatisitica(todasEstatisticas2, txtOcorrencia2, txtResultado2);
                        }
                        if (resposta3 == true){
                            preencheEstatisitica(todasEstatisticas3, txtOcorrencia3, txtResultado3);
                        }
                        if (resposta4 == true){
                            preencheEstatisitica(todasEstatisticas4, txtOcorrencia4, txtResultado4);
                        }
                    } else {
                        Toast.makeText(ActEstatisticas.this,
                                "A data posicionada não possui registros",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ActEstatisticas.this,
                            "A data: " + campoPesquisa.getText() + " é invalida, será necessário digitar uma data correta ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void preencheEstatisitica(List<Estatistica> lista, TextView ocorrencia, TextView resultado){
        int valorOcorr1 = 0;
        int tamanhoOcorr1 = lista.size();
        ocorrencia.setText(lista.get(0).getNome_ocorrencia());
        for (Estatistica ocorrenciaSomada : lista) {
            int valor = Integer.parseInt(ocorrenciaSomada.getValor_ocorrencia());
            valorOcorr1 = valorOcorr1 + valor;
        }
        int resultadoOcorr1 = valorOcorr1 / tamanhoOcorr1;
        resultado.setText(String.valueOf(resultadoOcorr1));
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
        btnPesquisa = findViewById(R.id.tela_estatistica_btnPesquisar);

        txtOcorrencia1 = findViewById(R.id.tela_estatistica_txtOcorrencia1);
        txtOcorrencia2 = findViewById(R.id.tela_estatistica_txtOcorrencia2);
        txtOcorrencia3 = findViewById(R.id.tela_estatistica_txtOcorrencia3);
        txtOcorrencia4 = findViewById(R.id.tela_estatistica_txtOcorrencia4);

        txtResultado1 = findViewById(R.id.tela_estatistica_txtResultado1);
        txtResultado2 = findViewById(R.id.tela_estatistica_txtResultado2);
        txtResultado3 = findViewById(R.id.tela_estatistica_txtResultado3);
        txtResultado4 = findViewById(R.id.tela_estatistica_txtResultado4);
    }

    private boolean buscaEstatisticas1(String data) {
        todasEstatisticas1 = dao.todasOcorrencias1(data);
        if (todasEstatisticas1.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean buscaEstatisticas2(String data) {
        todasEstatisticas2 = dao.todasOcorrencias2(data);
        if (todasEstatisticas2.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean buscaEstatisticas3(String data) {
        todasEstatisticas3 = dao.todasOcorrencias3(data);
        if (todasEstatisticas3.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean buscaEstatisticas4(String data) {
        todasEstatisticas4 = dao.todasOcorrencias4(data);
        if (todasEstatisticas4.isEmpty()) {
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

    private void insereEstatisticasDeTeste() {
//        dao.insere(new Estatistica(1,"Pressão Arterial", "11/10/2020", "133"));
//        dao.insere(new Estatistica(1,"Pressão Arterial", "11/10/2020", "112"));
//        dao.insere(new Estatistica(1,"Pressão Arterial", "11/10/2020", "100"));
//        dao.insere(new Estatistica(2,"Temperatura", "12/10/2020", "120"));
//        dao.insere(new Estatistica(2,"Temperatura", "11/10/2020", "147"));
//        dao.insere(new Estatistica(2,"Temperatura", "12/10/2020", "100"));
//        dao.insere(new Estatistica(3,"Estatistica 3", "13/10/2020", "123"));
//        dao.insere(new Estatistica(3,"Estatistica 3", "11/10/2020", "321"));
//        dao.insere(new Estatistica(3,"Estatistica 3", "12/10/2020", "222"));
//        dao.insere(new Estatistica(4,"Estatistica 4", "12/10/2020", "111"));
//        dao.insere(new Estatistica(4,"Estatistica 4", "12/10/2020", "123"));
//        dao.insere(new Estatistica(4,"Estatistica 4", "12/10/2020", "100"));
    }
}