package tcc.com.br.tcc.asynctask.agenda;

import android.os.AsyncTask;

import tcc.com.br.tcc.database.dao.RoomAgendaDAO;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaAgendaAdapter;

public class RemoveAgendaTask extends AsyncTask<Void, Void, Void> {
    private final RoomAgendaDAO dao;
    private final ListaAgendaAdapter adapter;
    private final int id;
    private final int posicaoDeslizada;


    public RemoveAgendaTask(RoomAgendaDAO dao, ListaAgendaAdapter adapter, int id, int posicaoDeslizada) {

        this.dao = dao;
        this.adapter = adapter;
        this.id = id;
        this.posicaoDeslizada = posicaoDeslizada;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.remove(id);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.remove(posicaoDeslizada);
    }
}
