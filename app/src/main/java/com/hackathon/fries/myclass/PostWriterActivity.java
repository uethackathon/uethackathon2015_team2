package com.hackathon.fries.myclass;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackathon.fries.myclass.app.AppConfig;
import com.hackathon.fries.myclass.app.AppController;
import com.hackathon.fries.myclass.appmanager.AppManager;
import com.hackathon.fries.myclass.fragment.LopFragment;
import com.hackathon.fries.myclass.helper.SQLiteHandler;
import com.hackathon.fries.myclass.models.ItemComment;
import com.hackathon.fries.myclass.models.ItemLop;
import com.hackathon.fries.myclass.models.ItemTimeLine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostWriterActivity extends AppCompatActivity {

    private static final String TAG = "PostWriterActivity";
    private ProgressDialog pDialog;

    private ImageView ivPost;
    private EditText edtContentPost;
    private EditText edtTitlePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_writer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        initViews();
    }

    private void initViews() {
        edtTitlePost = (EditText) findViewById(R.id.edt_titlepost);
        edtContentPost = (EditText) findViewById(R.id.edt_contentPost);
        ivPost = (ImageView) findViewById(R.id.iv_post);

        ivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitlePost.getText().toString();
                String content = edtContentPost.getText().toString();
                if (content.isEmpty()) {
                    return;
                }

                //get user
                SQLiteHandler db = new SQLiteHandler(getApplicationContext());
                HashMap<String, String> user = db.getUserDetails();
                String uid = user.get("uid");

                int currentAdapter = AppManager.getInstance().getCurrentAdapter();
                String currentDBKey = "";

                ArrayList<ItemLop> itemLop = null;
                switch (currentAdapter) {
                    case LopFragment.LOP_MON_HOC:
                        currentDBKey = LopFragment.KEY_LOP_MON_HOC;
                        itemLop = AppManager.getInstance().getArrItemLopMonHoc();
                        break;
                    case LopFragment.LOP_KHOA_HOC:
                        currentDBKey = LopFragment.KEY_LOP_KHOA_HOC;
                        itemLop = AppManager.getInstance().getArrItemLopKhoaHoc();
                        break;
                    case LopFragment.NHOM:
                        currentDBKey = LopFragment.KEY_NHOM;
                        break;
                }
                postPost(uid,itemLop.get(0).getIdData(),currentDBKey,title,content);

            }
        });
    }

    private void postPost(final String uid, final String group, final String base, final String title, final String content) {
        showDialog();
        Log.i(TAG, uid);
        Log.i(TAG, group);
        Log.i(TAG, base);
        Log.i(TAG, title);
        Log.i(TAG, content);

        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.URL_POST_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                // cap nhat giao dien
                                // thong bao dang bai thanh cong
//                                JSONObject jsonPost = jsonObject.getJSONObject("post");
//                                String idPost = jsonPost.getString("id");
//                                String titlePost = jsonPost.getString("title");
//                                String contentPost = jsonPost.getString("content");
//                                String groupPost = jsonPost.getString("group");
//                                int likePost = jsonPost.getInt("like");
//                                boolean isIncognitoPost = jsonPost.getBoolean("isIncognito");
//                                String typePost = jsonPost.getString("type");
//                                String basePost = jsonPost.getString("base");
//
//                                //Lay thong tin nguoi dang bai (author)
//                                JSONObject jsonAuthor = jsonObject.getJSONObject("author");
//                                String nameAuthor = jsonAuthor.getString("name");
//                                String idAuthor = jsonAuthor.getString("id");
//                                String emailAuthor = jsonAuthor.getString("email");
//                                String typeAuthor = jsonAuthor.getString("type");
//                                String mssvAuthor = jsonAuthor.getString("mssv");
//
//                                //chua xay dung chuc nang ava
//                                String avaAuthor = "";
//
//                                //Khoi tao mang comment rong
//                                ArrayList<ItemComment> itemCommentArr = new ArrayList<>();
//                                ItemTimeLine itemTimeLine = new ItemTimeLine(titlePost, nameAuthor, avaAuthor,
//                                        contentPost, likePost, false);
//                                itemTimeLine.setItemComments(itemCommentArr);

                                Message msg = new Message();
                                msg.setTarget(mHandler);
                                msg.sendToTarget();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Log.i(TAG, "Post error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("author", uid);
                data.put("base", base);
                data.put("title", title);
                data.put("content", content);
                data.put("group", group);

                return data;
            }
        };

        AppController.getInstance().addToRequestQueue(request, "post");
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            finish();
        }
    };

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.hide();
        }
    }

}
