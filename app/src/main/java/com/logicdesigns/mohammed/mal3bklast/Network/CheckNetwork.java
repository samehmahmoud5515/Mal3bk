package com.logicdesigns.mohammed.mal3bklast.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork {
    Context context;
    private static CheckNetwork mInstance = null;

    private CheckNetwork() {

    }

    public static CheckNetwork getInstance() {
        if (mInstance == null) {
            mInstance = new CheckNetwork();
        }
        return mInstance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
