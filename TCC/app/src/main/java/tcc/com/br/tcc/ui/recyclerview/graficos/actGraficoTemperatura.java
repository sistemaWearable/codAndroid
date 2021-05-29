package tcc.com.br.tcc.ui.recyclerview.graficos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import tcc.com.br.tcc.R;
import tcc.com.br.tcc.model.Estatistica;

public class actGraficoTemperatura extends AppCompatActivity {

    private BarChart barChart;
    private Intent dadosRecebidos;
    private List<Estatistica> todasEstatisticas;
    private List<Estatistica> estHora0 = new ArrayList<>();;
    private List<Estatistica> estHora2 = new ArrayList<>();;
    private List<Estatistica> estHora4 = new ArrayList<>();;
    private List<Estatistica> estHora6 = new ArrayList<>();;
    private List<Estatistica> estHora8 = new ArrayList<>();;
    private List<Estatistica> estHora10 = new ArrayList<>();;
    private List<Estatistica> estHora12 = new ArrayList<>();;
    private List<Estatistica> estHora14 = new ArrayList<>();;
    private List<Estatistica> estHora16 = new ArrayList<>();;
    private List<Estatistica> estHora18 = new ArrayList<>();;
    private List<Estatistica> estHora20 = new ArrayList<>();;
    private List<Estatistica> estHora22 = new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_grafico_temperatura);
        setTitle("Grafico de Temperatura em Cº");
        barChart = findViewById(R.id.chartGraficoTemp);
        dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra("est1")){
            todasEstatisticas = (List<Estatistica>) dadosRecebidos.getSerializableExtra("est1");
            preencheValoresLista(todasEstatisticas);
            preencheGrafico();
        }
        else{
            Toast.makeText(actGraficoTemperatura.this, "Nenhum dado foi recebido, por favor reinicie o aplicativo e tente novamente",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void preencheValoresLista(List<Estatistica> todasEstatisticas) {
        for (Estatistica listaSeparadaHora: todasEstatisticas) {
            String hora = listaSeparadaHora.getData_ocorrencia().substring(11,13);
            if(hora.equals("00")|| hora.equals("01")){
                estHora0.add(listaSeparadaHora);
            }
            else if(hora.equals("02")|| hora.equals("03")){
                estHora2.add(listaSeparadaHora);
            }
            else if(hora.equals("04")|| hora.equals("05")){
                estHora4.add(listaSeparadaHora);
            }
            else if(hora.equals("06")|| hora.equals("07")){
                estHora6.add(listaSeparadaHora);
            }
            else if(hora.equals("08")|| hora.equals("09")){
                estHora8.add(listaSeparadaHora);
            }
            else if(hora.equals("10")|| hora.equals("11")){
                estHora10.add(listaSeparadaHora);
            }
            else if(hora.equals("12")|| hora.equals("13")){
                estHora12.add(listaSeparadaHora);
            }
            else if(hora.equals("14")|| hora.equals("15")){
                estHora14.add(listaSeparadaHora);
            }
            else if(hora.equals("16")|| hora.equals("17")){
                estHora16.add(listaSeparadaHora);
            }
            else if(hora.equals("18")|| hora.equals("19")){
                estHora18.add(listaSeparadaHora);
            }
            else if(hora.equals("20")|| hora.equals("21")){
                estHora20.add(listaSeparadaHora);
            }
            else if(hora.equals("22")|| hora.equals("23")){
                estHora22.add(listaSeparadaHora);
            }
        }
    }

    public int preencheEstatisitica(List<Estatistica> lista){

        int valorOcorr = 0;
        int tamanhoOcorr = lista.size();
        int resultadoOcorr=0;
        for (Estatistica ocorrenciaSomada : lista) {
            int valor = Integer.parseInt(ocorrenciaSomada.getValor_ocorrencia());
            valorOcorr = valorOcorr + valor;
        }
        if(valorOcorr>0){
            resultadoOcorr = valorOcorr / tamanhoOcorr;
        }
        else{
            resultadoOcorr = 0;
        }
        return  resultadoOcorr;
    }

    private void preencheGrafico() {
        int valorhora0 = preencheEstatisitica(estHora0);
        int valorhora2 = preencheEstatisitica(estHora2);
        int valorhora4 = preencheEstatisitica(estHora4);
        int valorhora6 = preencheEstatisitica(estHora6);
        int valorhora8 = preencheEstatisitica(estHora8);
        int valorhora10 = preencheEstatisitica(estHora10);
        int valorhora12 = preencheEstatisitica(estHora12);
        int valorhora14 = preencheEstatisitica(estHora14);
        int valorhora16 = preencheEstatisitica(estHora16);
        int valorhora18 = preencheEstatisitica(estHora18);
        int valorhora20 = preencheEstatisitica(estHora20);
        int valorhora22 = preencheEstatisitica(estHora22);

        ArrayList<BarEntry> temperatura = new ArrayList<>();
        temperatura.add(new BarEntry(0, valorhora0));
        temperatura.add(new BarEntry(2, valorhora2));
        temperatura.add(new BarEntry(4, valorhora4));
        temperatura.add(new BarEntry(6, valorhora6));
        temperatura.add(new BarEntry(8, valorhora8));
        temperatura.add(new BarEntry(10, valorhora10));
        temperatura.add(new BarEntry(12, valorhora12));
        temperatura.add(new BarEntry(14, valorhora14));
        temperatura.add(new BarEntry(16, valorhora16));
        temperatura.add(new BarEntry(18, valorhora18));
        temperatura.add(new BarEntry(20, valorhora20));
        temperatura.add(new BarEntry(22, valorhora22));

        BarDataSet barDataSet = new BarDataSet(temperatura,"Temperatura");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(11f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(1500);
    }
}