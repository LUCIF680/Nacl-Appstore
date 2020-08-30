package com.nacl.android.Model;

import android.content.Context;

public class NaclModel {
    Context context;
    public NaclModel(Context context){
        this.context = context;
    }
    public int dpToPixel(int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}
