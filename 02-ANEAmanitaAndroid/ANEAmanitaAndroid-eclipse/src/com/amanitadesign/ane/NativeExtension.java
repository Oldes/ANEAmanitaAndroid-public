package com.amanitadesign.ane;

import com.amanitadesign.ane.NativeExtensionContext;
import android.content.Context;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 * Created by Oldes on 9/16/2016.
 */
public class NativeExtension implements FREExtension {
    public static final String TAG = "AmanitaNativeExtension";
    public static final int VERBOSE = 0; 

    public static NativeExtensionContext extensionContext;
    public static Context appContext;

    @Override
    public FREContext createContext(String contextType) {
        return extensionContext = new NativeExtensionContext();
    }

    @Override
    public void dispose() {
        if(VERBOSE > 0) Log.i(TAG, "Extension disposed.");

        appContext = null;
        extensionContext = null;
    }

    @Override
    public void initialize() {
        if(VERBOSE > 0) Log.i(TAG, "Extension initialized.");
    }

}
