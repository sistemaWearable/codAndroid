package tcc.com.br.tcc.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
    int pegaIdAgenda(String nome, String palchave1, String palchave2);

    @Query("SELECT * FROM Agenda WHERE nome_agenda = :nome and palavra_chave1= :palchave1 and palavra_chave2= :palchave2")
    Agenda pegaAgendaNomePalChave(String nome, String palchave1, String palchave2);

    @Query("SELECT id_agenda FROM Agenda WHERE id_agenda = :id")
    int pegaAgendaPorID(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void salva(List<Agenda> agendasNovas);
}
