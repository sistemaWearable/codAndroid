package tcc.com.br.tcc.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Representante implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id_rep;
    private String nome_rep;
    private String numero_rep;

    @Ignore
    public Representante(String nome_rep, String numero_rep) {
        this.nome_rep = nome_rep;
        this.numero_rep = numero_rep;
    }


    public Representante(){

    }

    public Representante(int id_rep, String setNome_rep, String setNumero_rep) {
        this.id_rep = id_rep;
        this.nome_rep = nome_rep;
        this.numero_rep = numero_rep;
    }

    public int getId_rep() {
        return id_rep;
    }

    public void setId_rep(int id_rep) {
        this.id_rep = id_rep;
    }

    public String getNome_rep() {
        return nome_rep;
    }

    public void setNome_rep(String nome_rep) {
        this.nome_rep = nome_rep;
    }

    public String getNumero_rep() {
        return numero_rep;
    }

    public void setNumero_rep(String numero_rep) {
        this.numero_rep = numero_rep;
    }
}
