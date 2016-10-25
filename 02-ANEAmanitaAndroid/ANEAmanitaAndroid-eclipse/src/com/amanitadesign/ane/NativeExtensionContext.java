package com.amanitadesign.ane;

import java.util.HashMap;
import java.util.Map;

import com.amanitadesign.ane.functions.*;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;


public class NativeExtensionContext extends FREContext
{
    public NativeExtensionContext()
    {
    }

    @Override
	public void dispose() {
    	if(NativeExtension.VERBOSE > 0) Log.i(NativeExtension.TAG,"Context disposed.");
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
		functions.put("init", new InitFunction());
		functions.put("hello", new HelloFunction());
		return functions;
	}

}
