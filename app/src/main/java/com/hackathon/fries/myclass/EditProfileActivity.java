package com.hackathon.fries.myclass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackathon.fries.myclass.app.AppConfig;
import com.hackathon.fries.myclass.app.AppController;
import com.hackathon.fries.myclass.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends Activity {
    private static final String TAG = "EditProfileActivity";
    private EditText edtName, edtEmail, edtLopKhoaHoc, edtMssv;
    private CircleImageView ivAvar;
    private Button btnDone;

    private ProgressDialog pDialog;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        //init data base
        db = new SQLiteHandler(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        initViews();
    }

    private void initViews() {
        edtName = (EditText) findViewById(R.id.edt_editname);
        edtMssv = (EditText) findViewById(R.id.edt_editmssv);
        edtEmail = (EditText) findViewById(R.id.edt_editemail);
        edtLopKhoaHoc = (EditText) findViewById(R.id.edt_editlop);
        ivAvar = (CircleImageView) findViewById(R.id.iv_editava);

        //Get intent from MainActivity
        Intent mIntent = getIntent();
        edtName.setText(mIntent.getStringExtra("name"));
        edtEmail.setText(mIntent.getStringExtra("email"));
        edtLopKhoaHoc.setText(mIntent.getStringExtra("lop"));
        edtMssv.setText(mIntent.getStringExtra("mssv"));

        ivAvar.setFillColor(Color.WHITE);

        edtEmail.setInputType(InputType.TYPE_NULL);

        btnDone = (Button) findViewById(R.id.btn_editdone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String lop = edtLopKhoaHoc.getText().toString();
                String mssv = edtMssv.getText().toString();

                if (!name.equalsIgnoreCase("") && !lop.equalsIgnoreCase("")) {

                    updateUser(name, lop, mssv, edtEmail.getText().toString());
                }
            }
        });
    }

    private void updateUser(final String name, final String lop, final String mssv, final String email){
        // Tag used to cancel the request
        String tag_string_req = "req_updateuser";

        pDialog.setMessage("Cập nhật ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        String lop = user.getString("lop");
                        String mssv = user.getString("mssv");
                        String type = user.getString("type");


//                        String lop = "K58CLC";
//                        String mssv = "13020285";
//                        String type = "Sinh viên";

                        Log.i(TAG, "update name: " + name);
                        Log.i(TAG, "update email: " + email);
                        Log.i(TAG, "update createat: " + created_at);
                        Log.i(TAG, "update lop: " + lop);
                        Log.i(TAG, "update mssv: " + mssv);
                        Log.i(TAG, "update type: " + type);

                        if (type.equalsIgnoreCase("student")){
                            type = "Sinh viên";
                        } else if (type.equalsIgnoreCase("teacher")){
                            type = "Giảng viên";
                        }

                        // Inserting row in users table
                        db.deleteUsers();
                        db.addUser(name, email, uid, created_at, lop, mssv, type);

                        // Return mainActivity
                        //Tra intent ve MainActivity
                        Intent mIntent = new Intent();
                        mIntent.putExtra("name", name);
                        mIntent.putExtra("lop", lop);
                        mIntent.putExtra("mssv", mssv);
                        mIntent.putExtra("type", type);
                        setResult(RESULT_OK, mIntent);
                        Toast.makeText(getApplicationContext(), "Cập nhật thông tin thành công :)", Toast.LENGTH_LONG).show();
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                        setResult(RESULT_CANCELED);
//                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
//                params.put("password", password);
                params.put("lop", lop);
                params.put("mssv", mssv);


                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
