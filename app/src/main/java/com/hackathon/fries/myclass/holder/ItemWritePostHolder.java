package com.hackathon.fries.myclass.holder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathon.fries.myclass.PostWriterActivity;
import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.appmanager.AppManager;

/**
 * Created by Tdh4vn on 11/21/2015.
 */
public class ItemWritePostHolder extends AbstactHolder {
    private ImageView imgAvatar;
    private TextView txtView;
    public ItemWritePostHolder(View itemView) {
        super(itemView);
        imgAvatar = (ImageView) itemView.findViewById(R.id.avatar);
        txtView = (TextView) itemView.findViewById(R.id.textView2);
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity)AppManager.getInstance().getMainContext(), PostWriterActivity.class);
                ((Activity)AppManager.getInstance().getMainContext()).startActivity(intent);

            }
        });

    }
    @Override
    public int getViewHolderType() {
        int viewHolderType = 0;
        return viewHolderType;
    }

    public ImageView getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(ImageView imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public TextView getTxtEdit() {
        return txtView;
    }

    public void setTxtEdit(TextView txtView) {
        this.txtView = txtView;
    }
}
