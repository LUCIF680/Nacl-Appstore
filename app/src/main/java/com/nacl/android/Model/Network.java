package com.nacl.android.Model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class Network extends AsyncTask<Void,Void,String> {
    private String url;
    static private String ipaddress = "http://192.168.29.237";
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
    public void appendUrlParm(String var,String request) throws UnsupportedEncodingException {
        url += "&" + var + "=" + URLEncoder.encode(request, StandardCharsets.UTF_8.name());
    }

    public static String getIpaddress() {
        return ipaddress;
    }

    @Override
    public String doInBackground(Void... parm) {
        String return_value;
        try {
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            StringBuffer content = new StringBuffer();
            while ((return_value = in.readLine()) != null) {
                content.append(return_value);
            }
            in.close();
            return_value = content.toString();
            con.disconnect();
            return return_value;
        } catch (MalformedURLException ex){
            Error.displayError(ex.hashCode(),ex.getMessage());
            return null;
        }
        catch (IOException ex){
            Error.displayError(ex.hashCode(),ex.getMessage());
            return null;
        }
    }
    static public boolean isConnected(){
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(getIpaddress()+"/network/ping").openConnection();
            con.getResponseCode();
            return true;
        } catch (Exception ex){
            Log.e("error",ex.getMessage().toString());
            return false;
        }
    }
}