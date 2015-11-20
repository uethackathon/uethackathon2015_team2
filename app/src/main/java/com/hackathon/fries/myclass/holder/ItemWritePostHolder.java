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
    private EditText txtEdit;
    public ItemWritePostHolder(View itemView) {
        super(itemView);
        imgAvatar = (ImageView) itemView.findViewById(R.id.avatar);
        txtEdit = (EditText) itemView.findViewById(R.id.textBox);
    }

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

    public EditText getTxtEdit() {
        return txtEdit;
    }

    public void setTxtEdit(EditText txtEdit) {
        this.txtEdit = txtEdit;
    }
}
