package com.amanitadesign.ane.functions;

import com.amanitadesign.ane.NativeExtension;

import android.content.Context;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class InitFunction implements FREFunction {
	
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		Context appContext = context.getActivity().getApplicationContext();
		NativeExtension.appContext = appContext;
		return null;
	}
}
