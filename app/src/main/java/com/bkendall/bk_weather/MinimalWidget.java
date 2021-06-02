package com.bkendall.bk_weather;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static com.bkendall.bk_weather.StringHandler.setDoubleToInt;


public class MinimalWidget extends AppWidgetProvider {

    public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // I run on launch and every 30 minutes to update the widget
        CharSequence widgetText = null;
        try {
            widgetText = setTempString(context);
        } catch (InterruptedException e) {
            widgetText = "XXX";
        }

        Intent intent = new Intent(context, MainActivity.class);   // This intent is what launches the app onClick
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.minimal_widget);
        ComponentName watchWidget = new ComponentName(context, MinimalWidget.class);

        views.setOnClickPendingIntent(R.id.widgetText, pendingIntent);
        views.setTextViewText(R.id.widgetText, widgetText);
        Toast.makeText(context, "YAYAA", Toast.LENGTH_LONG).show();
        appWidgetManager.updateAppWidget(watchWidget, views);
    }

//    @Override
//    public void onEnabled(Context context)
//    {
//        // start alarm
//        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
//        appWidgetAlarm.startAlarm();
//    }
//
//    @Override
//    public void onDisabled(Context context)
//    {
//        // stop alarm only if all widgets have been disabled
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(),getClass().getName());
//        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
//        if (appWidgetIds.length == 0) {
//            // stop alarm
//            AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
//            appWidgetAlarm.stopAlarm();
//        }
//
//    }

    private static String setTempString(final Context context) throws InterruptedException {
        // I launch am what parses the data. I have the power to launch a thread if the saved data
        // is old or non existent
        JSONObject json;
        String temp;
        final String apiKey;
        final String FILE_NAME = String.valueOf(R.string.fileName);
        final FileHandler fileHandler = new FileHandler();
        final String filePath = context.getFilesDir().getAbsolutePath() + "/" + FILE_NAME;

        // Checks if the file doesn't exist OR if it is older than fifteen minutes, if True, the thread starts
        if (!fileHandler.checkIfFileExists(filePath) || !fileHandler.fileModifyDate(filePath)) {
            apiKey = context.getString(R.string.api_key);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject json;

                        String filePath = context.getFilesDir().getAbsolutePath() + "/" + FILE_NAME;
                        json = FetchWeather.getForecast(setLatitude(context), setLongitude(context), apiKey);
                        fileHandler.createFile(filePath, json.toString());

                    } catch (IOException | JSONException e) {
                        // Do nothing, continue to next catch
                    }
                }
            });
            t.start();
            t.join();
        }
        try {
            json = new JSONObject(fileHandler.readFile(new File(filePath)));
            JSONObject currentWeather = json.getJSONObject("current");
            temp = String.valueOf(setDoubleToInt(currentWeather.getDouble("temp")));

        }catch (IOException | JSONException e){
            temp = "XXX";
        }

        return temp;
    }

    private static double setLatitude(Context context){
        // I set latitude for the api call. Null Island is default
        try {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            assert lm != null;
            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); // TODO: look to update to get current location

            assert location != null;
            return location.getLatitude();
        } catch (NullPointerException e) {
            return 0;
        }
    }
    private static double setLongitude(Context context){
        // I set longitude for the api call. Null Island is default
        try {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            assert lm != null;
            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); // TODO: look to update to get current location

            assert location != null;
            return location.getLongitude();
        } catch (NullPointerException e) {
            return 0;
        }
    }
}