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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amanitadesign.ane.functions.*;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.air.ActivityResultCallback;
import com.adobe.air.AndroidActivityWrapper;

import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;


public class NativeExtensionContext extends FREContext implements GameHelper.GameHelperListener, ActivityResultCallback
{
	public static final String TAG = "AmanitaContext";
	private AndroidActivityWrapper aaw = null;

    private static GameHelper mHelper = null;
    final int RC_SHOW_ACHIEVEMENTS = 4237;
    final int MAX_SNAPSHOT_RESOLVE_RETRIES = 3;


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

		functions.put("signIn", new AirGooglePlayGamesSignInFunction());
		functions.put("signOut", new AirGooglePlayGamesSignOutFunction());
		functions.put("isSignedIn", new AirGooglePlayGamesIsSignedInFunction());
		functions.put("reportAchievement", new AirGooglePlayGamesReportAchievementFunction());
		functions.put("showStandardAchievements", new AirGooglePlayGamesShowAchievementsFunction());

		return functions;
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		NativeExtension.log("ExtensionContext.onActivityResult" +
				" requestCode:" + Integer.toString(requestCode) +
				" resultCode:" + Integer.toString(resultCode));

        if (requestCode == RC_SHOW_ACHIEVEMENTS && resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED)
        {
            mHelper.disconnect();
            mHelper = null;
            dispatchEvent("ON_SIGN_OUT_SUCCESS");
        }
        else if (mHelper != null)
        {
            mHelper.onActivityResult(requestCode, resultCode, intent);
        }
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


    public GameHelper createHelperIfNeeded(Activity activity)
    {
        if (mHelper == null)
        {
            logEvent("create helper");
            mHelper = new GameHelper(getActivity(), GameHelper.CLIENT_GAMES);// | GameHelper.CLIENT_SNAPSHOT | GameHelper.CLIENT_PLUS);
            mHelper.setup(this);
        }
        return mHelper;
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

    public void signOut()
    {
        logEvent("signOut");

        mHelper.signOut();
        dispatchEvent("ON_SIGN_OUT_SUCCESS");
    }

    public Boolean isSignedIn() {
        return mHelper.isSignedIn();
    }

    public GoogleApiClient getApiClient() {
        return mHelper.getApiClient();
    }

    public void reportAchievements(String achievementId)
    {
        if (!isSignedIn()) {
            return;
        }
        Games.Achievements.unlock(getApiClient(), achievementId);
    }

    public void showAchievements()
    {
        Intent achievementsIntent = Games.Achievements.getAchievementsIntent(mHelper.getApiClient());
        getActivity().startActivityForResult(achievementsIntent, RC_SHOW_ACHIEVEMENTS);
    }

    public void reportAchievements(String achievementId, double percentDouble)
    {
        if (percentDouble > 0 && percentDouble <= 1){
            int percent = (int) (percentDouble * 100);
            Games.Achievements.setSteps(getApiClient(), achievementId, percent);
        }
    }

    @Override
    public void onSignInFailed() {
        logEvent("onSignInFailed");
        dispatchEvent("ON_SIGN_IN_FAIL");
        if (_activityInstances != null)
        {
            for (Activity activity : _activityInstances)
            {
                if (activity != null)
                {
                    activity.finish();
                }
            }
            _activityInstances = null;
        }
    }

    @Override
    public void onSignInSucceeded() {
        dispatchEvent("ON_SIGN_IN_SUCCESS");
        if (_activityInstances != null)
        {
            for (Activity activity : _activityInstances)
            {
                if (activity != null)
                {
                    activity.finish();
                }
            }
            _activityInstances = null;
        }
    }


}
