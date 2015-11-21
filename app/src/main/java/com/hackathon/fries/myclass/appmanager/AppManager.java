package com.hackathon.fries.myclass.appmanager;

import android.content.Context;

import com.hackathon.fries.myclass.models.ItemLop;

import java.util.ArrayList;

/**
 * Created by Tdh4vn on 11/21/2015.
 */
public class AppManager {
    private static AppManager _sharePointer = null;
    private ArrayList<ItemLop> arrItemLopKhoaHoc;
    private ArrayList<ItemLop> arrItemLopMonHoc;

    private Context mainContext;
    public AppManager(){
        arrItemLopKhoaHoc = new ArrayList<>();
        arrItemLopMonHoc = new ArrayList<>();
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

    public ArrayList<ItemLop> getArrItemLopKhoaHoc() {
        return arrItemLopKhoaHoc;
    }

    public void setArrItemLopKhoaHoc(ArrayList<ItemLop> arrItemLopKhoaHoc) {
        this.arrItemLopKhoaHoc = arrItemLopKhoaHoc;
    }

    public ArrayList<ItemLop> getArrItemLopMonHoc() {
        return arrItemLopMonHoc;
    }

    public void setArrItemLopMonHoc(ArrayList<ItemLop> arrItemLopMonHoc) {
        this.arrItemLopMonHoc = arrItemLopMonHoc;
    }
}
