package android.arduino.bluetooth.Controllers;

import android.arduino.bluetooth.Interfaces.ICallback;
import android.arduino.bluetooth.Tasks.CarDetailAsyncTask;
import android.arduino.bluetooth.Utils.Utils;
import android.arduino.bluetooth.config.Constants;
import android.content.Context;
import android.util.Log;

public class CarDetailsController {
	private Context mContext;

	public CarDetailsController(Context context) {
		mContext = context;
	}

	public void sendCarDetails(String params, ICallback<String> callback) {
		if (Utils.checkInternetConnection(mContext)) {
			try {
				CarDetailAsyncTask task = new CarDetailAsyncTask(mContext, callback);
				task.execute(params);
			} catch (Exception e) {
				if (Constants.LOG_ENABLED) {
					e.printStackTrace();
				}
			}
		} else {
			if (Constants.LOG_ENABLED) {
				Log.d("Nt:-1.1.2", "Internet is not working");
			}
		}

	}

}
