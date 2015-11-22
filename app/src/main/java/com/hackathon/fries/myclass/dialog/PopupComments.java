package com.hackathon.fries.myclass.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.adapter.CommentAdapter;
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

/**
 * Created by TMQ on 21-Nov-15.
 */
public class PopupComments {
    private static final String TAG = "PopupComments";
    private PopupWindow popupComment;
    private View rootView;
    private Context mContext;
    private CommentAdapter adapter;
    private ProgressDialog pDialog;

    private EditText edtContentCmt;
    private ImageView btnSend;
    private ListView listView;

    // Dem so lan gui binh luan
    private int countNumberSend = 0;


    public PopupComments(Context context, ArrayList<ItemComment> arr) {
        mContext = context;
        adapter = new CommentAdapter(mContext, arr);
        pDialog = new ProgressDialog(context);
    }

    // base post author content
    public void showPopupComments(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rootView = layoutInflater.inflate(R.layout.popup_comment, null, false);
        rootView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slide_in_bottom));

        listView = (ListView) rootView.findViewById(R.id.listCommentPopup);
        listView.setAdapter(adapter);

        edtContentCmt = (EditText) rootView.findViewById(R.id.edtComment);
        btnSend = (ImageView) rootView.findViewById(R.id.btnSendComment);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtContentCmt.getText().toString();

                if (content.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Bình luận rỗng!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //get user
                SQLiteHandler db = new SQLiteHandler(mContext);
                HashMap<String, String> user = db.getUserDetails();
                String uid = user.get("uid");
                String name = user.get("name");

                int currentAdapter = AppManager.getInstance().getCurrentAdapter();
                String currentDBKey = "";

                ArrayList<ItemTimeLine> itemTimeLine = AppManager.getInstance().getArrItemTimeLine();
                if (itemTimeLine.size() == 0){
                    Log.i(TAG, "itemTimeLine null");
                }
                switch (currentAdapter) {
                    case LopFragment.LOP_MON_HOC:
                        currentDBKey = LopFragment.KEY_LOP_MON_HOC;
//                        itemLop = AppManager.getInstance().getArrItemLopMonHoc();
                        break;
                    case LopFragment.LOP_KHOA_HOC:
                        currentDBKey = LopFragment.KEY_LOP_KHOA_HOC;
//                        itemLop = AppManager.getInstance().getArrItemLopKhoaHoc();
                        break;
                    case LopFragment.NHOM:
                        currentDBKey = LopFragment.KEY_NHOM;
                        break;
                }

                postCmt(currentDBKey, itemTimeLine.get(LopFragment.positionItemClick).getIdPost(), uid, content);

            }
        });

        // get device size
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int mDeviceHeight = size.y;

        // set height depends on the device size
        popupComment = new PopupWindow(rootView, size.x, size.y - 50, true);
        // set a background drawable with rounders corners
        popupComment.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_popup_comment));
        // make it focusable to show the keyboard to enter in `EditText`
        popupComment.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popupComment.setOutsideTouchable(true);

        popupComment.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);

        setOnDismissPopup();

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popupComment.showAtLocation(view, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    private void setOnDismissPopup() {
        popupComment.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rootView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slide_out_bottom));
                mListener.onDismiss(countNumberSend);
            }
        });
    }

    private void postCmt(final String base, final String post, final String author, final String content) {
        showDialog();
        Log.i(TAG, base);
        Log.i(TAG, post);
        Log.i(TAG, author);
        Log.i(TAG, content);

        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.URL_POST_COMMENT,
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
                                JSONObject jsonComment = jsonObject.getJSONObject("comment");
                                String idCmt = jsonComment.getString("id");

                                Bundle b = new Bundle();
                                b.putString("idCmt", idCmt);
                                b.putString("content", content);

                                Message msg = new Message();
                                msg.setData(b);
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
                Toast.makeText(mContext, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("author", author);
                data.put("base", base);
                data.put("content", content);
                data.put("post", post);

                return data;
            }
        };

        AppController.getInstance().addToRequestQueue(request, "post");
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(mContext, "Gửi bình luận thành công", Toast.LENGTH_LONG).show();

            Bundle b = msg.getData();
            String idCmt = b.getString("idCmt");
            String content = b.getString("content");

            SQLiteHandler db = new SQLiteHandler(mContext);
            HashMap<String, String> user = db.getUserDetails();
            String uid = user.get("uid");
            String name = user.get("name");

            // Thay doi tren Local
            countNumberSend++; // Tang so lan gui binh luan len
            adapter.addComment(new ItemComment(idCmt, uid, name, "", content, false));
            adapter.notifyDataSetChanged();
            edtContentCmt.setText("");
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

    // --------------------- Communication -------------------------------------------

    private OnDismissListener mListener;

    public void setOnDismissListener(OnDismissListener listener) {
        mListener = listener;
    }

    public interface OnDismissListener {
        public void onDismiss(int numberSend);
    }
}
