package com.kitchencreator.kitchencreator.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.Dashboard.Yourorders;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.model.Requests;


import java.util.Random;

public class ListenOrder extends Service implements ChildEventListener{

    FirebaseDatabase db;
    DatabaseReference orders;


    @Override
    public void onCreate() {
        super.onCreate();
        db=FirebaseDatabase.getInstance();
        orders=db.getReference("Requests");
    }
    public ListenOrder() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orders.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Requests requests = dataSnapshot.getValue(Requests.class);
        shownotification(dataSnapshot.getKey(),requests);

    }
    private void shownotification(String key, Requests requests) {
        Intent intent = new Intent(getBaseContext(), Yourorders.class);
        intent.putExtra("userphone",requests.getPhoneno());
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setTicker("KitchenCreator")
                .setContentInfo("New Order").setContentText("Order #"+key+"was update status to" + Common.converttostatus(requests.getStatus()))
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(contentIntent)
                .setContentInfo("Info");

        NotificationManager notification = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //int randint = new Random().nextInt(9999-1)+1;
        notification.notify(1,builder.build());
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
