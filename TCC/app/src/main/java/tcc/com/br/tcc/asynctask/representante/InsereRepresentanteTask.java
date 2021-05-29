package tcc.com.br.tcc.asynctask.representante;

import android.os.AsyncTask;

import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaRepAdapter;

public class InsereRepresentanteTask extends AsyncTask<Void, Void, Void> {

    private final RoomRepresentanteDAO dao;
    private final ListaRepAdapter adapter;
    private final Representante repRecebido;

    public InsereRepresentanteTask(RoomRepresentanteDAO dao, ListaRepAdapter adapter, Representante repRecebido) {

        this.dao = dao;
        this.adapter = adapter;
        this.repRecebido = repRecebido;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.insere(repRecebido);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.adiciona(dao.pegaRepNomeNumero(repRecebido.getNome_rep(), repRecebido.getNumero_rep()));
    }
}
