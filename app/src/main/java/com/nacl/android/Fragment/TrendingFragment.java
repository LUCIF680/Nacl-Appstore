package com.nacl.android.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.nacl.android.Model.Network;
import com.nacl.android.Model.TrendingModel;
import com.nacl.android.R;


public class TrendingFragment extends Fragment {

    public View onCreateView(@NonNull  LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        TrendingModel model = new TrendingModel(requireContext());
        Network netwrok = new Network();
        final View root = inflater.inflate(R.layout.fragment_trending, container, false);
        final LinearLayout root_layout = root.findViewById(R.id.card_root_view);

        // Loading the loading icon and try again on failure(button)
        model.loading(root);




/*
        LinearLayout linear_layout = new LinearLayout(getContext());
        linear_layout.setOrientation(LinearLayout.HORIZONTAL);
        linear_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        CardLayout card_view = new CardLayout(requireContext());
        card_view.setPrice("50.0", Currency.INR);
        card_view.setCompanyName("Cd Project Red");
        card_view.setGameName("Cyberpunk 2077");
        card_view.setImageResource(R.drawable.cyberpunk);
        linear_layout.addView(card_view.init());

        card_view = new CardLayout(requireContext());
        card_view.setPrice("50.0", Currency.INR);
        card_view.setCompanyName("Cd Project Red");
        card_view.setGameName("Cyberpunk 2077");
        card_view.setImageResource(R.drawable.cyberpunk);
        linear_layout.addView(card_view.init());

        root_layout.addView(linear_layout);
*/

        return root;
    }
}