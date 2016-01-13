package android.arduino.bluetooth.Tasks;

import android.arduino.bluetooth.Interfaces.ICallback;
import android.arduino.bluetooth.Models.CarDetailsData;
import android.arduino.bluetooth.Utils.Utils;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Udit on 1/13/16.
 */
public class CarDetailAsyncTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ICallback<String> mCallback;

    public CarDetailAsyncTask(Context mContext, ICallback<String> mCallback) {
        super();
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    @Override
    protected String doInBackground(String... params) {
        if (Utils.checkInternetConnection(mContext)) {
            try {
                CarDetailsData carDetailsData = new CarDetailsData();
                return carDetailsData.sendData(params[0], mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Async Task", "Internet is not working");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            this.mCallback.getResponse(result);
        } catch (Exception e) {
            Log.d("Async Task", "SomeThing gone Wrong wih the Result");
        }
    }
}
