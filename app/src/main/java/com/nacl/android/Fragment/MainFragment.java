package com.nacl.android.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.nacl.android.R;

public final class MainFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View current_view = inflater.inflate(R.layout.fragment_login, container, false);
        Snackbar network_error = Snackbar.make(current_view, R.string.network_error, Snackbar.LENGTH_INDEFINITE);
        network_error.getView().setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
        network_error.show();
        return current_view;
    }
}
