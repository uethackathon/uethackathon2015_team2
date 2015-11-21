package com.hackathon.fries.myclass.appmanager;

import android.content.Context;

/**
 * Created by Tdh4vn on 11/21/2015.
 */
public class AppManager {
    private static AppManager _sharePointer = null;
    private Context mainContext;
    public AppManager(){

    }
    public static AppManager getInstance(){
        if(_sharePointer == null){
            _sharePointer = new AppManager();
        }
        return _sharePointer;
    }

    public static AppManager get_sharePointer() {
        return _sharePointer;
    }

    public static void set_sharePointer(AppManager _sharePointer) {
        AppManager._sharePointer = _sharePointer;
    }

    public Context getMainContext() {
        return mainContext;
    }

    public void setMainContext(Context mainContext) {
        this.mainContext = mainContext;
    }
}
