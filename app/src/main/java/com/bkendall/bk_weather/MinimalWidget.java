package com.bkendall.bk_weather;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.widget.RemoteViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static com.bkendall.bk_weather.StringHandler.setDoubleToInt;

//TODO: resource files don't work right in this
public class MinimalWidget extends AppWidgetProvider {

    final String FILE_NAME = String.valueOf(R.string.fileName);
    final FileHandler fileHandler = new FileHandler();

    CharSequence widgetText;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // I run on launch and every 30 minutes to update the widget
        final String filePath = context.getFilesDir().getAbsolutePath() + "/" + FILE_NAME;

        if (!fileHandler.checkIfFileExists(filePath) || !fileHandler.fileModifyDate(filePath)) {
            try {
                runApiCall(context);
            } catch (InterruptedException e) {
                // DO nothing
            }
        }

        widgetText = setTempString(context);

        Intent intent = new Intent(context, MainActivity.class);   // This intent is what launches the app onClick
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.minimal_widget);
        ComponentName watchWidget = new ComponentName(context, MinimalWidget.class);

        views.setOnClickPendingIntent(R.id.widgetText, pendingIntent);
        System.out.println(setAlertColor(context));
        if (setAlertColor(context)){
            views.setInt(R.id.minimalWidget, "setBackgroundColor", Color.RED);
        }

        views.setTextViewText(R.id.widgetText, widgetText);

        appWidgetManager.updateAppWidget(watchWidget, views);
    }

    private void runApiCall(final Context context) throws InterruptedException {
        final String apiKey;

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


    private String setTempString(final Context context) {
        // I launch am what parses the data. I have the power to launch a thread if the saved data
        // is old or non existent
        JSONObject json;
        String temp;
        final String filePath = context.getFilesDir().getAbsolutePath() + "/" + FILE_NAME;

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
            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

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
            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            assert location != null;
            return location.getLongitude();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    private boolean setAlertColor(Context context) {
        // I check to see if there are any alerts
        JSONObject json;
        final String filePath = context.getFilesDir().getAbsolutePath() + "/" + FILE_NAME;

        try {
            json = new JSONObject(fileHandler.readFile(new File(filePath)));
            json.getJSONArray("alerts");

            return true;
        } catch (IOException | JSONException e) {

            return false;
        }
    }
}