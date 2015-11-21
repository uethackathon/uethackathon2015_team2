package com.hackathon.fries.myclass.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackathon.fries.myclass.MainActivity;
import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.appmanager.AppManager;
import com.hackathon.fries.myclass.app.AppConfig;
import com.hackathon.fries.myclass.app.AppController;
import com.hackathon.fries.myclass.helper.SQLiteHandler;
import com.hackathon.fries.myclass.models.ItemLop;
import com.hackathon.fries.myclass.adapter.LopAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class LopFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "LopFragment";
    private View root;
    private ListView lvMain;
    private LopAdapter lopMonHocAdt, lopKhoaHocAdt, nhomAdt;
    private Context mContext;
    private int currentAdapter = 0;
//    private ProgressDialog pDialog;

    private SwipeRefreshLayout swipeRefresh;

    public static final int LOP_MON_HOC = 1;
    public static final int LOP_KHOA_HOC = 2;
    public static final int NHOM = 3;

    public static final String KEY_LOP_MON_HOC = "classSubject";
    public static final String KEY_LOP_KHOA_HOC = "class_xes";
    public static final String KEY_NHOM = "nhom";

    private SQLiteHandler db;
    private String uid;
    private String currentDBKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.lopmonhoc, null);
        mContext = getActivity();
        AppManager.getInstance().setMainContext(mContext);

        //dialog
//        pDialog = new ProgressDialog(mContext);
//        pDialog.setCancelable(false);

        //get user information
        db = new SQLiteHandler(mContext);
        HashMap<String, String> user = db.getUserDetails();
        uid = user.get("uid");

        //Swipe refresh
        swipeRefresh = (SwipeRefreshLayout) root.findViewById(R.id.swipe);
        swipeRefresh.setOnRefreshListener(this);

        initViews();

        switch (currentAdapter) {
            case LOP_MON_HOC:
                showLopMonHoc();
                break;
            case LOP_KHOA_HOC:
                showLopKhoaHoc();
                break;
            case NHOM:
                showNhomAdt();
                break;
            case 0:
                Toast.makeText(mContext, "init adapter", Toast.LENGTH_LONG).show();
                initLopKhoaHoc();
                initLopMonHoc();
                initNhom();
                break;
        }
        return root;
    }

    private void initViews() {
        lvMain = (ListView) root.findViewById(R.id.lv_lopmonhoc);

        lvMain.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(mContext, "ok", Toast.LENGTH_LONG).show();
        MainActivity mainActivity = (MainActivity) mContext;

        ItemLop item = (ItemLop) parent.getAdapter().getItem(position);
        mainActivity.goToTimeLine(item.getIdData(),currentAdapter);
    }

    private void initLopMonHoc() {
        ArrayList<ItemLop> itemArr = new ArrayList<>();
        itemArr.add(new ItemLop("Tin hoc cơ sở 4","12", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Cơ nhiệt","12", "INT2204 1", "Đinh Văn Châu", 90));
        itemArr.add(new ItemLop("Tin học cơ sở 1","12", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Xác suất thống kê","12", "INT2204 1", "Lê Phê Đô", 90));
        itemArr.add(new ItemLop("Tin nâng cao","12", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Lập trình hướng đối tượng","12", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Thiết kế giao diện người dùng","12", "INT2204 1", "Nguyễn Thị Nhật Thanh", 90));
        itemArr.add(new ItemLop("Giải tích","12", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Tối ưu hoá","12", "INT2204 1", "Lê Thu Hà", 90));
//        requestLopHoc(uid, KEY_LOP_MON_HOC, itemArr);
        lopMonHocAdt = new LopAdapter(mContext);
        lopMonHocAdt.setItemArr(itemArr);
    }

    private void initLopKhoaHoc() {
        ArrayList<ItemLop> itemArr = new ArrayList<>();
//        itemArr.add(new ItemLop("K58CLC", "QH-2013-I/CQ", "Phạm Bảo Sơn", 70));

        requestLopHoc(uid, KEY_LOP_KHOA_HOC, itemArr);
        lopKhoaHocAdt = new LopAdapter(mContext);
        lopKhoaHocAdt.setItemArr(itemArr);
    }

    private void initNhom() {
        ArrayList<ItemLop> itemArr = new ArrayList<>();
//        itemArr.add(new ItemLop("Thuc hanh csdl","23", "234782", "", 12));
//        requestLopHoc(uid, KEY_NHOM, itemArr);
        nhomAdt = new LopAdapter(mContext);
        nhomAdt.setItemArr(itemArr);
    }

    public void showLopMonHoc() {
        lvMain.setAdapter(lopMonHocAdt);
        currentAdapter = LOP_MON_HOC;
        currentDBKey = KEY_LOP_MON_HOC;
    }

    public void showLopKhoaHoc() {
        lvMain.setAdapter(lopKhoaHocAdt);
        currentAdapter = LOP_KHOA_HOC;
        currentDBKey = KEY_LOP_KHOA_HOC;
    }

    public void showNhomAdt() {
        lvMain.setAdapter(nhomAdt);
        currentAdapter = NHOM;
        currentDBKey = KEY_NHOM;
    }


    private void requestLopHoc(final String uid, final String databaseKey, final ArrayList<ItemLop> itemArr) {
//        pDialog.setMessage("Lấy thông tin lớp học ...");
//        showDialog();
        swipeRefresh.setRefreshing(true);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_LOPKHOAHOC, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Get lop hoc Response: " + response.toString());
//                hideDialog();
                swipeRefresh.setRefreshing(false);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONObject lopHoc = jObj.getJSONObject("group");
                        String id = lopHoc.getString("id");
                        String idLop = lopHoc.getString("maLMH");
                        String nameLop = lopHoc.getString("name");
                        String baseLop = lopHoc.getString("base");
                        int soSV = lopHoc.getInt("soSV");

                        Log.i(TAG, "id: " + id);
                        Log.i(TAG, "idLop: " + idLop);
                        Log.i(TAG, "name lop: " + nameLop);
                        Log.i(TAG, "so sv: " + soSV);

                        JSONObject jsonGiangVien = lopHoc.getJSONObject("teacher");
                        String idGiangVien = jsonGiangVien.getString("id");
                        String nameGiangVien = jsonGiangVien.getString("name");
                        String emailGiangVien = jsonGiangVien.getString("email");
                        String typeGiangVien = jsonGiangVien.getString("type");

                        itemArr.add(new ItemLop(nameLop, id, idLop, nameGiangVien, soSV));

                        Toast.makeText(mContext, "lay lop hoc thành công!", Toast.LENGTH_LONG).show();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(mContext,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Get class Error: " + error.getMessage());
                Toast.makeText(mContext,
                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
                swipeRefresh.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", name);
                params.put("id", uid);
                params.put("base", databaseKey);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "Get lop hoc");
    }

//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }

    @Override
    public void onRefresh() {
        ArrayList<ItemLop> itemLopArr = new ArrayList<>();
        requestLopHoc(uid,currentDBKey,itemLopArr);

        Toast.makeText(mContext, "currentDBKey: " + currentDBKey +
                " currentadapter: " + currentAdapter, Toast.LENGTH_LONG).show();

        switch (currentAdapter){
            case LOP_MON_HOC:
                initLopMonHoc();
                break;
            case LOP_KHOA_HOC:
                initLopKhoaHoc();
                break;
            case NHOM:
                initNhom();
                break;
        }
    }
}
