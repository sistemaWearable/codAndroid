package tcc.com.br.tcc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import tcc.com.br.tcc.R;
import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaRepAdapter;
import tcc.com.br.tcc.ui.recyclerview.adapter.listener.OnItemClickListenerRepresentante;
import tcc.com.br.tcc.ui.recyclerview.helper.callback.RepresentanteItemTouchCallback;

public class ActRepresentante extends AppCompatActivity {

    private ListaRepAdapter adapter;
    private List<Representante> todosRepresentantes;
    private WearableDatabase database;//instancia do banco
    private RoomRepresentanteDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_representante);
        setTitle("Lista de Representantes");

        todosRepresentantes = buscaTodosRepresentantes();
        configuraRecyclerView(todosRepresentantes);
        chamaCadastroRepresentanteInsere();
    }

    private List<Representante> buscaTodosRepresentantes() {
        database = Room.databaseBuilder(this, WearableDatabase.class, "wearable.db").allowMainThreadQueries().build();
        dao = database.getRoomRepresentanteDAO();
        return dao.todos();
    }

    private void configuraRecyclerView(List<Representante> todosRepresentantes) {
        RecyclerView listaRepresentante = findViewById(R.id.lista_representante_recyclerview);
        configuraAdapter(todosRepresentantes, listaRepresentante);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RepresentanteItemTouchCallback(adapter, ActRepresentante.this));
        itemTouchHelper.attachToRecyclerView(listaRepresentante);
    }

    private void configuraAdapter(List<Representante> todosRepresentantes, RecyclerView listaRepresentante) {
        adapter = new ListaRepAdapter(this, todosRepresentantes);
        listaRepresentante.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaRepresentante.setLayoutManager(layoutManager);
        adapter.setOnItemClickListenerRepresentante(new OnItemClickListenerRepresentante() {
            @Override
            public void onItemClick(Representante representante, int posicao) {
                chamaCadastroRepresentanteAlteracao(representante, posicao);
            }
        });
    }

    private void chamaCadastroRepresentanteAlteracao(Representante representante, int posicao) {
        Intent intent = new Intent(ActRepresentante.this, CadastroRepresentante.class);
        intent.putExtra("rep", representante);
        intent.putExtra("posicao", posicao);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.hasExtra("rep")) {
            Representante repRecebido = (Representante) data.getSerializableExtra("rep");
            adapter.adiciona(repRecebido);
            dao.insere(repRecebido);
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.hasExtra("rep")) {
            Representante representanteRecebido = (Representante) data.getSerializableExtra("rep");
            int posicaoRecebida = data.getIntExtra("posicao", -1);
            if (posicaoRecebida > -1) {
                dao.altera(representanteRecebido);
                // new DAORepresentanteTeste().altera(posicaoRecebida, repRecebido);
                adapter.altera(posicaoRecebida, representanteRecebido);
            } else {
                Toast.makeText(ActRepresentante.this, "Ocorreu erro na alteração do Representante", Toast.LENGTH_LONG);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void chamaCadastroRepresentanteInsere() {
        FloatingActionButton fab = findViewById(R.id.lista_agenda_btnCadAgenda);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActRepresentante.this, CadastroRepresentante.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}