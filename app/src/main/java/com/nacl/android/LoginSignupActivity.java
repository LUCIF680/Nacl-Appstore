package com.nacl.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.nacl.android.Fragment.LoginFragment;
import com.nacl.android.Fragment.SignupFragment;
import com.nacl.android.Model.Error;
import com.nacl.android.Model.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LoginSignupActivity extends AppCompatActivity
        implements LoginFragment.LoginFragmentListener,
        SignupFragment.SignupFragmentListener{

    Fragment login_fragment;
    Fragment signup_fragment;
    String csrf_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login_signup);

        final Snackbar network_error = Snackbar.make(findViewById(R.id.container),
                R.string.network_error, Snackbar.LENGTH_INDEFINITE);
        network_error.getView().setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
        new Thread() {
            public void run() {

                boolean not_error = true;
                while (true) {
                    if (Network.isConnected()) {
                        network_error.dismiss();
                        // GET AUTH TOKEN FROM SERVER
                        if (checkAuthToken())
                            break;
                        if (not_error){
                            // Get CSRF TOKEN
                            try {
                                Network csrf = new Network();
                                csrf.setUrl(Network.getIpaddress()+"/user/request_csrf");
                                csrf.execute();
                                csrf_token = csrf.get();
                                not_error = false;
                            } catch (Exception ex) {
                                not_error = true;
                                Error.displayError(ex.hashCode(), ex.getMessage());
                            }
                        }
                    }
                    else{
                        if (!network_error.isShown())
                            network_error.show();
                    }
                }
            }
        }.start();
        loadLoginFragment("");
    }
    public String getCsrfToken(){
        for(int i = 0;csrf_token == null && i<45;i++);
        return csrf_token;
    }
    @Override
    public void loadSignupFragment(String error){
        Bundle bundle = new Bundle();
        bundle.putString("error",error);
        bundle.putString("csrf",csrf_token);
        signup_fragment = new SignupFragment();
        signup_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signup_fragment)
                .commit();
    }
    @Override
    public void loadLoginFragment(String error){
        Bundle bundle = new Bundle();
        bundle.putString("error",error);
        bundle.putString("csrf",csrf_token);
        login_fragment = new LoginFragment();
        login_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, login_fragment)
                .commit();
    }
    public boolean checkAuthToken(){
        @SuppressLint("WrongConstant")
        SharedPreferences storage = this.getSharedPreferences("storage",MODE_APPEND);
        if (storage.contains("token")) {
            try {
                Network token_check = new Network();
                token_check.setUrl(Network.getIpaddress()+"/user/check_token?");
                token_check.appendUrlParm("token", storage.getString("token", ""));
                token_check.execute();
                String response = token_check.get();
                Log.e("hello","im fucked");
                if (new JSONObject(response).getBoolean("auth")) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }catch (Exception ex){
                Error.displayError(ex.hashCode(),ex.getMessage());
                return false;
            }
        }
        return false;
    }
}
