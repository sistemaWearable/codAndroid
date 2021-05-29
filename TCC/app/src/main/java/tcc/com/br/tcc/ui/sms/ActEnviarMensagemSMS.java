package tcc.com.br.tcc.ui.sms;

import android.content.Intent;
import android.telephony.SmsManager;

import java.util.List;

import tcc.com.br.tcc.database.WearableDatabase;
import tcc.com.br.tcc.database.dao.RoomRepresentanteDAO;
import tcc.com.br.tcc.model.Representante;

public class ActEnviarMensagemSMS{

    private Intent dadosRecebidos;
    private WearableDatabase database;
    private RoomRepresentanteDAO daoRep;
    private List<Representante> todosRep;

    private void iniciaComponentesBanco() {
        //database = Room.databaseBuilder(this, WearableDatabase.class, "wearable.db").allowMainThreadQueries().build();
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

    public void enviarMensagemSMS(String mensagem, List<Representante> todosRep) {

        SmsManager smsManager = SmsManager.getDefault();
        for (Representante rep :
                todosRep) {
            smsManager.sendTextMessage("55" + rep.getNumero_rep(), null, mensagem, null, null);
        }
    }
}
