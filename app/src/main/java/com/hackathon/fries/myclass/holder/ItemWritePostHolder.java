package com.hackathon.fries.myclass.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathon.fries.myclass.R;

/**
 * Created by Tdh4vn on 11/21/2015.
 */
public class ItemWritePostHolder extends AbstactHolder {
    private ImageView imgAvatar;
    private TextView txtView;
    public ItemWritePostHolder(View itemView) {
        super(itemView);
        imgAvatar = (ImageView) itemView.findViewById(R.id.avatar);
        txtView = (EditText) itemView.findViewById(R.id.textView2);
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
