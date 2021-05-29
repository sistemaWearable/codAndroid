package tcc.com.br.tcc.asynctask.representante;

import android.os.AsyncTask;

import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaRepAdapter;

public class AlteraRepresentanteTask extends AsyncTask <Void, Void, Void> {
    private final RoomRepresentanteDAO dao;
    private final ListaRepAdapter adapter;
    private final Representante representanteRecebido;
    private final int posicaoRecebida;

    public AlteraRepresentanteTask(RoomRepresentanteDAO dao, ListaRepAdapter adapter, Representante representanteRecebido, int posicaoRecebida) {

        this.dao = dao;
        this.adapter = adapter;
        this.representanteRecebido = representanteRecebido;
        this.posicaoRecebida = posicaoRecebida;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.altera(representanteRecebido);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.altera(posicaoRecebida, representanteRecebido);
    }
}
