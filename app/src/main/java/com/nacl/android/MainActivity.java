package com.nacl.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.material.snackbar.Snackbar;

import com.nacl.android.Fragment.MainFragment;
import com.nacl.android.Model.Network;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener{

    Fragment main_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.fragment_main);
        final View current_view = findViewById(R.id.main_container);

        final FloatingSearchView search_bar = findViewById(R.id.search_bar);
        search_bar.setOnLeftMenuClickListener(
                new FloatingSearchView.OnLeftMenuClickListener() {

                    @Override
                    public void onMenuOpened() {
                        Log.e("msg","hello");
                    }

                    @Override
                    public void onMenuClosed() {

                    }


                } );
        /*Snackbar here for network issue
        * ===============================*/
        final Snackbar network_error = Snackbar.make(current_view, R.string.network_error, Snackbar.LENGTH_INDEFINITE);
        network_error.getView().setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
        new Thread() {
            public void run() {
                boolean error = true;
                while (true) {
                    if (Network.isConnected()) {
                        network_error.dismiss();
                    }
                    else{
                        if (!network_error.isShown())
                            network_error.show();
                    }
                }
            }
        }.start();

        /*Save the token value in SharedPreferences
        * =========================================*/
        Intent intent = getIntent();
        if (intent.getStringExtra("token") != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("storage", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("token", intent.getStringExtra("token"));
            myEdit.apply();
        }

        loadMainFragment();
    }

    @Override
    public void loadMainFragment(){
        main_fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, main_fragment)
                .commit();
    }

}
