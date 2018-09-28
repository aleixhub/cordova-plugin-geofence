package com.cowbell.cordova.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.*;
import com.cowbell.cordova.geofence.Gson;
import com.cowbell.cordova.geofence.GeoNotification;

public class TransitionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.setLogger(new Logger(GeofencePlugin.TAG, context, false));
        Logger logger = Logger.getLogger();
            
        String error = intent.getStringExtra("error");

        if (error != null) {
            //handle error
            logger.log(Log.DEBUG, error);
        } else {
            String geofencesJson = intent.getStringExtra("transitionData");                
            GeoNotification[] geoNotifications = Gson.get().fromJson(geofencesJson, GeoNotification[].class);

            notifier = new GeoNotificationNotifier((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE), context);
            //handle geoNotifications objects
            for (GeoNotification geoNotification : geoNotifications) {
                if (geoNotification.notification != null) {
                    notifier.notify(geoNotification.notification);
                }
            }                      
        }       
    }
}
