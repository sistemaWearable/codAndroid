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
import tcc.com.br.tcc.asynctask.agenda.AlteraAgendaTask;
import tcc.com.br.tcc.asynctask.agenda.BaseAgendaAsyncTask;
import tcc.com.br.tcc.asynctask.agenda.InsereAgendaTask;
import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomAgendaDAO;
import tcc.com.br.tcc.model.Agenda;
import tcc.com.br.tcc.retrofit.TccRetrofit;
import tcc.com.br.tcc.retrofit.service.AgendaService;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaAgendaAdapter;
import tcc.com.br.tcc.ui.recyclerview.adapter.listener.OnItemClickListenerAgenda;
import tcc.com.br.tcc.ui.recyclerview.helper.callback.AgendaItemTouchCallback;

public class ActAgenda extends AppCompatActivity {

    private ListaAgendaAdapter adapter;
    private List<Agenda> todasAgenda;
    private WearableDatabase database;//instancia do banco
    private RoomAgendaDAO dao;
    AgendaService service = new TccRetrofit().getAgendaService();
    private ActRepresentante.DadosCarregadosCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agenda);
        setTitle("Lista de Tarefas");

        todasAgenda = buscaTodasAgendas();
        configuraRecyclerView(todasAgenda);
        chamaCadastroAgendaInsere();
    }

    private List<Agenda> buscaTodasAgendas() {
        database = Room.databaseBuilder(this, WearableDatabase.class, "wearable.db").allowMainThreadQueries().build();
        dao = database.getRoomAgendaDAO();
        Call<List<Agenda>> call = service.buscaTodasAgendas();
        new BaseAgendaAsyncTask<>(() -> {
            try {
                Response<List<Agenda>> resposta = call.execute();
                List<Agenda> agendasNovas = resposta.body();
                dao.salva(agendasNovas);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dao.todos();
        }, agendasNovas -> {
            if (agendasNovas != null) {
                adapter.atualiza(agendasNovas);
            } else {
                Toast.makeText(ActAgenda.this,
                        "Não foi possível buscar as agendas da API",
                        Toast.LENGTH_SHORT).show();
            }
        }).execute();
        return dao.todos();
    }

    private void configuraRecyclerView(List<Agenda> todasAgenda) {
        RecyclerView listaAgenda = findViewById(R.id.lista_agenda_recyclerview);
        configuraAdapter(todasAgenda, listaAgenda);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new AgendaItemTouchCallback(adapter, ActAgenda.this));
        itemTouchHelper.attachToRecyclerView(listaAgenda);
    }

    private void configuraAdapter(List<Agenda> todasAgenda, RecyclerView listaAgenda) {
        adapter = new ListaAgendaAdapter(this, todasAgenda);
        listaAgenda.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaAgenda.setLayoutManager(layoutManager);
        adapter.setOnItemClickListenerAgenda(new OnItemClickListenerAgenda() {
            @Override
            public void onItemClick(Agenda agenda, int posicao) {
                chamaCadastroAgendaAlteracao(agenda, posicao);
            }
        });
    }

    private void chamaCadastroAgendaAlteracao(Agenda agenda, int posicao) {
        Intent intent = new Intent(ActAgenda.this, CadastroAgenda.class);
        intent.putExtra("agenda", agenda);
        intent.putExtra("posicao", posicao);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.hasExtra("agenda")) {
            Agenda agendaRecebida = (Agenda) data.getSerializableExtra("agenda");
            Call<Agenda> call = service.salva(agendaRecebida);
            call.enqueue(new Callback<Agenda>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<Agenda> call, Response<Agenda> response) {
                    if (response.isSuccessful()) {
                        Agenda agendaNovo = response.body();
                        if (agendaNovo != null) {
                            new InsereAgendaTask(dao, adapter, agendaNovo).execute();
                        }
                    } else {
                        callback.quandoFalha("Resposta não sucedida");
                    }
                }

                @Override
                public void onFailure(Call<Agenda> call, Throwable t) {
                    callback.quandoFalha("Falha de Comunicação: " + t.getMessage());
                }
            });
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data.hasExtra("agenda")) {
            Agenda agendaRecebida = (Agenda) data.getSerializableExtra("agenda");
            int posicaoRecebida = data.getIntExtra("posicao", -1);
            if (posicaoRecebida > -1) {
                dao.altera(agendaRecebida);
                // new DAOAgendaTeste().altera(posicaoRecebida, agendaRecebida);
                adapter.altera(posicaoRecebida, agendaRecebida);
                Call<Agenda> call = service.edita(agendaRecebida.getId_agenda(), agendaRecebida);
                call.enqueue(new Callback<Agenda>() {
                    @Override
                    public void onResponse(Call<Agenda> call, Response<Agenda> response) {
                        if (response.isSuccessful()) {
                            Agenda agendaNovo = response.body();
                            if (agendaNovo != null) {
                                new AlteraAgendaTask(dao, adapter, agendaRecebida, posicaoRecebida).execute();
                            }
                        } else {
                            callback.quandoFalha("Resposta não sucedida");
                        }
                    }

                    @Override
                    public void onFailure(Call<Agenda> call, Throwable t) {
                        callback.quandoFalha("Falha de Comunicação: " + t.getMessage());
                    }
                });
            } else {
                Toast.makeText(ActAgenda.this, "Ocorreu erro na alteração do Representante", Toast.LENGTH_LONG);
            }

        } else {
            Toast.makeText(ActAgenda.this, "Ocorreu erro na alteração da Agenda", Toast.LENGTH_LONG);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void chamaCadastroAgendaInsere() {
        FloatingActionButton fab = findViewById(R.id.lista_agenda_btnCadAgenda);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActAgenda.this, CadastroAgenda.class);
                startActivityForResult(intent,1);
            }
        });
    }
}