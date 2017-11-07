package com.codeit.rajat.utilities;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rajat on 7/11/17.
 */

public class CheckNetworkConnection {

    private Context ctx;

    public CheckNetworkConnection(Context ctx) {
        this.ctx = ctx;
    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                ctx.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo !=null){
                if(networkInfo.getState()== NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }
}
