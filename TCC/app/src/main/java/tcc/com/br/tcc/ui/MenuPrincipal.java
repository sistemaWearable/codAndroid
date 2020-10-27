package tcc.com.br.tcc.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import tcc.com.br.tcc.R;

public class MenuPrincipal extends AppCompatActivity {

    private TextView lblConectado;
    private boolean conexaoDispositivo = false;
    private ImageButton btnRepresentante;
    private ImageButton btnAgenda;
    private ImageButton btnGraficoBarras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        setTitle("Menu Principal");
        lblConectado = findViewById(R.id.menu_principal_lblConectado);
        if(conexaoDispositivo == true){
            lblConectado.setTextColor(Color.parseColor("#228B22"));
            lblConectado.setText("Há um dispositivo conectado");
        }
        else{
            lblConectado.setTextColor(Color.RED);
            lblConectado.setText("Nenhum dispositivo conectado");
        }



        btnRepresentante = findViewById(R.id.menu_principal_btnRepresentante);
        chamaActivityRepresentante();
        btnAgenda = findViewById(R.id.menu_principal_btnAgenda);
        chamaActivityAgenda();
        btnGraficoBarras = findViewById(R.id.menu_principal_imgGraficoBarras);
        chamaActivityEstatisticas();
    }

    private void chamaActivityEstatisticas() {
        btnGraficoBarras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ActEstatisticas.class);
                startActivity(intent);
            }
        });
    }

    private void chamaActivityAgenda() {
        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ActAgenda.class);
                startActivity(intent);
            }
        });
    }

    private void chamaActivityRepresentante() {
        btnRepresentante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ActRepresentante.class);
                startActivity(intent);
            }
        });
    }
}