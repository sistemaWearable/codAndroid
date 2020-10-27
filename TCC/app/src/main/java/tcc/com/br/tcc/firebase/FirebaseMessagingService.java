package tcc.com.br.tcc.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        Log.d("1", "Refreshed token: " + token);
    }

}
