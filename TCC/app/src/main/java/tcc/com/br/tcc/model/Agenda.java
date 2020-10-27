package tcc.com.br.tcc.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Agenda implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id_agenda;
    private String nome_agenda;
    private String palavra_chave1;
    private String palavra_chave2;
    private String hora;
    private int domingo;
    private int segunda;
    private int terca;
    private int quarta;
    private int quinta;
    private int sexta;
    private int sabado;
    private int bloqueado;

    @Ignore
    public Agenda(String nome_agenda, String palavra_chave1, String palavra_chave2,
                  String hora, int domingo, int segunda, int terca, int quarta, int quinta, int sexta, int sabado, int bloqueado) {
        this.nome_agenda = nome_agenda;
        this.palavra_chave1 = palavra_chave1;
        this.palavra_chave2 = palavra_chave2;
        this.hora = hora;
        this.domingo = domingo;
        this.segunda = segunda;
        this.terca = terca;
        this.quarta = quarta;
        this.quinta = quinta;
        this.sexta = sexta;
        this.sabado = sabado;
        this.bloqueado = bloqueado;
    }

    public Agenda(){

    }

    public int getId_agenda() {
        return id_agenda;
    }

    public void setId_agenda(int id_agenda) {
        this.id_agenda = id_agenda;
    }

    public String getNome_agenda() {
        return nome_agenda;
    }

    public void setNome_agenda(String nome_agenda) {
        this.nome_agenda = nome_agenda;
    }

    public String getPalavra_chave1() {
        return palavra_chave1;
    }

    public void setPalavra_chave1(String palavra_chave1) {
        this.palavra_chave1 = palavra_chave1;
    }

    public String getPalavra_chave2() {
        return palavra_chave2;
    }

    public void setPalavra_chave2(String palavra_chave2) {
        this.palavra_chave2 = palavra_chave2;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getDomingo() {
        return domingo;
    }

    public void setDomingo(int domingo) {
        this.domingo = domingo;
    }

    public int getSegunda() {
        return segunda;
    }

    public void setSegunda(int segunda) {
        this.segunda = segunda;
    }

    public int getTerca() {
        return terca;
    }

    public void setTerca(int terca) {
        this.terca = terca;
    }

    public int getQuarta() {
        return quarta;
    }

    public void setQuarta(int quarta) {
        this.quarta = quarta;
    }

    public int getQuinta() {
        return quinta;
    }

    public void setQuinta(int quinta) {
        this.quinta = quinta;
    }

    public int getSexta() {
        return sexta;
    }

    public void setSexta(int sexta) {
        this.sexta = sexta;
    }

    public int getSabado() {
        return sabado;
    }

    public void setSabado(int sabado) {
        this.sabado = sabado;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }
}
