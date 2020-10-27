package tcc.com.br.tcc.ui.recyclerview.helper.callback;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomAgendaDAO;
import tcc.com.br.tcc.ui.recyclerview.adapter.ListaAgendaAdapter;

public class AgendaItemTouchCallback extends ItemTouchHelper.Callback {

    private final ListaAgendaAdapter adapter;
    private final Context context;
    private WearableDatabase database;//instancia do banco
    private RoomAgendaDAO dao;

    public AgendaItemTouchCallback(ListaAgendaAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, marcacoesDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //esse fica em branco, pois é obrigatório da classe pai, porém não vou usar
        //serve pra animação vertical
        return false;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
        database = Room.databaseBuilder(context, WearableDatabase.class, "wearable.db").allowMainThreadQueries().build();
        dao = database.getRoomAgendaDAO();
        new AlertDialog.Builder(context).setTitle("Removendo Tarefa")
                .setMessage("Tem certeza que pretende continuar ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int posicaoDeslizada = viewHolder.getAdapterPosition();
                        int id = adapter.getItemIdAgenda(posicaoDeslizada);
                        dao.remove(id);
                       // new DAOAgendaTeste().remove(posicaoDeslizada);
                        adapter.remove(posicaoDeslizada);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                }).show();
    }
}
