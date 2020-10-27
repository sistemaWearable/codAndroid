package tcc.com.br.tcc.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tcc.com.br.tcc.model.Agenda;

@Dao
public interface RoomAgendaDAO {

    @Insert
    void insere(Agenda agenda);

    @Query("SELECT * FROM Agenda")
    List<Agenda> todos();

    @Query("DELETE FROM Agenda WHERE id_agenda = :id")
    void remove(int id);

    @Update
    void altera(Agenda agendaRecebida);

    @Query("SELECT id_agenda FROM Agenda WHERE nome_agenda = :nome and palavra_chave1= :palchave1 and palavra_chave2= :palchave2")
    int pegaIdRep(String nome, String palchave1, String palchave2);
}
