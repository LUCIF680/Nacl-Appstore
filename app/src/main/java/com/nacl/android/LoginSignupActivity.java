package com.nacl.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.nacl.android.Fragment.LoginFragment;
import com.nacl.android.Fragment.SignupFragment;
import com.nacl.android.Model.Error;
import com.nacl.android.Model.Network;

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
                boolean error = true;
                while (true) {
                    if (Network.isConnected()) {
                        network_error.dismiss();
                        if (error){
                            try {
                                Network token = new Network();
                                token.setUrl("http://192.168.0.4/user/request_csrf");
                                token.execute();
                                csrf_token = token.get();
                                error = false;
                            } catch (Exception ex) {
                                error = true;
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
    public String getToken(){
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
}
