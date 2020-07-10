package com.example.billprogram;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈 on 2020/5/11.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static  void addActivity(Activity activity){
        activities.remove(activity);
    }

    public static  void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity: activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
