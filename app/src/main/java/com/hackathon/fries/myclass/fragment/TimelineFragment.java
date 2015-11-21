package com.hackathon.fries.myclass.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.dialog.ViewDialog;
import com.hackathon.fries.myclass.models.ItemComment;
import com.hackathon.fries.myclass.models.ItemTimeLine;
import com.hackathon.fries.myclass.adapter.TimeLineAdapter;
import com.hackathon.fries.myclass.app.AppConfig;
import com.hackathon.fries.myclass.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.*;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class TimelineFragment extends Fragment {
    private static final String TAG = "TimelineFragment";
    private View root;
    private Context mainContext;
    private ListView lvTimeline;
    private TimeLineAdapter mAdapter;
    private ArrayList<ItemTimeLine> itemPostArr;
    //    private ArrayList<ItemComment> itemCommentArr;
    private ProgressDialog pLog;
    private RecyclerView mRecyclerView;

    private String idLop;
    private int lopType;
    private String keyLopType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_timeline, null);
        mainContext = getActivity();

        pLog = new ProgressDialog(mainContext);
        pLog.setCancelable(false);
        initData();
        initViews();

        return root;
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mainContext));

//        Log.i(TAG, "initview name: " + itemPostArr.get(0).getName());
//        Log.i(TAG, "initview content: " + itemPostArr.get(0).getContent());
//        Log.i(TAG, "initview like: " + itemPostArr.get(0).getLike());
//        Log.i(TAG, "initview title: " + itemPostArr.get(0).getTitle());

//        mAdapter = new TimeLineAdapter(itemPostArr, mainContext);
//
//        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(mainContext, "da nhan", Toast.LENGTH_LONG).show();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                        ViewDialog mDialog = new ViewDialog();
//                        mDialog.showDialog((Activity) mainContext, "ok");
//                        break;
//                }
                return true;
            }
        });

    }

    public void initData() {
        //Lay du lieu tu server ve luu trong 1 ArrayList
//        getPostComment(idLop);
        //set itemArr cho adapter

//        setDemoData();
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
        itemPostArr = new ArrayList<>();
        requestPost(idLop, keyLopType, itemPostArr);
        setDemoData();
//        new RequestPSot().execute();

    }


    private void setDemoData() {
//        itemPostArr = new ArrayList<>();
        ArrayList<ItemComment> itemCommentArr = new ArrayList<>();

        itemCommentArr.add(new ItemComment("", "", "", false));
//        itemCommentArr.add(new ItemComment("Tran Van Tu", "", "thang nay hoi cau ngu vcc", false));
//        itemCommentArr.add(new ItemComment("Tran Minh Quy", "", "thang nay hoi cau ngu vcc", true));
//        itemCommentArr.add(new ItemComment("Nguyen Tien Minh", "", "thang nay hoi cau ngu vcc", false));

        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
//        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, false));
//        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, false));
//        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
//        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
//        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
//        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
//        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
//        itemPostArr.add(new ItemTimeLine("Hoi ngu", "Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));

        itemPostArr.get(0).setItemComments(itemCommentArr);
//        itemPostArr.get(1).setItemComments(itemCommentArr);
//        itemPostArr.get(2).setItemComments(itemCommentArr);
//        itemPostArr.get(3).setItemComments(itemCommentArr);
//        itemPostArr.get(4).setItemComments(itemCommentArr);
//        itemPostArr.get(5).setItemComments(itemCommentArr);
//        itemPostArr.get(6).setItemComments(itemCommentArr);
//        itemPostArr.get(7).setItemComments(itemCommentArr);
//        itemPostArr.get(8).setItemComments(itemCommentArr);
    }
//    private boolean isDataLoaded = false;

    private void requestPost(final String id, final String database_type, final ArrayList<ItemTimeLine> itemPostArr) {
//        itemPostArr = new ArrayList<>();
//        itemCommentArr = new ArrayList<>();

        //Hien thi 1 dialog cho request
        showDialog();

        StringRequest request = new StringRequest(Method.POST, AppConfig.URL_GET_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
//                        String id = jsonObject.getString("idclass");
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

                            ArrayList<ItemComment> itemCommentArr = null;
                            for (int j = 0; j < jsonCommentArr.length(); j++) {
                                itemCommentArr = new ArrayList<>();

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

                        }

                        Toast.makeText(mainContext, "Lay thong tin bai post thanh cong", Toast.LENGTH_LONG).show();

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
                hideDialog();
                Toast.makeText(mainContext, "Không load được thông tin bài đăng", Toast.LENGTH_LONG).show();
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
            mAdapter = new TimeLineAdapter(itemPostArr, mainContext);
            mRecyclerView.setAdapter(mAdapter);
            Log.i(TAG, "setadapter thanh cong");
        }
    };

    private class RequestPost extends AsyncTask<Void, Void, Void> {
        private ArrayList<ItemTimeLine> itemPostArr = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... params) {
            //Hien thi 1 dialog cho request
//            showDialog();

            StringRequest request = new StringRequest(Method.POST, AppConfig.URL_GET_POST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    hideDialog();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        if (!error) {
//                        String id = jsonObject.getString("idclass");
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

                                ArrayList<ItemComment> itemCommentArr = null;
                                for (int j = 0; j < jsonCommentArr.length(); j++) {
                                    itemCommentArr = new ArrayList<>();

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

                            }

//                            Toast.makeText(mainContext, "Lay thong tin bai post thanh cong", Toast.LENGTH_LONG).show();
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
//                    hideDialog();
                    Toast.makeText(mainContext, "Không load được thông tin bài đăng", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> lop = new HashMap<>();
                    lop.put("id", idLop);
                    lop.put("base", keyLopType);
                    return lop;
                }
            };
            AppController.getInstance().addToRequestQueue(request, "timeline_item");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            initViews();
        }
    }

    private void showDialog() {
        if (!pLog.isShowing()) {
            pLog.show();
        }
    }

    private void hideDialog() {
        if (pLog.isShowing()) {
            pLog.hide();
        }
    }

    public void setIdLop(String idLop, int lopType) {
        this.idLop = idLop;
        this.lopType = lopType;
    }

}
