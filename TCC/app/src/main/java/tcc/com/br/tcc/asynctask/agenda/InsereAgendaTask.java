package tcc.com.br.tcc.asynctask.agenda;

import android.os.AsyncTask;

import tcc.com.br.tcc.database.dao.RoomAgendaDAO;
import tcc.com.br.tcc.model.Agenda;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaAgendaAdapter;

public class InsereAgendaTask extends AsyncTask<Void, Void, Void> {

    private final RoomAgendaDAO dao;
    private final ListaAgendaAdapter adapter;
    private final Agenda agendaRecebida;

    public InsereAgendaTask(RoomAgendaDAO dao, ListaAgendaAdapter adapter, Agenda agendaRecebida) {

        this.dao = dao;
        this.adapter = adapter;
        this.agendaRecebida = agendaRecebida;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.insere(agendaRecebida);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.adiciona(dao.pegaAgendaNomePalChave(agendaRecebida.getNome_agenda(),agendaRecebida.getPalavra_chave1(), agendaRecebida.getPalavra_chave2()));
    }
}

