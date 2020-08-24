package com.nacl.android.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.nacl.android.R;

public final class MainFragment extends Fragment {
    private MainFragmentListener listener;
    public interface MainFragmentListener {
        void loadMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View current_view = inflater.inflate(R.layout.fragment_main, container, false);

        return current_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainFragment.MainFragmentListener) {
            listener = (MainFragment.MainFragmentListener) context;
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
