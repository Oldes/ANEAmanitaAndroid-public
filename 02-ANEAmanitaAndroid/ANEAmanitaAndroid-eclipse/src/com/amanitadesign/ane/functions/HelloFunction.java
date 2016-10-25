package com.amanitadesign.ane.functions;

import android.app.Activity;
import android.provider.Settings.Secure;
import android.util.Log;
import android.os.Environment;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.amanitadesign.ane.NativeExtension;

public class HelloFunction implements FREFunction  {
	@Override
	public FREObject call(FREContext ctx, FREObject[] passedArgs) {
		FREObject result = null;

		try{
			Activity act = ctx.getActivity();
			String deviceId = Secure.getString(act.getContentResolver(), Secure.ANDROID_ID);

			String message = "*** DeviceID: "+deviceId
					+"\n*** PackageName: "+act.getPackageName()
					+"\n*** ExternalStorageDir: "+Environment.getExternalStorageDirectory();
			if(NativeExtension.VERBOSE > 0) Log.i(NativeExtension.TAG, message);
			
			result = FREObject.newObject(message);

		} catch (Exception e) {
			Log.e(NativeExtension.TAG, "##### Hello Exception");
			e.printStackTrace();
		}
		return result;
	}
}
