package tcc.com.br.tcc.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tcc.com.br.tcc.model.Estatistica;

@Dao
public interface RoomEstatisticaDAO {

    @Insert
    void insere(Estatistica estatistica);

    @Query("SELECT * FROM Estatistica WHERE cod_ocorrencia = 1 and data_ocorrencia = :data")
    List<Estatistica> todasOcorrencias1(String data);

    @Query("SELECT * FROM Estatistica WHERE cod_ocorrencia = 2 and data_ocorrencia = :data")
    List<Estatistica> todasOcorrencias2(String data);

    @Query("SELECT * FROM Estatistica WHERE cod_ocorrencia = 3 and data_ocorrencia = :data")
    List<Estatistica> todasOcorrencias3(String data);

    @Query("SELECT * FROM Estatistica WHERE cod_ocorrencia = 4 and data_ocorrencia = :data")
    List<Estatistica> todasOcorrencias4(String data);

}
