package android.arduino.bluetooth.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {

	private static Context mContext;

//	public static void showCenteredToast(Context context, String msg) {
//		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER, 0, 0);
//		toast.show();
//	}

	public static long getDateFilter(Context context) {
		long dateFilter;
		SharedPreferences sharedPreferences = context.getSharedPreferences("PREFS_PRIVATE", Context.MODE_PRIVATE);
		long dataSent = sharedPreferences.getLong("lastTime", 0);
		boolean frist = sharedPreferences.getBoolean("firstTime", false);
		if (frist == false && dataSent > 0) {
			dateFilter = dataSent;
		} else {
			dateFilter = new Date(System.currentTimeMillis() - 1L * 24 * 3600 * 1000).getTime();
		}
		return dateFilter;
	}

	public static String getDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") {
			public StringBuffer format(Date date, StringBuffer toAppendTo, java.text.FieldPosition pos) {
				StringBuffer toFix = super.format(date, toAppendTo, pos);
				return toFix.insert(toFix.length() - 2, ':');
			};
		};
		return dateFormat.format(date);
	}

	public static String getCurrentData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateandTime = sdf.format(new Date());
		return currentDateandTime;
	}
	
	public static long getCurrentTime(){
		return new Date(System.currentTimeMillis()).getTime();
	}

	/*
	 * public static void go(Context context ,Class cls) { Intent myIntent = new
	 * Intent(context, cls); context.startActivity(myIntent); }
	 */

	public static boolean checkInternetConnection(Context context) {
		if (context == null) {
			context = Utils.getmContext();
		}
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static Context getmContext() {
		return mContext;
	}

	public static void setmContext(Context mContext) {
		if (mContext == null) {
			Utils.mContext = mContext;
		}
	}
	
	public static String getSearchedString(Context context, int searchStringTextViewId) {
		ViewGroup viewGroup = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
		return getSearchString(viewGroup , searchStringTextViewId);
		
	}
	
	private static String getSearchString (ViewGroup viewGroup, int searchStringTextViewId){
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View currentView = viewGroup.getChildAt(i);
			if (currentView instanceof ViewGroup) {
				getSearchString((ViewGroup) viewGroup.getChildAt(i) ,searchStringTextViewId);
			} else if (currentView instanceof TextView && !(currentView instanceof Button)) {
				TextView thisTextView = (TextView) currentView;
				if (thisTextView instanceof EditText && thisTextView.getId() == searchStringTextViewId) {
					return thisTextView.getText().toString();
				}
			}
		}
		return new String();
	}
	
}
