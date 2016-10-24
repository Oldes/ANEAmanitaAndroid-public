package com.amanitadesign.ane.functions;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;
import android.os.Environment;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class HelloFunction implements FREFunction  {
	public static final String TAG = "Hello";
	public static Context mContext;
	@Override
	public FREObject call(FREContext ctx, FREObject[] passedArgs) {
		FREObject result = null;

		Activity act;

		try{

			act = ctx.getActivity();
			String deviceId = Secure.getString(act.getContentResolver(), Secure.ANDROID_ID);
			mContext=act;

			result = FREObject.newObject(
					"*** DeviceID: "+deviceId
					+"\n*** PackageName: "+act.getPackageName()
					+"\n*** ExternalStorageDir: "+Environment.getExternalStorageDirectory());

		} catch (FREWrongThreadException e) {
			Log.d(TAG, "##### Caught FREWrongThreadException");
			e.printStackTrace();
		} catch (Exception e) {
			Log.d(TAG, "##### Exception");
			e.printStackTrace();
		}
		return result;
	}
}
