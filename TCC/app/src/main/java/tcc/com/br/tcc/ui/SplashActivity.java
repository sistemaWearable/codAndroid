package tcc.com.br.tcc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.aware.SubscribeConfig;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.Task;

import tcc.com.br.tcc.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                mostraLogin();
            }
        }, 2500);
    }

    private void mostraLogin() {
        if(googlePlayServiceAceitavel(this) == 0){
        Intent intent = new Intent(this, MenuPrincipal.class);
        startActivity(intent);
        finish();
        }
        else{
            Toast.makeText(this,
                    "O seu Google Play Service está desatualizado, por favor atualize para usar esse aplicativo", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public int googlePlayServiceAceitavel(Context context){
        int resultado = new GoogleApiAvailability().isGooglePlayServicesAvailable(context);
        return resultado;
    }
}