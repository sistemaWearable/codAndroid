package tcc.com.br.tcc.asynctask.representante;

import android.os.AsyncTask;

import java.util.List;

import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Representante;

public class BuscaRepresentanteTask extends AsyncTask<Void, Void, List<Representante>> {

    private RoomRepresentanteDAO dao;

    private List<Representante> todosRep;

    public BuscaRepresentanteTask(RoomRepresentanteDAO dao) {
        this.dao = dao;
    }

    @Override
    protected List<Representante> doInBackground(Void[] objects) {
        todosRep = dao.todos();
        return todosRep;
    }

    @Override
    protected void onPostExecute(List<Representante> representantes) {
        super.onPostExecute(representantes);
    }
}
