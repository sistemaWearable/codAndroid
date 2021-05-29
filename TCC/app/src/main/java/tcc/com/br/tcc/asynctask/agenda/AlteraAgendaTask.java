package tcc.com.br.tcc.asynctask.agenda;

import android.os.AsyncTask;

import tcc.com.br.tcc.database.dao.RoomAgendaDAO;
import tcc.com.br.tcc.model.Agenda;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaAgendaAdapter;

public class AlteraAgendaTask extends AsyncTask <Void, Void, Void> {
    private final RoomAgendaDAO dao;
    private final ListaAgendaAdapter adapter;
    private final Agenda agendaRecebido;
    private final int posicaoRecebida;

    public AlteraAgendaTask(RoomAgendaDAO dao, ListaAgendaAdapter adapter, Agenda agendaRecebido, int posicaoRecebida) {

        this.dao = dao;
        this.adapter = adapter;
        this.agendaRecebido = agendaRecebido;
        this.posicaoRecebida = posicaoRecebida;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.altera(agendaRecebido);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.altera(posicaoRecebida, agendaRecebido);
    }
}
