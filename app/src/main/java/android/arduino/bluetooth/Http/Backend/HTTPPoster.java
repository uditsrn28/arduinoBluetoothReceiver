/**
 * @author Udit Sarin
 */
package android.arduino.bluetooth.Http.Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.arduino.bluetooth.config.Constants;
import android.util.Log;

public class HTTPPoster {

	private static final String CLASS_TAG = "HTTPPoster:";

	public static String doPost(String urlString, ArrayList<NameValuePair> mPostData) {
		if (Constants.LOG_ENABLED) {
			Log.i("Http Poster", CLASS_TAG +  "doPost() : request url:" + urlString);
			Log.i("Http Poster", CLASS_TAG +  "doPost() : post data:" + mPostData);
		}
		String line = new String();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(urlString);
		httppost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
//		httppost.addHeader("Accept-Charset","UTF-8");
		try {
			httppost.setEntity(new UrlEncodedFormEntity(mPostData,"UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			line = readStream(response.getEntity().getContent());
		} catch (UnsupportedEncodingException e) {
			if (Constants.LOG_ENABLED) {
				Log.e("Http Poster", CLASS_TAG + "doPost(): UnsupportedEncodingException : " + e.getMessage());
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			if (Constants.LOG_ENABLED) {
				Log.e("Http Poster", CLASS_TAG + "doPost(): ClientProtocolException : " + e.getMessage());
				e.printStackTrace();
			}
		} catch (IOException e) {
			if (Constants.LOG_ENABLED) {
				Log.e("Http Poster", CLASS_TAG + "doPost(): IOException : " + e.getMessage());
				e.printStackTrace();
			}
		}
		if (Constants.LOG_ENABLED) {
			Log.i("Http Poster", CLASS_TAG + "doPost() : raw response:" + line);
		}
		return line;
	}

	/**
	 * Reads the data from input stream
	 * 
	 * @param is
	 * @return The content from InputStream.
	 * @throws IOException
	 */
	public static String readStream(InputStream is) throws IOException {
		String response = new String();

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
				response = writer.toString();
			} finally {
				is.close();
			}
		}
		return response;
	}
}