package com.example.android.scheduler;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;

import android.util.Log;

import org.apache.cordova.CordovaPlugin;

/*import com.red_folder.phonegap.plugin.backgroundservice.BackgroundServicePluginLogic.ExecuteResult;
import com.red_folder.phonegap.plugin.backgroundservice.BackgroundServicePluginLogic.ExecuteStatus;*/

public class SchedulerPlugin extends CordovaPlugin{

	/*
	 ************************************************************************************************
	 * Static values 
	 ************************************************************************************************
	 */
	private static final String TAG = BackgroundServicePlugin.class.getSimpleName();

SampleAlarmReceiver alarm = new SampleAlarmReceiver();

/*-------------*/
   @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {

        if (action.equals("start")) {

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
            return false;
        }

        callbackContext.success();
        return true;
    }




}