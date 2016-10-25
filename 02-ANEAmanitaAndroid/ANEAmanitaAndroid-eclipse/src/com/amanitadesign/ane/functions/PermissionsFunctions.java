/*
 * 
 * This code was partially stolen from Wadedwalker as is explained here:
 * https://forums.adobe.com/message/9088185#9088185
 * 
 */
package com.amanitadesign.ane.functions;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREArray;
import com.amanitadesign.ane.NativeExtension;
import com.amanitadesign.ane.PermissionsRequestActivity;

public class PermissionsFunctions {
	
	static public class CheckPermission implements FREFunction {
		@Override
		final public FREObject call(FREContext context, FREObject[] args) {
			try {
				String permission = args[0].getAsString();
				if(NativeExtension.VERBOSE > 1) Log.d(NativeExtension.TAG, "Checking permission: "+ permission);
				int permissionCheck = context.getActivity().checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid());
				if(NativeExtension.VERBOSE > 0) Log.d(NativeExtension.TAG, "Checking permission: "+ permission+" "+ permissionCheck);
				return 	FREObject.newObject(permissionCheck == PackageManager.PERMISSION_GRANTED);
			}
			catch (Exception e) {
				return null;
			}
		}
	}

	static public class RequestPermissions implements FREFunction {
		@Override
		final public FREObject call(FREContext context, FREObject[] args) {
			try {
				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
					Activity act = context.getActivity();
					act.overridePendingTransition(0,0);
					
					final FREArray array = (FREArray) args[0];
					final int lng = (int) array.getLength();
				    final String[] permissions = new String[(int) array.getLength()];
				    
				    for (int i = 0; i < lng; i += 1) {
				    	String permission = array.getObjectAt(i).getAsString();
				    	if(permission != null) permissions[i] = permission;
				    }
				    
				    if(NativeExtension.VERBOSE > 0) Log.d(NativeExtension.TAG, "Checking permissions: "+ permissions);
					
					final Intent intent = new Intent(act, PermissionsRequestActivity.class);
					intent.putExtra("permissions", permissions);
					act.startActivity(intent);
					act.overridePendingTransition(0,0);
					
					return FREObject.newObject(true); //true means that we should wait for callback on AS3 side
				}
				else {
					return FREObject.newObject(false); //false means that we can continue without waiting for callback
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
