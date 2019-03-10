package com.example.admin.mob_seure111;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

public class SMSReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    private static final int REQUEST_ENABLE_BT = 0;

    DatabaseHelper dbHelper;
    //lock Screen
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;

    AudioManager audioManager;
    WifiManager wifiManager;
    TelephonyManager telephonyManager;
    BluetoothAdapter bluetoothAdapter;
    Location location;
    LocationManager locationManager;

    List address;

    String nor, sil, loc, won, woff, rout, lock, don, doff, bon, boff;
    String tmp="";


    @Override
 /*   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsreceiver);
    }*/

    public void onReceive(Context context, Intent intent) {
        //-Toast.makeText(context,,Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Intent recieved: " + intent.getAction());

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        dbHelper = new DatabaseHelper(context);
        dbHelper.open();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 0L, 0.0F, new myLocation());

        deviceManger = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        compName = new ComponentName(context, MyAdmin.class);

        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }
                String orignatingAddress = messages[0].getOriginatingAddress();
                String sms = messages[0].getMessageBody();
                SmsManager var6 = SmsManager.getDefault();

                nor = Common.getPreferenceString(context,"norm","");
                sil = Common.getPreferenceString(context,"sil","");
                loc = Common.getPreferenceString(context,"loc","");
                won = Common.getPreferenceString(context,"wio","");
                woff = Common.getPreferenceString(context,"wif","");
                rout = Common.getPreferenceString(context,"rout","");
                lock = Common.getPreferenceString(context,"lock","");
                don = Common.getPreferenceString(context,"do","");
                doff = Common.getPreferenceString(context,"df","");
                bon = Common.getPreferenceString(context,"bo","");
                boff = Common.getPreferenceString(context,"bf","");
                if(sms.equalsIgnoreCase(nor)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    dbHelper.Insert(sms,orignatingAddress);
                    return;
                }
                if(sms.equalsIgnoreCase(sil)) {
                    audioManager.setRingerMode(1);
                    dbHelper.Insert(sms,orignatingAddress);
                    return;
                }
                if(sms.equalsIgnoreCase(lock)) {
                    boolean active = deviceManger.isAdminActive(compName);
                    if (active) {
                        deviceManger.lockNow();
                        dbHelper.Insert(sms,orignatingAddress);
                    }
                    return;
                }
                if(sms.equalsIgnoreCase(won)) {
                    wifiManager.setWifiEnabled(true);
                    dbHelper.Insert(sms,orignatingAddress);
                    return;
                }
                if(sms.equalsIgnoreCase(woff)) {
                    wifiManager.setWifiEnabled(false);
                    dbHelper.Insert(sms,orignatingAddress);
                    return;
                }
                if(sms.equalsIgnoreCase(rout)) {
                    //Ring out loud
                    if(audioManager.getRingerMode()==1) {
                        audioManager.setRingerMode(2);
                        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification notification = new Notification();
                        notification.sound = Uri.parse("android.resource://com.cenet.mobsecure/raw/siren");
                        notificationManager.notify(0,notification);
                    } else {
                        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification notification = new Notification();
                        notification.sound = Uri.parse("android.resource://com.cenet.mobsecure/raw/siren");
                        notificationManager.notify(0,notification);
                        notificationManager.notify(1,notification);
                    }
                    dbHelper.Insert(sms,orignatingAddress);
                    return;
                }
                if(sms.equalsIgnoreCase(don)) {
                    try
                    {
                        Method setMobileDataEnabledMethod = telephonyManager.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

                        if (null != setMobileDataEnabledMethod)
                        {
                            setMobileDataEnabledMethod.invoke(telephonyManager, true);
                        }
                    }
                    catch (Exception ex)
                    {
                        Log.e(TAG, "Error setting mobile data state", ex);
                    }
                    dbHelper.Insert(sms,orignatingAddress);
                    return;

                }
                if(sms.equalsIgnoreCase(doff)) {
                    try
                    {
                        Method setMobileDataEnabledMethod = telephonyManager.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

                        if (null != setMobileDataEnabledMethod)
                        {
                            setMobileDataEnabledMethod.invoke(telephonyManager, false);
                        }
                    }
                    catch (Exception ex)
                    {
                        Log.e(TAG, "Error setting mobile data state", ex);
                    }
                    dbHelper.Insert(sms,orignatingAddress);
                    return;
                }
                if(sms.equalsIgnoreCase(bon)) {
                    if (!bluetoothAdapter.isEnabled()) {
                        bluetoothAdapter.enable();
                        dbHelper.Insert(sms,orignatingAddress);
                    }
                    return;
                }
                if(sms.equalsIgnoreCase(boff)) {
                    bluetoothAdapter.disable();
                    dbHelper.Insert(sms,orignatingAddress);
                    return;
                }
                if(sms.equalsIgnoreCase(loc)) {
                    location = locationManager.getLastKnownLocation("gps");
                    //var6.sendTextMessage(orignatingAddress,null,"hello",null,null);
                    if(location!=null) {
                        String var11 = "lati:-"+location.getLatitude() + "\n" + "lon:-"+location.getLongitude();
                        Double var12 = Double.valueOf(location.getLatitude());
                        Double var13 = Double.valueOf(location.getLongitude());
                        Geocoder var14 = new Geocoder(context, Locale.getDefault());

                        try{
                            address = var14.getFromLocation(var12.doubleValue(),var13.doubleValue(),1);
                            if(address != null && address.size() > 0) {
                                tmp = tmp + " , " + ((Address) address.get(0)).getAddressLine(0) + " , " + ((Address)address.get(0)).getSubAdminArea() + " , "+((Address)address.get(0)).getAdminArea() + " , " + ((Address) address.get(0)).getCountryName();
                            }
                            var6.sendTextMessage(orignatingAddress,null,var11+"\n"+tmp,null,null);

                        }catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        var6.sendTextMessage(orignatingAddress,null,var11+"\n"+tmp,null,null);
                        return;
                    }
                    dbHelper.Insert(sms,orignatingAddress);
                }
            }
        }
    }
    public class myLocation implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}


