package tcc.com.br.tcc.ui.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;

import java.util.List;

import tcc.com.br.tcc.R;
import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Representante;

public class ActEnviarMensagemSMS extends AppCompatActivity {

    private Intent dadosRecebidos;
    private WearableDatabase database;
    private RoomRepresentanteDAO daoRep;
    private List<Representante> todosRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_enviar_mensagem_s_m_s);
        dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra("mensagem")){
            iniciaComponentesBanco();
            String mensagem = (String) dadosRecebidos.getSerializableExtra("mensagem");
            enviarMensagemSMS(mensagem);
        }
    }

    private void iniciaComponentesBanco() {
        database = Room.databaseBuilder(this, WearableDatabase.class, "wearable.db").allowMainThreadQueries().build();
        daoRep = database.getRoomRepresentanteDAO();
        todosRep = daoRep.todos();
    }

//    public void enviarMensagemSMS(Context context, List<Representante> representantes, String mensagem) {
//
//        SmsManager smsManager = SmsManager.getDefault();
//        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {//tem que pegar o contexto da activity que chama se não da nullPointerException
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
//        }
//        else {
//            smsManager.sendTextMessage("5511980418803", null, "A pressão está correta", null, null);
//        }
//    }

    public void enviarMensagemSMS(String mensagem) {

        SmsManager smsManager = SmsManager.getDefault();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {//tem que pegar o contexto da activity que chama se não da nullPointerException
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            finish();
        }
        else {
            for (Representante rep :
                    todosRep) {
                smsManager.sendTextMessage("55" + rep.getNumero_rep(), null, mensagem, null, null);
            }
            finish();
        }
    }
}
