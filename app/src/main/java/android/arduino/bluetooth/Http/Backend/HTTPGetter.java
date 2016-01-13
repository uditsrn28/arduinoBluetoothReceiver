/**
 * @author Udit Sarin
 */

package android.arduino.bluetooth.Http.Backend;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.arduino.bluetooth.config.Constants;
import android.util.Log;

public class HTTPGetter {

	private static final String CLASS_TAG = "HTTPGetter:";

	public static String getResponse(String url) {
		if (Constants.LOG_ENABLED) {
			Log.i("Http Getter", CLASS_TAG + "getResponse() url: " + url);
		}
		String result = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			Log.e("url is" , url);
			HttpGet getRequest = new HttpGet(url);
			HttpResponse getResponse;
			getResponse = client.execute(getRequest);
			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null) {
				result = EntityUtils.toString(getResponseEntity, HTTP.UTF_8);

			}
		} catch (ClientProtocolException e) {
			if (Constants.LOG_ENABLED) {
				Log.i("Http Getter", CLASS_TAG + "getResponse(): " + e.getMessage());
				e.printStackTrace();
			}
		} catch (IOException e) {
			if (Constants.LOG_ENABLED) {
				Log.i("Http Getter", CLASS_TAG + "getResponse(): " + e.getMessage());
				e.printStackTrace();
			}
		}
		return result;
	}
}
