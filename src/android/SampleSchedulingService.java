package com.example.android.scheduler;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.R;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SampleSchedulingService extends IntentService {
    public SampleSchedulingService() {
        super("SchedulingService");
    }
    
    public static final String TAG = "Scheduling Demo";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    // The string the app searches for in the Google home page content. If the app finds 
    // the string, it indicates the presence of a doodle.  
    public static final String SEARCH_STRING = "coupons";
    // The Google home page URL from which the app fetches content.
    // You can find a list of other Google domains with possible doodles here:
    // http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://coupons.apps-cube.com/en/coupons/app_popular_coupons.json";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)
        // The URL from which to fetch content.
        String urlString = URL;
    
        String result ="";
        
        // Try to connect to the Google homepage and download content.
        try {
             Log.i(TAG, "Try to connect to the site and download content");
            result = loadFromNetwork(urlString);
        } catch (IOException e) {
            Log.i(TAG, "Ошибка получения данных с сайта");
        }
    
        // If the app finds the string "doodle" in the Google home page content, it
        // indicates the presence of a doodle. Post a "Doodle Alert" notification.
        if (result.indexOf(SEARCH_STRING) != -1) {
            sendNotification("Есть купоны");
            Log.i(TAG, "Нашел coupons!!");
        } else {
            sendNotification("В данных с сервера нет купонов");
            Log.i(TAG, "No coupons found. :-(");
        }
        // Release the wake lock provided by the BroadcastReceiver.
        SampleAlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
    }
    
    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
               this.getSystemService(Context.NOTIFICATION_SERVICE);
    
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, SchedulerActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.sym_action_email)
        .setContentTitle("Заголовок")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
 
//
// The methods below this line fetch content from the specified URL and return the
// content as a string.
//
    /** Given a URL string, initiate a fetch operation. */
    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";
      
        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }      
        }
        return str;
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws IOException
     */
    private InputStream downloadUrl(String urlString) throws IOException {
    
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

    /** 
     * Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from www.google.com.
     * @return String version of InputStream.
     * @throws IOException
     */
    private String readIt(InputStream stream) throws IOException {
      
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for(String line = reader.readLine(); line != null; line = reader.readLine()) 
            builder.append(line);
        reader.close();
        return builder.toString();
    }




}
