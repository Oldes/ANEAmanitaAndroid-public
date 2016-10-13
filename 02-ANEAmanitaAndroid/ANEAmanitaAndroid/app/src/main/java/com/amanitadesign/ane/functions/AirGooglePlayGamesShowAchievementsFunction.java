package com.amanitadesign.ane.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.amanitadesign.ane.NativeExtension;

public class AirGooglePlayGamesShowAchievementsFunction implements FREFunction {
	
	public AirGooglePlayGamesShowAchievementsFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

		NativeExtension.extensionContext.createHelperIfNeeded(arg0.getActivity());
        if (NativeExtension.extensionContext.isSignedIn()) {
			NativeExtension.extensionContext.showAchievements();
        }
		
		return null;
	}
}
