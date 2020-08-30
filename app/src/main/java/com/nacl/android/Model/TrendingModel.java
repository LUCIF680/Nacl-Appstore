package com.nacl.android.Model;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModel;

import com.nacl.android.R;

import java.util.Date;

public class TrendingModel {
    Context context;
    final public int TIME_OUT = 10000;
    public TrendingModel(Context context){
        this.context = context;
    }
    public void loading(final View root){
        final Button button = root.findViewById(R.id.try_again);
        root.findViewById(R.id.spin_kit).setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {

                final NaclModel model = new NaclModel(context);
                long time = new Date().getTime(); // time is in millisec
                time += TIME_OUT;
                while(true) {
                    if (time < new Date().getTime()) {
                        root.post(new Runnable() {
                            @Override
                            public void run() {
                                button.setVisibility(View.VISIBLE);
                                root.findViewById(R.id.spin_kit).setVisibility(View.GONE);
                                button.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                            loading(root);
                                    }
                                });

                            }
                        });
                        break;
                    }
                }
            }
        }).start();
    }

}