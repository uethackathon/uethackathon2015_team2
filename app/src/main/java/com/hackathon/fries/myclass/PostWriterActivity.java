package com.hackathon.fries.myclass;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackathon.fries.myclass.app.AppConfig;
import com.hackathon.fries.myclass.app.AppController;
import com.hackathon.fries.myclass.models.ItemComment;
import com.hackathon.fries.myclass.models.ItemTimeLine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostWriterActivity extends AppCompatActivity {

    private static final String TAG = "PostWriteActivity";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_writer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

    }

    private void postPost(final String uid, final String base, final String title, final String content) {
        showDialog();

        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.URL_POST_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                // cap nhat giao dien
                                // thong bao dang bai thanh cong
                                JSONObject jsonPost = jsonObject.getJSONObject("post");
                                String idPost = jsonPost.getString("id");
                                String titlePost = jsonPost.getString("title");
                                String contentPost = jsonPost.getString("content");
                                String groupPost = jsonPost.getString("group");
                                int likePost = jsonPost.getInt("like");
                                boolean isIncognitoPost = jsonPost.getBoolean("isIncognito");
                                String typePost = jsonPost.getString("type");
                                String basePost = jsonPost.getString("base");

                                //Lay thong tin nguoi dang bai (author)
                                JSONObject jsonAuthor = jsonObject.getJSONObject("author");
                                String nameAuthor = jsonAuthor.getString("name");
                                String idAuthor = jsonAuthor.getString("id");
                                String emailAuthor = jsonAuthor.getString("email");
                                String typeAuthor = jsonAuthor.getString("type");
                                String mssvAuthor = jsonAuthor.getString("mssv");

                                //chua xay dung chuc nang ava
                                String avaAuthor = "";

                                //Khoi tao mang comment rong
                                ArrayList<ItemComment> itemCommentArr = new ArrayList<>();
                                ItemTimeLine itemTimeLine = new ItemTimeLine(titlePost, nameAuthor, avaAuthor,
                                        contentPost, likePost, false);
                                itemTimeLine.setItemComments(itemCommentArr);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                return data;
            }
        };

        AppController.getInstance().addToRequestQueue(request, "post");
    }

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
