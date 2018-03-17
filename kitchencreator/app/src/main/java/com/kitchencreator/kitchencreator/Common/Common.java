package com.kitchencreator.kitchencreator.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kitchencreator.kitchencreator.model.Requests;
import com.kitchencreator.kitchencreator.model.commondetails;

/**
 * Created by hp on 06-02-2018.
 */

public class Common {

    public static commondetails userdetail;
    public static Requests currentrequest;
    public static int dollar = 70;


    public static String converttostatus(String status) {
        if (status.equals("0")){
            return "placed";
        }else if (status.equals("1")){
            return  "On the way";
        }else if (status.equals("2")) {
            return "Recieved";
        }else
            return "Shipped";

    }
    public static final String DELETE = "Delete";
    public static  boolean isConnectedtoInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i=0;i<info.length;i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;

                }
            }

            }
        return false;
        }
    }

