package tcc.com.br.tcc.asynctask.agenda;

import android.os.AsyncTask;

import java.util.List;


import tcc.com.br.tcc.database.dao.RoomAgendaDAO;
import tcc.com.br.tcc.model.Agenda;

public class BuscaAgendaTask extends AsyncTask<Void, Void,List<Agenda>> {

    private RoomAgendaDAO dao;

    private List<Agenda> todasAgenda;

    public BuscaAgendaTask(RoomAgendaDAO dao) {
        this.dao = dao;
    }

    @Override
    protected List<Agenda> doInBackground(Void[] objects) {
        todasAgenda = dao.todos();
        return todasAgenda;
    }

    @Override
    protected void onPostExecute(List<Agenda> Agendas) {
        super.onPostExecute(Agendas);
    }

}
