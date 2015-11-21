package com.hackathon.fries.myclass.dialog;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.adapter.CommentAdapter;
import com.hackathon.fries.myclass.models.ItemComment;

import java.util.ArrayList;

/**
 * Created by TMQ on 21-Nov-15.
 */
public class PopupComments {
    private PopupWindow popupComment;
    private View rootView;
    private Context mContext;
    private CommentAdapter adapter;



    public PopupComments(Context context, ArrayList<ItemComment> arr){
        mContext = context;
        adapter = new CommentAdapter(mContext, arr);
    }

    public void showPopupComments(View view){
        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rootView = layoutInflater.inflate(R.layout.popup_comment, null,false);
        rootView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slide_in_bottom));

        ListView listView = (ListView)  rootView.findViewById(R.id.listCommentPopup);

        listView.setAdapter(adapter);

        // get device size
        Display display = ((WindowManager ) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int mDeviceHeight = size.y;

        // set height depends on the device size
        popupComment = new PopupWindow(rootView, size.x,size.y-50, true );
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

    private void setOnDismissPopup(){
        popupComment.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rootView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slide_out_bottom));
            }
        });
    }

}
