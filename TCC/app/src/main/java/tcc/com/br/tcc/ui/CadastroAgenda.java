package tcc.com.br.tcc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import tcc.com.br.tcc.R;
import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomAgendaDAO;
import tcc.com.br.tcc.model.Agenda;

public class CadastroAgenda extends AppCompatActivity {

    private int posicaoRecebida = -1; //posicao invalida, evitando bug
    private EditText txtNome;
    private EditText txtPalChave1;
    private EditText txtPalChave2;
    private EditText txtHora;
    private EditText txtMinuto;
    private CheckBox chkDomingo;
    private CheckBox chkSegunda;
    private CheckBox chkTerca;
    private CheckBox chkQuarta;
    private CheckBox chkQuinta;
    private CheckBox chkSexta;
    private CheckBox chkSabado;
    private RadioButton rdbBloqueado;
    private RadioButton rdbNaoBloq;
    private WearableDatabase database;//instancia do banco
    private RoomAgendaDAO dao;
    private Intent dadosRecebidos;
    private Agenda agendaRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_agenda);
        setTitle("Cadastro de Tarefas");
        inicializaCampos();
        dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra("agenda")) {
            database = Room.databaseBuilder(this, WearableDatabase.class, "wearable.db").allowMainThreadQueries().build();
            dao = database.getRoomAgendaDAO();
            setTitle("Alterar Tarefa");
            agendaRecebida = (Agenda) dadosRecebidos.getSerializableExtra("agenda");
            int idRecebido = dao.pegaIdRep(agendaRecebida.getNome_agenda(),
                    agendaRecebida.getPalavra_chave1(), agendaRecebida.getPalavra_chave2());
            agendaRecebida.setId_agenda(idRecebido);
            posicaoRecebida = dadosRecebidos.getIntExtra("posicao", -1);
            preencheCampos(agendaRecebida);
        }
    }

    private void preencheCampos(Agenda agendaRecebida) {
        //preenche os campos recebidos na alteração
        txtNome.setText(agendaRecebida.getNome_agenda());
        txtPalChave1.setText(agendaRecebida.getPalavra_chave1());
        txtPalChave2.setText(agendaRecebida.getPalavra_chave2());
        txtHora.setText(agendaRecebida.getHora().substring(0, 2));
        txtMinuto.setText(agendaRecebida.getHora().substring(3, 5));
        //preenche checkbox conforme o posicionado
        preencheCheckBox(chkDomingo, agendaRecebida.getDomingo());
        preencheCheckBox(chkSegunda, agendaRecebida.getSegunda());
        preencheCheckBox(chkTerca, agendaRecebida.getTerca());
        preencheCheckBox(chkQuarta, agendaRecebida.getQuarta());
        preencheCheckBox(chkQuinta, agendaRecebida.getQuinta());
        preencheCheckBox(chkSexta, agendaRecebida.getSexta());
        preencheCheckBox(chkSabado, agendaRecebida.getSabado());
        //preenche radiobutton posicionado
        preencheRadioButton(rdbBloqueado, rdbNaoBloq, agendaRecebida.getBloqueado());
    }

    private void inicializaCampos() {
        txtNome = findViewById(R.id.cadastro_agenda_txtNome);
        txtPalChave1 = findViewById(R.id.cadastro_agenda_txtPalChave1);
        txtPalChave2 = findViewById(R.id.cadastro_agenda_txtPalChave2);
        txtHora = findViewById(R.id.cadastro_agenda_txtHora);
        txtMinuto = findViewById(R.id.cadastro_agenda_txtMinuto);
        chkDomingo = findViewById(R.id.cadastro_agenda_chkDomingo);
        chkSegunda = findViewById(R.id.cadastro_agenda_chkSegunda);
        chkTerca = findViewById(R.id.cadastro_agenda_chkTerca);
        chkQuarta = findViewById(R.id.cadastro_agenda_chkQuarta);
        chkQuinta = findViewById(R.id.cadastro_agenda_chkQuinta);
        chkSexta = findViewById(R.id.cadastro_agenda_chkSexta);
        chkSabado = findViewById(R.id.cadastro_agenda_chkSabado);
        rdbBloqueado = findViewById(R.id.cadastro_agenda_rdbBloqueado);
        rdbNaoBloq = findViewById(R.id.cadastro_agenda_rdbNaoBloq);
    }

    public void preencheCheckBox(CheckBox checkBox, int flag) {
        if (flag == 0) {
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
        }
    }

    public void preencheRadioButton(RadioButton rdbBloq, RadioButton rdbNaoBloq, int flag) {
        if (flag == 1) {
            rdbBloq.setChecked(true);
            rdbNaoBloq.setChecked(false);
        } else {
            rdbBloq.setChecked(false);
            rdbNaoBloq.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agenda_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_agenda_btnSalvar) {
            insereAgenda();
        }
        return super.onOptionsItemSelected(item);
    }

    private void insereAgenda() {
        //pega os valores dos campos na tela
        String txtHoraFinal = txtHora.getText().toString() + ":" + txtMinuto.getText().toString();
        int flagDomingo = verificaCheckBox(chkDomingo);
        int flagSegunda = verificaCheckBox(chkSegunda);
        int flagTerca = verificaCheckBox(chkTerca);
        int flagQuarta = verificaCheckBox(chkQuarta);
        int flagQuinta = verificaCheckBox(chkQuinta);
        int flagSexta = verificaCheckBox(chkSexta);
        int flagSabado = verificaCheckBox(chkSabado);
        int flagBloqueado = verificaRadioButton(rdbBloqueado, rdbNaoBloq);

        //valida campos obrigatórios
        if (validaPreenchimento(txtNome) || validaPreenchimento(txtPalChave1) || validaPreenchimento(txtPalChave2)
                || validaPreenchimento(txtHora) || validaPreenchimento(txtMinuto)) {
            Toast.makeText(this, "Preencha todos os campos para salvar", Toast.LENGTH_LONG).show();
        } else {
            if (validaHora(txtHora.getText().toString(), txtMinuto.getText().toString()) == true) {
                if(dadosRecebidos.hasExtra("agenda")){
                    agendaRecebida.setNome_agenda(txtNome.getText().toString());
                    agendaRecebida.setPalavra_chave1(txtPalChave1.getText().toString());
                    agendaRecebida.setPalavra_chave2(txtPalChave2.getText().toString());
                    agendaRecebida.setHora(txtHoraFinal);
                    agendaRecebida.setDomingo(flagDomingo);
                    agendaRecebida.setSegunda(flagSegunda);
                    agendaRecebida.setTerca(flagTerca);
                    agendaRecebida.setQuarta(flagQuarta);
                    agendaRecebida.setQuinta(flagQuinta);
                    agendaRecebida.setSexta(flagSexta);
                    agendaRecebida.setSabado(flagSabado);
                    agendaRecebida.setBloqueado(flagBloqueado);
                    Intent resultadoInsercao = new Intent();
                    resultadoInsercao.putExtra("agenda", agendaRecebida);
                    resultadoInsercao.putExtra("posicao", posicaoRecebida);
                    setResult(Activity.RESULT_OK, resultadoInsercao);
                    finish();
                }
                else{
                    //Adiciona no model
                    Agenda agenda = new Agenda(txtNome.getText().toString(),
                            txtPalChave1.getText().toString(),
                            txtPalChave2.getText().toString(),
                            txtHoraFinal,
                            flagDomingo,
                            flagSegunda,
                            flagTerca,
                            flagQuarta,
                            flagQuinta,
                            flagSexta,
                            flagSabado,
                            flagBloqueado);

                    //Adiciona no banco
                    Intent resultadoInsercao = new Intent();
                    resultadoInsercao.putExtra("agenda", agenda);
                    resultadoInsercao.putExtra("posicao", posicaoRecebida);
                    setResult(Activity.RESULT_OK, resultadoInsercao);
                    finish();
                }
            } else {
                Toast.makeText(this, "A hora está com conteúdo inválido, por favor informar horário correto",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean validaHora(String hora, String minuto) {
        int horaConvertida = Integer.parseInt(hora);
        int minutoConvertido = Integer.parseInt(minuto);
        if (horaConvertida <= 24 && horaConvertida >= 00 && minutoConvertido >= 00 && minutoConvertido <= 59) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validaPreenchimento(EditText texto) {
        if (texto.getText().toString().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int verificaCheckBox(CheckBox dia) {
        if (dia.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int verificaRadioButton(RadioButton rdbBloq, RadioButton rdbNaoBloq) {
        if (rdbBloq.isChecked() && !rdbNaoBloq.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }
}