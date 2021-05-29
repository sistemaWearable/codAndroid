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

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tcc.com.br.tcc.R;
import tcc.com.br.tcc.asynctask.representante.AlteraRepresentanteTask;
import tcc.com.br.tcc.asynctask.representante.BaseRepresentanteAsyncTask;
import tcc.com.br.tcc.asynctask.representante.InsereRepresentanteTask;
import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.retrofit.TccRetrofit;
import tcc.com.br.tcc.retrofit.service.RepresentanteService;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaRepAdapter;
import tcc.com.br.tcc.ui.recyclerview.adapter.listener.OnItemClickListenerRepresentante;
import tcc.com.br.tcc.ui.recyclerview.helper.callback.RepresentanteItemTouchCallback;

public class ActRepresentante extends AppCompatActivity {

    private ListaRepAdapter adapter;
    private List<Representante> todosRepresentantes;
    private WearableDatabase database;//instancia do banco
    private RoomRepresentanteDAO dao;
    RepresentanteService service = new TccRetrofit().getRepresentanteService();
    private DadosCarregadosCallback callback;

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
        Call<List<Representante>> call = service.buscaTodosRep();
        new BaseRepresentanteAsyncTask<>(() -> {
            try {
                Response<List<Representante>> resposta = call.execute();
                List<Representante> repNovos = resposta.body();
                dao.salva(repNovos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dao.todos();
        }, repNovos -> {
            if (repNovos != null) {
                adapter.atualiza(repNovos);
            } else {
                Toast.makeText(ActRepresentante.this,
                        "Não foi possível buscar os representante da API",
                        Toast.LENGTH_SHORT).show();
            }
        }).execute();
        return dao.todos();
    }

    public void configuraRecyclerView(List<Representante> todosRepresentantes) {
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
            Call<Representante> call = service.salva(repRecebido);
            call.enqueue(new Callback<Representante>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<Representante> call, Response<Representante> response) {
                    if (response.isSuccessful()) {
                        Representante repNovo = response.body();
                        if (repNovo != null) {
                          new InsereRepresentanteTask(dao, adapter, repNovo).execute();
                        }
                    }else{
                        callback.quandoFalha("Resposta não sucedida");
                    }
                }

                @Override
                public void onFailure(Call<Representante> call, Throwable t) {
                        callback.quandoFalha("Falha de Comunicação: "+ t.getMessage());
                }
            });
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.hasExtra("rep")) {
            Representante representanteRecebido = (Representante) data.getSerializableExtra("rep");
            int posicaoRecebida = data.getIntExtra("posicao", -1);
            if (posicaoRecebida > -1) {
                Call<Representante> call = service.edita(representanteRecebido.getId_rep(),representanteRecebido);
                call.enqueue(new Callback<Representante>() {
                    @Override
                    public void onResponse(Call<Representante> call, Response<Representante> response) {
                        if (response.isSuccessful()) {
                            Representante repNovo = response.body();
                            if (repNovo != null) {
                                new AlteraRepresentanteTask(dao, adapter, representanteRecebido, posicaoRecebida).execute();
                            }
                        }else{
                            callback.quandoFalha("Resposta não sucedida");
                        }
                    }

                    @Override
                    public void onFailure(Call<Representante> call, Throwable t) {
                        callback.quandoFalha("Falha de Comunicação: "+ t.getMessage());
                    }
                });
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

    public interface DadosCarregadosCallback<T>{
        void quandoFalha(String erro);
    }
}