package com.hackathon.fries.myclass.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hackathon.fries.myclass.adapter.ItemComment;
import com.hackathon.fries.myclass.adapter.ItemTimeLine;
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
    private View root;
    private Context mainContext;
    private ListView lvTimeline;
    private TimeLineAdapter mAdapter;

    private ProgressDialog pLog;

    private String idLop;
//    public TimelineFragment(String id) {
//        this.idLop = id;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_timeline, null);
        mainContext = getActivity();

        pLog = new ProgressDialog(mainContext);
        pLog.setCancelable(false);

        initViews();
        initData();
        return root;
    }

    private void initViews() {
        mAdapter = new TimeLineAdapter(mainContext);
        lvTimeline = (ListView) root.findViewById(R.id.lv_timeline);

        lvTimeline.setAdapter(mAdapter);
    }

    public void initData() {
        //Lay du lieu tu server ve luu trong 1 ArrayList
//        getPostComment(idLop);
        //set itemArr cho adapter

        setDemoData();

//        ArrayList<ItemTimeLine> itemArr = getPost();
    }

    private void setDemoData() {
        itemPostArr = new ArrayList<>();
        itemCommentArr = new ArrayList<>();

        itemCommentArr.add(new ItemComment("","","",false));
        itemCommentArr.add(new ItemComment("Tran Van Tu","","thang nay hoi cau ngu vcc",false));
        itemCommentArr.add(new ItemComment("Tran Minh Quy","","thang nay hoi cau ngu vcc",true));
        itemCommentArr.add(new ItemComment("Nguyen Tien Minh","","thang nay hoi cau ngu vcc",false));

        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, false));
        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, false));
        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));
        itemPostArr.add(new ItemTimeLine("Tran Duc Hung", "", "Hom nay em co cau hoi rat hay danh cho quy vi", 12, true));

        itemPostArr.get(0).setItemComments(itemCommentArr);
        itemPostArr.get(1).setItemComments(itemCommentArr);
        itemPostArr.get(2).setItemComments(itemCommentArr);
        itemPostArr.get(3).setItemComments(itemCommentArr);
        itemPostArr.get(4).setItemComments(itemCommentArr);
        itemPostArr.get(5).setItemComments(itemCommentArr);
        itemPostArr.get(6).setItemComments(itemCommentArr);
        itemPostArr.get(7).setItemComments(itemCommentArr);
        itemPostArr.get(8).setItemComments(itemCommentArr);
    }

    private ArrayList<ItemTimeLine> itemPostArr;
    private ArrayList<ItemComment> itemCommentArr;

    private void getPostComment(final String id) {
        itemPostArr = new ArrayList<>();
        itemCommentArr = new ArrayList<>();

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
                        String id = jsonObject.getString("idclass");
                        //lay jsonItem nhet vao item
                        JSONArray jsonPost = jsonObject.getJSONArray("post");

                        for (int i = 0; i < jsonPost.length(); i++) {
                            //Lay mang cac post
                            //Luu vao 1 arrayList post
                            String name = jsonPost.getJSONObject(i).getString("name");
                            String tittle = jsonPost.getJSONObject(i).getString("tittle");
                            String content = jsonPost.getJSONObject(i).getString("content");
                            String ava = jsonPost.getJSONObject(i).getString("ava");
                            int like = jsonPost.getJSONObject(i).getInt("like");
                            boolean isConfirm = jsonPost.getJSONObject(i).getBoolean("confirm");
                            itemPostArr.add(new ItemTimeLine(name, ava, content, like, isConfirm));

                            //Lay mang cac comment
                            //Luu vao 1 arraylist comment
                            JSONArray jsonComment = jsonPost.getJSONObject(i).getJSONArray("comment");

                            for (int j = 0; j < jsonComment.length(); j++) {
                                itemCommentArr = new ArrayList<>();
                                String name1 = jsonComment.getJSONObject(j).getString("name");
                                String avaUrl1 = jsonComment.getJSONObject(j).getString("avatar");
                                String contentComment = jsonComment.getJSONObject(j).getString("content");
                                boolean isVote = jsonComment.getJSONObject(j).getBoolean("vote");

                                itemCommentArr.add(new ItemComment(name1, avaUrl1, contentComment, isVote));

                                itemPostArr.get(i).setItemComments(itemCommentArr);
                            }
                        }
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
                return lop;
            }
        };
        AppController.getInstance().addToRequestQueue(request, "timeline_item");
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

    public void setIdLop(String idLop) {
        this.idLop = idLop;
    }
}
