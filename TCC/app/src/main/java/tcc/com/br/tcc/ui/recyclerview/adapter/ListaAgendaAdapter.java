package tcc.com.br.tcc.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tcc.com.br.tcc.R;
import tcc.com.br.tcc.model.Agenda;
import tcc.com.br.tcc.ui.recyclerview.adapter.listener.OnItemClickListenerAgenda;

public class ListaAgendaAdapter extends RecyclerView.Adapter<ListaAgendaAdapter.AgendaViewHolder> {

    private Context context;
    private final List<Agenda> agendas;
    private OnItemClickListenerAgenda onItemClickListenerAgenda;

    public ListaAgendaAdapter(Context context, List<Agenda> agenda) {
        this.context = context;
        this.agendas = agenda;
    }

    @NonNull
    @Override
    public ListaAgendaAdapter.AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_agenda, parent, false);
        return new AgendaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAgendaAdapter.AgendaViewHolder holder, int position) {
        Agenda agenda = agendas.get(position);
        holder.vinculaAgendaNaView(agenda);
    }

    public int getItemIdAgenda(int posicao){
        int id = agendas.get(posicao).getId_agenda();
        return id;
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }



    public void setOnItemClickListenerAgenda(OnItemClickListenerAgenda onItemClickListenerAgenda) {
        this.onItemClickListenerAgenda = onItemClickListenerAgenda;
    }

    public void altera(int posicao, Agenda agenda) {
        agendas.set(posicao, agenda);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        agendas.remove(posicao);
        notifyItemRemoved(posicao);
    }


    class AgendaViewHolder extends RecyclerView.ViewHolder{

        private final TextView nome;
        private final TextView hora;
        private Agenda agenda;

        public AgendaViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.item_agenda_nome);
            hora = itemView.findViewById(R.id.item_agenda_hora);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListenerAgenda.onItemClick(agenda, getAdapterPosition());
                }
            });
        }

        public void vinculaAgendaNaView(Agenda agenda){
            this.agenda = agenda;
            nome.setText(agenda.getNome_agenda());
            hora.setText(agenda.getHora());
        }
    }

    public void adiciona(Agenda agenda){
        agendas.add(agenda);
        notifyDataSetChanged();
    }
}
