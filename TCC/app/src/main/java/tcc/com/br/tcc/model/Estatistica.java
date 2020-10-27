package tcc.com.br.tcc.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Estatistica implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id_est;
    private int cod_ocorrencia;
    private String nome_ocorrencia;
    private String data_ocorrencia;
    private String valor_ocorrencia;

    @Ignore
    public Estatistica(int cod_ocorrencia, String nome_ocorrencia, String data_ocorrencia, String valor_ocorrencia){
        this.cod_ocorrencia = cod_ocorrencia;
        this.nome_ocorrencia = nome_ocorrencia;
        this.data_ocorrencia = data_ocorrencia;
        this.valor_ocorrencia = valor_ocorrencia;
    }

    public Estatistica(){

    }

    public int getId_est() {
        return id_est;
    }

    public void setId_est(int id_est) {
        this.id_est = id_est;
    }

    public int getCod_ocorrencia() {
        return cod_ocorrencia;
    }

    public void setCod_ocorrencia(int cod_ocorrencia) {
        this.cod_ocorrencia = cod_ocorrencia;
    }

    public String getNome_ocorrencia() {
        return nome_ocorrencia;
    }

    public void setNome_ocorrencia(String nome_ocorrencia) {
        this.nome_ocorrencia = nome_ocorrencia;
    }

    public String getData_ocorrencia() {
        return data_ocorrencia;
    }

    public void setData_ocorrencia(String data_ocorrencia) {
        this.data_ocorrencia = data_ocorrencia;
    }

    public String getValor_ocorrencia() {
        return valor_ocorrencia;
    }

    public void setValor_ocorrencia(String valor_ocorrencia) {
        this.valor_ocorrencia = valor_ocorrencia;
    }
}
