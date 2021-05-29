package tcc.com.br.tcc.asynctask.agenda;

import android.os.AsyncTask;

import java.io.IOException;

public class BaseAgendaAsyncTask<T> extends AsyncTask<Void, Void, T> {

    private final ExecutaListener<T> executaListener;
    private final FinalizadaListener<T> finalizadaListener;

    public BaseAgendaAsyncTask(ExecutaListener<T> executaListener,
                               FinalizadaListener<T> finalizadaListener) {
        this.executaListener = executaListener;
        this.finalizadaListener = finalizadaListener;
    }

    @Override
    protected T doInBackground(Void... voids) {
        try {
            return executaListener.quandoExecuta();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(T resultado) {
        super.onPostExecute(resultado);
        finalizadaListener.quandoFinalizada(resultado);
    }

    public interface ExecutaListener<T> {
        T quandoExecuta() throws IOException;
    }

    public interface FinalizadaListener<T> {
        void quandoFinalizada(T resultado);
    }

}