package com.bkendall.bk_weather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static com.bkendall.bk_weather.StringHandler.setDoubleToInt;

/**
 * Implementation of App Widget functionality.
 */
public class MinimalWidget extends AppWidgetProvider {

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        JSONObject json;
        int temp;
        final String FILE_NAME = String.valueOf(R.string.fileName);
        final FileHandler fileHandler = new FileHandler();
        String filePath = context.getFilesDir().getAbsolutePath() + "/" + FILE_NAME;

        if (!fileHandler.checkIfFileExists(filePath)) {
            System.out.println("FIX ME");
        }
        try {
            json = new JSONObject(fileHandler.readFile(new File(filePath)));
            JSONObject currentWeather = json.getJSONObject("current");
            temp = setDoubleToInt(currentWeather.getDouble("temp"));

        }catch (IOException | JSONException e){
            temp = 666;
        }
        CharSequence widgetText = String.valueOf(temp);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.minimal_widget);
        views.setTextViewText(R.id.widgetText, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}