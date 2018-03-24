package com.nikostsompanidis.tripwayapp2;

import android.app.Application;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Nikos Τsompanidis on 2/3/2018.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;
    public static String systemDefaultLanguage;

    @Override
    public void onCreate() {
        super.onCreate();
        systemDefaultLanguage = Locale.getDefault().getLanguage();
        Log.d("default lang",systemDefaultLanguage);
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
