package com.fekratoday.mashawer.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CheckInternetConnection {
    private static CheckInternetConnection instance;
    private boolean check;

    private CheckInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                check = true;
            }
        } else {
            check = false;
        }
    }

    public static CheckInternetConnection getInstance(Context context) {
        return new CheckInternetConnection(context);
    }

    public Boolean checkInternet() {
        return check;
    }

}