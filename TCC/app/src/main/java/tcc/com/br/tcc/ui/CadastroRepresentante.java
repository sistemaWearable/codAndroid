package tcc.com.br.tcc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import tcc.com.br.tcc.R;
import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Representante;

public class CadastroRepresentante extends AppCompatActivity {

    private int posicaoRecebida = -1; //posicao invalida, evitando bug
    private EditText txtNome;
    private EditText txtNumero;
    private Representante representanteRecebido;
    private Intent dadosRecebidos;
    private WearableDatabase database;//instancia do banco
    private RoomRepresentanteDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_representante);
        setTitle("Cadastro de Representante");
        inicializaCampos();
        dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra("rep")) {
            database = Room.databaseBuilder(this, WearableDatabase.class, "wearable.db").allowMainThreadQueries().build();
            dao = database.getRoomRepresentanteDAO();
            setTitle("Alterar Representante");
            representanteRecebido = (Representante) dadosRecebidos.getSerializableExtra("rep");
            int idRecebido = dao.pegaIdRep(representanteRecebido.getNome_rep(), representanteRecebido.getNumero_rep());
            representanteRecebido.setId_rep(idRecebido); //necessário para resolver um problema, onde após cadastrar um representante, não era possível alterar
            posicaoRecebida = dadosRecebidos.getIntExtra("posicao", -1);
            preencheCampos(representanteRecebido);
        }
    }

    private void inicializaCampos() {
        txtNome = findViewById(R.id.cadastro_representante_txtNome);
        txtNumero = findViewById(R.id.cadastro_representante_txtNumero);
    }

    private void preencheCampos(Representante representanteRecebido) {
        txtNome.setText(representanteRecebido.getNome_rep());
        txtNumero.setText(representanteRecebido.getNumero_rep());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_representante_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_representante_btnSalvar) {
            insereRepresentante();
        }
        return super.onOptionsItemSelected(item);
    }

    private void insereRepresentante() {

        //valida se os campos estão preenchidos
        if (txtNome.getText().toString().length() == 0 || txtNumero.getText().toString().length() == 0) {
            Toast.makeText(this, "Preencha todos os campos para salvar", Toast.LENGTH_LONG).show();
        } else {
            if (dadosRecebidos.hasExtra("rep")) {// verifica se é alteração
                representanteRecebido.setNome_rep(txtNome.getText().toString());
                representanteRecebido.setNumero_rep(txtNumero.getText().toString());
                Intent resultadoInsercao = new Intent();
                resultadoInsercao.putExtra("rep", representanteRecebido);
                resultadoInsercao.putExtra("posicao", posicaoRecebida);
                setResult(Activity.RESULT_OK, resultadoInsercao);
                finish();
            } else {
                Representante rep = new Representante( txtNome.getText().toString(),
                        txtNumero.getText().toString());
                Intent resultadoInsercao = new Intent();
                resultadoInsercao.putExtra("rep", rep);
                resultadoInsercao.putExtra("posicao", posicaoRecebida);
                setResult(Activity.RESULT_OK, resultadoInsercao);
                finish();
            }
        }
    }

}