package com.example.admin.mob_seure111;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleWidgetProvider extends AppWidgetProvider {
    private static final String MyOnClick = "myOnClickTag";
    private static final String ACTION_SIMPLEAPPWIDGET = "ACTION_BROADCASTWIDGETSAMPLE";
    private static int mCounter = 0;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
        // Construct an Intent which is pointing this class.
        Intent intent = new Intent(context, SimpleWidgetProvider.class);
        intent.setAction(ACTION_SIMPLEAPPWIDGET);
        // And this time we are sending a broadcast with getBroadcast
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    /*protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (MyOnClick.equals(intent.getAction())){
            Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show();
        }
    };*/
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_SIMPLEAPPWIDGET.equals(intent.getAction())) {

            Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show();

            DatabaseHelper dbHelper;
            List<String> name;

            SmsManager sms = SmsManager.getDefault();

            name = new ArrayList<String>();
            dbHelper = new DatabaseHelper(context);
            dbHelper.open();
            Cursor c =dbHelper.select_contact();
            if(c != null) {
                c.moveToFirst();
                for (int i = 0; i < c.getCount(); i++) {
                    name.add(c.getString(2));
                    name.add(c.getString(3));
                    name.add(c.getString(4));
                    name.add(c.getString(5));
                    c.moveToNext();
                }
            }

            for(String number : name) {
                if (!name.equals("") && name.size() == 10) {
                    sms.sendTextMessage("+91"+number, null, "Emergency", null, null);
                }
            }
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_widget);

            // This time we dont have widgetId. Reaching our widget with that way.
            ComponentName appWidget = new ComponentName(context, SimpleWidgetProvider.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }

}
