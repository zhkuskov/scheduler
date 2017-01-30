package com.example.android.scheduler;

import org.json.JSONArray;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;

import android.content.Context;

/*import com.red_folder.phonegap.plugin.backgroundservice.BackgroundServicePluginLogic.ExecuteResult;
import com.red_folder.phonegap.plugin.backgroundservice.BackgroundServicePluginLogic.ExecuteStatus;*/

public class SchedulerPlugin extends CordovaPlugin{

public static final String TAG = "SchedulerPlugin";

SampleAlarmReceiver alarm = new SampleAlarmReceiver();

/*---------------*/
@Override public void initialize(CordovaInterface cordova, CordovaWebView webView) { 
super.initialize(cordova, webView); 
// your init code here
 }
/*-------------*/
   @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {

try{

cordova.getThreadPool().execute(new Runnable() {
            public void run() {

        if (action.equals("start")) {

Log.i(TAG, "запущено действие start");
            /*
cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    webView.postMessage("SchedulerPlugin", "start");
                }
            });
*/

Context context=cordova.getActivity().getApplicationContext();

alarm.setAlarm(context);

        } else if (action.equals("stop")) {
            /*cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    webView.postMessage("splashscreen", "show");
                }
            });
*/
        } else {
Log.i(TAG, "не запущено никакое действие");
            return false;
        }//if

}
        });

        callbackContext.success();
        return true;

}catch (Exception ex) {
			// Do something with the error in production code
			Log.d(TAG, "ERROR");
			Log.d(TAG, ex.getMessage());
return false;
		}//try

    }




}