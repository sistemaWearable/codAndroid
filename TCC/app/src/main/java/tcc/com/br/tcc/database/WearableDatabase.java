package tcc.com.br.tcc.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import tcc.com.br.tcc.database.dao.RoomAgendaDAO;
import tcc.com.br.tcc.database.dao.RoomEstatisticaDAO;
import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Agenda;
import tcc.com.br.tcc.model.Estatistica;
import tcc.com.br.tcc.model.Representante;

@Database(entities = {Agenda.class, Representante.class, Estatistica.class}, version = 2, exportSchema = false)
public abstract class WearableDatabase extends RoomDatabase {

    public abstract RoomAgendaDAO getRoomAgendaDAO();

    public abstract RoomRepresentanteDAO getRoomRepresentanteDAO();

    public abstract RoomEstatisticaDAO getRoomEstatisticaDAO();

}
