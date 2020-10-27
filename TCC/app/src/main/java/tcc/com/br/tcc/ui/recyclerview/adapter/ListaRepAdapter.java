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
import tcc.com.br.tcc.model.Representante;
import tcc.com.br.tcc.ui.recyclerview.adapter.listener.OnItemClickListenerRepresentante;

public class ListaRepAdapter extends RecyclerView.Adapter<ListaRepAdapter.RepresentanteViewHolder> {

    private final List<Representante> representantes;
    Context context;
    private OnItemClickListenerRepresentante onItemClickListenerRepresentante;

    public ListaRepAdapter(Context context, List<Representante> rep){
        this.representantes = rep;
        this.context = context;
    }

    @NonNull
    @Override
    public ListaRepAdapter.RepresentanteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_representante, parent, false);
        return new RepresentanteViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaRepAdapter.RepresentanteViewHolder holder, int position) {
        Representante representante = representantes.get(position);
        holder.vinculaRepNaView(representante);
    }

    public int getItemIdRep(int posicao){
        int id = representantes.get(posicao).getId_rep();
        return id;
    }

    @Override
    public int getItemCount() {
        return representantes.size();
    }

    public void setOnItemClickListenerRepresentante(OnItemClickListenerRepresentante onItemClickListenerRepresentante) {
        this.onItemClickListenerRepresentante = onItemClickListenerRepresentante;
    }

    public void altera(int posicao, Representante rep) {
        representantes.set(posicao, rep);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        representantes.remove(posicao);
        notifyItemRemoved(posicao);
    }


    class RepresentanteViewHolder extends RecyclerView.ViewHolder{

        private final TextView nome;
        private final TextView numero;
        private Representante representante;

        public RepresentanteViewHolder(@NonNull final View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.item_agenda_nome);
            numero = itemView.findViewById(R.id.item_representante_numero);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListenerRepresentante.onItemClick(representante, getAdapterPosition());
                }
            });
        }

        public void vinculaRepNaView(Representante rep){
            this.representante = rep;
            nome.setText(rep.getNome_rep());
            numero.setText(rep.getNumero_rep());
        }

    }

    public void adiciona(Representante rep){
        representantes.add(rep);
        notifyDataSetChanged();
    }
}
