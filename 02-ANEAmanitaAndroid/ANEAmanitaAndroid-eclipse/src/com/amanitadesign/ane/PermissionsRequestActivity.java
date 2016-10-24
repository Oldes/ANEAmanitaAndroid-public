package com.amanitadesign.ane;

import android.annotation.TargetApi;
import android.app.Activity;  
import android.os.Build;  
//import android.graphics.Color;  
//import android.graphics.drawable.ColorDrawable;  
import android.os.Bundle;
import android.util.Log;


@TargetApi(23)  
final public class PermissionsRequestActivity extends Activity {  
  
  @Override  
  final protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
  
    //setTheme(android.R.style.Theme_Translucent_NoTitleBar);  
    setContentView(R.layout.request_permissions);
    //setVisible(false);  
  
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {  
      if (getActionBar() != null) {  
        getActionBar().hide();  
      }  
    }  
  
    //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
  
    this.requestPermissions(getIntent().getExtras().getStringArray("permissions"), android.os.Process.myUid());
  }  
  
  @Override  
  final public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {  
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);  

    String results = "";  
  
    for (int i = 0; i < permissions.length; i += 1) {  
      results += permissions[i] + "=" + grantResults[i];  
      if (i < permissions.length-1) {  
        results += ",";  
      }  
    }
    Log.d("Request", results);
    

    finish();  
    this.overridePendingTransition(0,0);  
    NativeExtension.extensionContext.dispatchStatusEventAsync("onRequestPermissionsResult", results);
  }  
}  