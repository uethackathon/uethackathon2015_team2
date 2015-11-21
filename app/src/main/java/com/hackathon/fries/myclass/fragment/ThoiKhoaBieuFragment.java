package com.hackathon.fries.myclass.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.adapter.TableSubjectAdapter;
import com.hackathon.fries.myclass.adapter.TimeLineAdapter;
import com.hackathon.fries.myclass.app.AppController;
import com.hackathon.fries.myclass.dialog.PopupComments;
import com.hackathon.fries.myclass.helper.SQLiteHandler;
import com.hackathon.fries.myclass.models.ItemLop;
import com.hackathon.fries.myclass.models.ItemLopMonHoc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TMQ on 20-Nov-15.
 */
public class ThoiKhoaBieuFragment extends Fragment implements AdapterView.OnItemClickListener{
    private static final String TAG = "ThoiKhoaBieuFragment";
    private Context mContext;
    private View rootView;
    private GridView gridSubject;
    private ArrayList<ItemLopMonHoc> listSubject = new ArrayList<>();

    private TableSubjectAdapter adapter;
    private Dialog dialogInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.table_subject, null);
        mContext = getActivity();

        initViews();

        getDataFromServer();

        return rootView;
    }

    private void initViews(){
        gridSubject = (GridView)    rootView.findViewById(R.id.gridSubject);
        gridSubject.setOnItemClickListener(this);

        dialogInfo = new Dialog(mContext, R.style.DialogNoActionBar);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemLopMonHoc item = adapter.getItem(position);
        if (item==null) {
            Toast.makeText(mContext, "Trống", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialogInfo(item);
    }

    private void showDialogInfo(ItemLopMonHoc item){
        dialogInfo.setContentView(R.layout.dialog_item_subject_info);

        dialogInfo.setTitle("Thông tin:");

        // InitViews
        TextView ten    = (TextView)    dialogInfo.findViewById(R.id.dialogTenMH);
        TextView maLMH  = (TextView)    dialogInfo.findViewById(R.id.dialogMaMH);
        TextView gv     = (TextView)    dialogInfo.findViewById(R.id.dialogGiangVien);
        TextView tgian  = (TextView)    dialogInfo.findViewById(R.id.dialogThoiGian);
        TextView diaDiem= (TextView)    dialogInfo.findViewById(R.id.dialogDiaDiem);

        // Set for Views
        ten.setText(item.getTen());
        maLMH.setText(item.getMaLMH());
        gv.setText(item.getGiangVien());
        diaDiem.setText(item.getDiaDiem());

        // Convert time:
        int vtri = item.getViTri();
        int soTiet = item.getSoTiet();

        int x = (vtri-1)/10;
        int y = vtri - 10*x;

        tgian.setText("Thứ " + (x+2) + "\t\tTiết " + y + " - " + (y+soTiet-1));

        dialogInfo.show();
    }

    //----------------------------------------------------------------------------------------------

    private static final String URL_REQUEST = "http://tutran.net/v1/timetable";

    private String getUserID(){
        SQLiteHandler sqlite;
        sqlite = new SQLiteHandler(mContext);

        HashMap<String, String> user = sqlite.getUserDetails();
        String uid = user.get("uid");

        return uid;
    }

    private void getDataFromServer(){
        listSubject.clear();
        String id = getUserID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL_REQUEST, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean error = response.getBoolean("error");
                    if (error) return;

                    JSONArray listSubjects = response.getJSONArray("timetable");
                    for (int i=0; i<listSubjects.length(); i++){
                        JSONObject subject = listSubjects.getJSONObject(i);
                        String ten      = subject.getString("name");
                        String maLMH    = subject.getString("maLMH");
                        int vtri    = subject.getInt("viTri");
                        int soTiet  = subject.getInt("soTiet");
                        int soSV    = subject.getInt("soSV");
                        int nhom    = subject.getInt("nhom");

                        JSONObject teacher = subject.getJSONObject("teacher");
                        String tenGV    = teacher.getString("name");

                        ItemLopMonHoc item = new ItemLopMonHoc(ten, maLMH, "Dia diem",tenGV, vtri, soTiet, soSV, nhom);

                        listSubject.add(item);

                        Log.i(TAG, listSubject.size() + " -- " + ten + ", " + vtri + ", " + soTiet + "_____________");
                    }

                    adapter = new TableSubjectAdapter(mContext);
                    adapter.setListSubject(listSubject);
                    gridSubject.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },  new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error Response");
            }
        }
        );

        if (AppController.getInstance() != null) {
            AppController.getInstance().addToRequestQueue(jsObjRequest, "jsonobject_request");
        } else {
            Log.i(TAG, "AppController is null");
        }



    }

}
