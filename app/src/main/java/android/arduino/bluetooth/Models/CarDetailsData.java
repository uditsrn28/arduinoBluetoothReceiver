package android.arduino.bluetooth.Models;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.arduino.bluetooth.Http.Backend.HTTPGetter;
import android.arduino.bluetooth.Utils.Utils;
import android.arduino.bluetooth.config.Constants;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

public class CarDetailsData {

    public String sendData(String params, Context context) {
        Gson gson = new Gson();
        String responseJsonString = new String();
        String url = Constants.URL;
        if (Utils.checkInternetConnection(context)) {
            try {
                url = url + Constants.URL_GET_KEY + "=" + URLEncoder.encode(params, "utf-8");
                responseJsonString = HTTPGetter.getResponse(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Nt:-1.1.2", "Internet is not working");
        }
        return responseJsonString;
    }
}
