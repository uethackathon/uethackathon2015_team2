package com.hackathon.fries.myclass.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.models.ItemComment;
import com.hackathon.fries.myclass.models.ItemTimeLine;
import com.hackathon.fries.myclass.adapter.TimeLineAdapter;
import com.hackathon.fries.myclass.app.AppConfig;
import com.hackathon.fries.myclass.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.*;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class TimelineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "TimelineFragment";
    private View root;
    private Context mainContext;
    //    private ListView lvTimeline;
    private TimeLineAdapter mAdapter;
    private ArrayList<ItemTimeLine> itemPostArr;
    //    private ProgressDialog pLog;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;

    private String idLop;
    private int lopType;
    private String keyLopType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_timeline, null);
        mainContext = getActivity();
        itemPostArr = new ArrayList<>();
        //dialog
//        pLog = new ProgressDialog(mainContext);
//        pLog.setCancelable(false);

        //swipe
        swipeRefresh = (SwipeRefreshLayout) root.findViewById(R.id.swipe_timeline);
        swipeRefresh.setOnRefreshListener(this);

        Bundle b = this.getArguments();
        lopType = b.getInt("lopType");
        idLop = b.getString("idData");

        Log.i(TAG, "lopType" + lopType + "");
        Log.i(TAG, "idLop" + idLop);
//        initData();

//        for (int i = 0; i < 100000; i++) {
//            i++;
//        }
//        initViews();
        switch (lopType) {
            case LopFragment.LOP_MON_HOC:
                keyLopType = LopFragment.KEY_LOP_MON_HOC;
                break;
            case LopFragment.LOP_KHOA_HOC:
                keyLopType = LopFragment.KEY_LOP_KHOA_HOC;
                break;
            case LopFragment.NHOM:
                keyLopType = LopFragment.KEY_NHOM;
                break;
        }
//        itemPostArr = new ArrayList<>();
        requestPost(idLop, keyLopType, itemPostArr);
