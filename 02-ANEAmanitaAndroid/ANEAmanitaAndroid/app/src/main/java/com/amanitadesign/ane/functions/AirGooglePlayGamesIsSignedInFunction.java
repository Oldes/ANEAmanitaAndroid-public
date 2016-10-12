package com.amanitadesign.ane.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import com.amanitadesign.ane.NativeExtension;

public class AirGooglePlayGamesIsSignedInFunction implements FREFunction {

    @Override
    public FREObject call(FREContext arg0, FREObject[] arg1) {

        NativeExtension.extensionContext.createHelperIfNeeded(arg0.getActivity());

        FREObject isSignedIn = null;

        try {
            isSignedIn = FREObject.newObject(NativeExtension.extensionContext.isSignedIn());
        } catch (FREWrongThreadException e) {
            e.printStackTrace();
        }

        return isSignedIn;
    }
}
