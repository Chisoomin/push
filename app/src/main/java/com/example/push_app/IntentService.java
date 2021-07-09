package com.example.push_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.fingerpush.android.FingerPushFcmListener;
import com.fingerpush.android.FingerPushManager;
import com.fingerpush.android.NetworkUtility;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class IntentService extends FingerPushFcmListener {
    String decodeTitle, decodeMessage, imgurl;
    @Override
    public void onMessage(Context context, Bundle data) {
        try{
            decodeTitle = URLDecoder.decode(data.getString("data.title"), "UTF-8");
        }catch (UnsupportedEncodingException e){}

        try{
            decodeMessage = URLDecoder.decode(data.getString("data.message"), "UTF-8");
        }catch (UnsupportedEncodingException e){}
        imgurl = data.getString("data.imgUrl");


        createNotificationChannel(decodeTitle,decodeMessage, imgurl);

    }
    private void createNotificationChannel(String Title, String Message, String imgurl) {
        Log.e("==", "createNotificationChannel");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("Channel ID", "Channel Name", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(null);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        AlertFragment alert = AlertFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("data", Title+" ");
        alert.setArguments(bundle);
        Intent intent = new Intent(IntentService.this, DrawerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Bitmap bitmap = getBitmapFromUrl(imgurl);

        PendingIntent pi = PendingIntent.getActivity(IntentService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Channel ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Title)
                .setContentText(Message)
                .setVibrate(new long[]{0, 500, 600, 1000})
                .setLights(Color.parseColor("#ffff00ff"), 500, 500)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
        //.setContentText("Hello World!");
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

        /*Intent intent = new Intent(IntentService.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pi = PendingIntent.getActivity(IntentService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        FingerNotification fingerNotification = new FingerNotification(this);
        fingerNotification.setNotificationIdentifier((int) System.currentTimeMillis());
        fingerNotification.setIcon(R.mipmap.ic_launcher); // Notification small icon
        fingerNotification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        fingerNotification.setVibrate(new long[]{0, 500, 600, 1000});
        fingerNotification.setLights(Color.parseColor("#ffff00ff"), 500, 500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fingerNotification.setColor(Color.rgb(0, 114, 162));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fingerNotification.createChannel("channel_id", "channel_name");
        }
        fingerNotification.showNotification(data, pi);*/



    }
    public Bitmap getBitmapFromUrl(String imgurl){
        try{
            URL url = new URL(imgurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}