//        while(itemPostArr.size() == 0){
//            Log.i(TAG, "wait");
//        }
        Handler handler = new Handler();
        handler.postAtTime(new Runnable() {
            public void run() {
                Log.i(TAG, "wait");
                if (itemPostArr.size() == 0) {
                    TimelineFragment.this.onDetach();
                    Toast.makeText(mainContext, "Chưa có bài đăng nào", Toast.LENGTH_LONG).show();
                }
                mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainContext);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                //String title, String name, String ava, String content, int like, boolean isConfirmByTeacher
                //ItemTimeLine item = new ItemTimeLine("FLAGS_POS");
                //itemPostArr.add(item);
                mAdapter = new TimeLineAdapter(itemPostArr, mainContext);
                mRecyclerView.setAdapter(mAdapter);

            }
        }, 2000);

        Log.i(TAG, "SIZE " + itemPostArr.size());
        return root;
    }

    private void requestPost(final String id, final String database_type, final ArrayList<ItemTimeLine> itemPostArr) {
        //Hien thi 1 dialog cho request
//        showDialog();
        swipeRefresh.setRefreshing(true);

        StringRequest request = new StringRequest(Method.POST, AppConfig.URL_GET_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                hideDialog();
                Log.i(TAG, "nhay vao onResponse");
                Log.i(TAG, response);
                swipeRefresh.setRefreshing(false);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        //lay jsonItem nhet vao item
                        JSONArray jsonPostArr = jsonObject.getJSONArray("posts");

                        for (int i = 0; i < jsonPostArr.length(); i++) {
                            //Lay mang cac post
                            //Luu vao 1 arrayList post
                            String id = jsonPostArr.getJSONObject(i).getString("id");
                            String titlePost = jsonPostArr.getJSONObject(i).getString("title");
                            String contentPost = jsonPostArr.getJSONObject(i).getString("content");
                            String groupType = jsonPostArr.getJSONObject(i).getString("group");
                            int like = jsonPostArr.getJSONObject(i).getInt("like");
//                            boolean isConfirm = jsonPostArr.getJSONObject(i).getBoolean("confirm");
                            boolean isIncognito = jsonPostArr.getJSONObject(i).getBoolean("isIncognito");
                            String basePost = jsonPostArr.getJSONObject(i).getString("base");

                            //author post
                            JSONObject jsonAuthorPost = jsonPostArr.getJSONObject(i).getJSONObject("author");
                            String nameAuthorPost = jsonAuthorPost.getString("name");
                            String idAuthorPost = jsonAuthorPost.getString("id");
                            String emailAuthorPost = jsonAuthorPost.getString("email");
                            String typeAuthorPost = jsonAuthorPost.getString("type");
                            String mssvAuthorPost = jsonAuthorPost.getString("mssv");

                            Log.i(TAG, "post: " + nameAuthorPost);
                            Log.i(TAG, "post: " + emailAuthorPost);
                            Log.i(TAG, "post: " + typeAuthorPost);

                            boolean isConfirm = false;

                            itemPostArr.add(new ItemTimeLine(titlePost, nameAuthorPost, "", contentPost, like, isConfirm));

                            //Lay mang cac comment
                            //Luu vao 1 arraylist comment
                            JSONArray jsonCommentArr = jsonPostArr.getJSONObject(i).getJSONArray("comments");

                            ArrayList<ItemComment> itemCommentArr = new ArrayList<>();
                            for (int j = 0; j < jsonCommentArr.length(); j++) {

                                String idComment = jsonCommentArr.getJSONObject(j).getString("id");
                                String contentComment = jsonCommentArr.getJSONObject(j).getString("content");

                                JSONObject jsonAuthorComment = jsonCommentArr.getJSONObject(j).getJSONObject("author");
                                String idAuthorComment = jsonAuthorComment.getString("id");
                                String nameAuthorComment = jsonAuthorComment.getString("name");
                                String emailAuthorComment = jsonAuthorComment.getString("email");
                                String typeAuthorComment = jsonAuthorComment.getString("type");
                                String mssvAuthorComment = jsonAuthorComment.getString("mssv");

                                Log.i(TAG, "comment: " + nameAuthorComment);
                                Log.i(TAG, "comment: " + emailAuthorComment);
                                Log.i(TAG, "comment: " + typeAuthorComment);

                                boolean isVote = jsonCommentArr.getJSONObject(j).getBoolean("confirmed");

                                itemCommentArr.add(new ItemComment(nameAuthorComment, "", contentComment, isVote));

                            }
                            itemPostArr.get(i).setItemComments(itemCommentArr);
                            mAdapter.notifyDataSetChanged();
                        }

                        //Toast.makeText(mainContext, "Lay thong tin bai post thanh cong", Toast.LENGTH_LONG).show();

                        Log.i(TAG, "post2: " + itemPostArr.get(0).getName());
                        Log.i(TAG, "post2: " + itemPostArr.get(0).getTitle());
                        Log.i(TAG, "post2: " + itemPostArr.get(0).getContent());

                        Message msg = new Message();
                        msg.setTarget(mHandler);
                        msg.sendToTarget();
//                        mAdapter = new TimeLineAdapter(itemPostArr, mainContext);
//                        mRecyclerView.setAdapter(mAdapter);
//                        mAdapter.updateList(itemPostArr);
//                        mAdapter.notifyDataSetChanged();
//                        initViews();
//                        isDataLoaded = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                hideDialog();
                swipeRefresh.setRefreshing(false);
                Toast.makeText(mainContext, "Không có bài đăng nào.", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> lop = new HashMap<>();
                lop.put("id", id);
                lop.put("base", database_type);
                return lop;
            }
        };
        AppController.getInstance().addToRequestQueue(request, "timeline_item");
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            mAdapter = new TimeLineAdapter(itemPostArr, mainContext);
//            mRecyclerView.setAdapter(mAdapter);
//            Log.i(TAG, "setadapter thanh cong");
//            initViews();
            Log.i(TAG, "ok");

            for (int i = 0; i < itemPostArr.size(); i++) {
                Log.i(TAG, "itemPost: " + itemPostArr.get(i).getName());
                Log.i(TAG, "itemPost: " + itemPostArr.get(i).getTitle());
                Log.i(TAG, "itemPost: " + itemPostArr.get(i).getContent());
                Log.i(TAG, "itemPost: " + itemPostArr.get(i).getLike());
                Log.i(TAG, "itemPost: " + itemPostArr.get(i).getAva());
            }
        }
    };

    @Override
    public void onRefresh() {
//        initData();
//        initViews();
        Log.i(TAG, "onRefesh");
        itemPostArr = new ArrayList<>();
        requestPost(idLop, keyLopType, itemPostArr);

        Handler handler = new Handler();
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                mAdapter.updateList(itemPostArr);
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

//    private void showDialog() {
//        if (!pLog.isShowing()) {
//            pLog.show();
//        }
//    }
//
//    private void hideDialog() {
//        if (pLog.isShowing()) {
//            pLog.hide();
//        }
//    }

    public void setIdLop(String idLop, int lopType) {
        this.idLop = idLop;
        this.lopType = lopType;
    }

}
