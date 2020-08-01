package com.example.edugorillaintern.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetworkStateChangeReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // checking the network state
        if (networkInfo != null) {
              //  if network is using wifi or mobile data

                if (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                    Toast.makeText(context, "internet using Mobile data ", Toast.LENGTH_SHORT).show();
                }

             if (networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                Toast.makeText(context, "internet is  using Wifi ", Toast.LENGTH_SHORT).show();
             }
        }
//  if internet is not working the phone
        else{
             Toast.makeText(context, "internet is not running ", Toast.LENGTH_SHORT).show();
        }
    }
}
