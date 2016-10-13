package com.amanitadesign.ane.functions;

import android.app.Activity;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.amanitadesign.ane.NativeExtension;
import com.amanitadesign.ane.GameHelper;

public class AirGooglePlayGamesSignInFunction implements FREFunction {

	public AirGooglePlayGamesSignInFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

        Activity appActivity = arg0.getActivity();
        GameHelper mHelper = NativeExtension.extensionContext.createHelperIfNeeded(null);

        mHelper.onStart(appActivity, false);
        mHelper.beginUserInitiatedSignIn();

        return null;
	}

}
