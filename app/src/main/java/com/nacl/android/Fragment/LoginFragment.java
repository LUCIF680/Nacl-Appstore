package com.nacl.android.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nacl.android.LoginSignupActivity;
import com.nacl.android.MainActivity;
import com.nacl.android.Model.Error;

import com.nacl.android.Model.LoginSignup;
import com.nacl.android.Model.Network;
import com.nacl.android.R;

import org.json.JSONObject;


public class LoginFragment extends Fragment{
    private LoginFragmentListener listener;
    private String csrf_token;
    public interface LoginFragmentListener{
        void loadSignupFragment(String error);
        void loadLoginFragment(String error);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View current_view = inflater.inflate(R.layout.fragment_login,container,false);
        final TextView error_section = current_view.findViewById(R.id.error);

        error_section.setText(this.getArguments().getString("error"));

        csrf_token = this.getArguments().getString("csrf");

        final Button signup_btn = current_view.findViewById(R.id.signup_btn);
        final ImageButton login_arrow = current_view.findViewById(R.id.login_arrow);
        final EditText email = current_view.findViewById(R.id.email);
        final EditText password = current_view.findViewById(R.id.password);

        final Network network = new Network();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadSignupFragment("");
            }
        });
        login_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (csrf_token == null)
                    csrf_token = ((LoginSignupActivity)getActivity()).getCsrfToken();
                try {
                    LoginSignup model = new LoginSignup();
                    /*========================================
                     * Check login*/
                    if ((model.checkEmail(email.getText().toString())
                            || model.checkLoginPassword(password.getText().toString())))
                        error_section.setText(model.error);
                    else {
                        network.setUrl(Network.getIpaddress()+"/user/login?");
                        network.appendUrlParm("email", email.getText().toString());
                        network.appendUrlParm("password", password.getText().toString());
                        network.appendUrlParm("token", new JSONObject(csrf_token).getString("token"));
                        network.appendUrlParm("session_id", new JSONObject(csrf_token).getString("session_id"));
                        network.execute();
                        String response = network.get();
                        if (new JSONObject(response).getBoolean("auth")) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("token",
                                    new JSONObject(response).getString("token"));
                            startActivity(intent);
                        } else
                            listener.loadLoginFragment(new JSONObject(response).getString("error"));
                    }
                }catch (Exception ex){Error.displayError(ex.hashCode(),ex.getMessage());}
            }
        });
        return current_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragmentListener) {
            listener = (LoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}