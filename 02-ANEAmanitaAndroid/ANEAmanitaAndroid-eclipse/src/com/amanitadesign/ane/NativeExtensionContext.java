/*
 * Copyright (C) <year> <copyright holders>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.amanitadesign.ane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amanitadesign.ane.functions.*;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.air.ActivityResultCallback;
import com.adobe.air.AndroidActivityWrapper;


public class NativeExtensionContext extends FREContext implements ActivityResultCallback
{
	public static final String TAG = "AmanitaContext";
	private AndroidActivityWrapper aaw = null;
    public static FREContext FREContext;

    public NativeExtensionContext()
    {
        aaw = AndroidActivityWrapper.GetAndroidActivityWrapper();
        aaw.addActivityResultListener(this); 
    }

    @Override
	public void dispose() {
        if (aaw != null)
        {
            aaw.removeActivityResultListener(this);
            aaw = null;
        }
		Log.d(TAG,"Context disposed.");
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
		functions.put("init", new InitFunction());
		functions.put("hello", new HelloFunction());
		functions.put("checkPermission", new PermissionsFunctions.CheckPermission());
		functions.put("requestPermissions", new PermissionsFunctions.RequestPermissions());
		return functions;
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		NativeExtension.log("ExtensionContext.onActivityResult" +
				" requestCode:" + Integer.toString(requestCode) +
				" resultCode:" + Integer.toString(resultCode));

    }

    public void dispatchEvent(String eventName) {
        dispatchEvent(eventName, "OK");
    }

    public void logEvent(String eventName) {
        Log.i(TAG, eventName);
    }

    public void dispatchEvent(String eventName, String eventData)
    {
        logEvent(eventName);
        if (eventData == null)
        {
            eventData = "OK";
        }
        dispatchStatusEventAsync(eventName, eventData);
    }


    private List<Activity> _activityInstances;

    public void registerActivity(Activity activity)
    {
        if (_activityInstances == null)
        {
            _activityInstances = new ArrayList<Activity>();
        }
        _activityInstances.add(activity);
    }

    
}
