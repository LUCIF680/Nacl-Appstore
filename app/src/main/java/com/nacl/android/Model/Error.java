package com.nacl.android.Model;

import android.util.Log;

public class Error{
    public static void displayError(int code,String msg){
        Log.e("error","Line no." + new Exception().getStackTrace()[1] + "msg : "+msg);
    }
}
