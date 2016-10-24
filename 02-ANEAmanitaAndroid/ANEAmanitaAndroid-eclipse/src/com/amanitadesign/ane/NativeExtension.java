package com.amanitadesign.ane;

import com.amanitadesign.ane.NativeExtensionContext;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 * Created by Oldes on 9/16/2016.
 */
public class NativeExtension implements FREExtension {
    public static final String TAG = "AmanitaNativeExtension";

    public static NativeExtensionContext extensionContext;
    public static Context appContext;
   // public static SettingsContentObserver mSettingsWatcher;

    public static Intent googleAPIActivityIntent;

    public static void notifyLicenseStatus(String status, String error) {
        Log.i(TAG, "notifyLicenseStatus: "+status+" "+error);
        try {
            extensionContext.dispatchStatusEventAsync(status, error);
        } catch (Exception e) {
            Log.i(TAG, "*** Failed to dispatch status!");
            e.printStackTrace();
        }
    }

    @Override
    public FREContext createContext(String contextType) {
        Log.i(TAG, "createContext: "+ contextType);
        extensionContext = new NativeExtensionContext();

        return extensionContext;
    }

    @Override
    public void dispose() {
        Log.d(TAG, "Extension disposed.");

        appContext = null;
        extensionContext = null;
        googleAPIActivityIntent = null;
    }

    @Override
    public void initialize() {
        Log.d(TAG, "Extension initialized.");
    }

    public static void log(String message)
    {
        extensionContext.dispatchStatusEventAsync("LOGGING", message);
    }
}
