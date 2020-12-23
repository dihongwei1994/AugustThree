package com.lcb.augustthree;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class Tip {

    public static void log(String text){
        Log.e("TAG","----------" + text);
    }

    public static float getDensity(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }


    //吐司
    public static void toast(Context context, String text){
        if (context!=null) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

}
