package tcc.com.br.tcc.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import tcc.com.br.tcc.R;
import tcc.com.br.tcc.workmanager.EstatisticasWorker;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validaPermissoesSMS();
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                chamaMenuPrincipal();
            }
        }, 2500);

    }

    private void chamaMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipal.class);


        startActivity(intent);

        finish();
    }

    private void validaPermissoesSMS() {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);//pede a permissão para SMS
    }
}
