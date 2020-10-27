package tcc.com.br.tcc.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tcc.com.br.tcc.model.Representante;

@Dao
public interface RoomRepresentanteDAO {

    @Insert
    void insere(Representante representante);

    @Query("SELECT * FROM Representante")
    List<Representante> todos();

    @Query("DELETE FROM Representante WHERE id_rep = :id")
    void remove(int id);

    @Update
    void altera(Representante representante);

    @Query("SELECT id_rep FROM Representante WHERE nome_rep = :nome and numero_rep = :numero")
    int pegaIdRep(String nome, String numero);

}
