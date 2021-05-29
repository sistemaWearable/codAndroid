package tcc.com.br.tcc.asynctask.representante;

import android.os.AsyncTask;

import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaRepAdapter;

public class RemoveRepresentanteTask extends AsyncTask<Void, Void, Void> {
    private final RoomRepresentanteDAO dao;
    private final ListaRepAdapter adapter;
    private final int id;
    private final int posicaoDeslizada;


    public RemoveRepresentanteTask(RoomRepresentanteDAO dao, ListaRepAdapter adapter, int id, int posicaoDeslizada) {

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
