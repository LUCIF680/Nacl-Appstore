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

import com.nacl.android.MainActivity;
import com.nacl.android.Model.Error;
import com.nacl.android.Model.LoginSignup;
import com.nacl.android.Model.Network;
import com.nacl.android.R;

import org.json.JSONObject;

public class SignupFragment extends Fragment{
    private SignupFragmentListener listener;
    private String csrf_token;
    public interface SignupFragmentListener{
        void loadLoginFragment(String error);
        void loadSignupFragment(String error);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View current_view = inflater.inflate(R.layout.fragment_signup,container,false);
        final TextView error = current_view.findViewById(R.id.error_signup);
        error.setText(this.getArguments().getString("error"));

        csrf_token = this.getArguments().getString("csrf");
        Button login_btn = current_view.findViewById(R.id.login_btn);
        ImageButton signup_arrow = current_view.findViewById(R.id.signup_arrow);
        final EditText email = current_view.findViewById(R.id.email);
        final EditText password = current_view.findViewById(R.id.password);
        final EditText conf_pass = current_view.findViewById(R.id.conf_password);
        final EditText username = current_view.findViewById(R.id.username);
        final Network network = new Network();
        signup_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*=============================================
                 * Check signup*/
                try {
                    LoginSignup model = new LoginSignup();
                    if ((model.checkUsername(username.getText().toString())
                            || model.checkEmail(email.getText().toString())
                            || model.checkPassword(password.getText().toString(),conf_pass.getText().toString())))
                        error.setText(model.error);
                    else {
                        network.setUrl("http://192.168.0.4/user/signup?");
                        network.appendUrlParm("email", email.getText().toString());
                        network.appendUrlParm("username",username.getText().toString());
                        network.appendUrlParm("password", password.getText().toString());
                        network.appendUrlParm("token", new JSONObject(csrf_token).getString("token"));
                        network.appendUrlParm("session_id", new JSONObject(csrf_token).getString("session_id"));
                        network.execute();
                        String response = network.get();
                        if (new JSONObject(response).getBoolean("auth")) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        } else
                            listener.loadSignupFragment(new JSONObject(response).getString("error"));
                    }
                }catch (Exception ex){Error.displayError(ex.hashCode(),ex.toString());}
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { listener.loadLoginFragment(""); }
        });
        return current_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignupFragmentListener) {
            listener = (SignupFragmentListener) context;
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