package com.nacl.android;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.nacl.android.Model.Network;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        final View current_view = findViewById(R.id.main_container);
        final Snackbar network_error = Snackbar.make(current_view, R.string.network_error, Snackbar.LENGTH_INDEFINITE);
        network_error.getView().setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
        new Thread() {
            public void run() {
                while (true) {
                    if (!Network.isConnected()){
                        if (network_error.isShown())
                            network_error.dismiss();
                    }else
                        network_error.show();
                }
            }
        }.start();
    }
}
