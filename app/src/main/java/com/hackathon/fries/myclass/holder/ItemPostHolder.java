package com.hackathon.fries.myclass.holder;

import android.content.ClipData;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.appmanager.AppManager;
import com.hackathon.fries.myclass.dialog.PopupComments;
import com.hackathon.fries.myclass.models.ItemComment;

import java.util.ArrayList;

/**
 * Created by Tdh4vn on 11/21/2015.
 */
public class ItemPostHolder extends AbstactHolder {
    private ArrayList<ItemComment> listComment = new ArrayList<>();

    public ItemPostHolder(View itemView) {
        super(itemView);
        imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtContent = (TextView) itemView.findViewById(R.id.tv_content);
        imgAvatarLastPost = (ImageView) itemView.findViewById(R.id.imgAvaComment);
        txtNameLastPost = (TextView) itemView.findViewById(R.id.txtUserName);

        txtCommentLastPost = (TextView) itemView.findViewById(R.id.txtContentComment);

        txtCountLike = (TextView) itemView.findViewById(R.id.txtCountLike);
        txtCountComment = (TextView) itemView.findViewById(R.id.txtCountComment);
        btnTks = (Button) itemView.findViewById(R.id.btnTks);
        btnComment = (Button) itemView.findViewById(R.id.btnComment);
        createListener();

    }

    public void setListComment(ArrayList<ItemComment> arr){
        listComment = arr;
    }

    private void createListener(){
        btnTks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupComments(v);
            }
        });
        txtCommentLastPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupComments(v);
            }
        });
        txtNameLastPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupComments(v);
            }
        });
    }

    private void showPopupComments(View view){
        PopupComments pop = new PopupComments(AppManager.getInstance().getMainContext(), listComment);
        pop.showPopupComments(view);
    }

    private ImageView imgAvatar;
    private TextView txtTitle;
    private TextView txtContent;
    private ImageView imgAvatarLastPost;
    private TextView txtNameLastPost;
    private TextView txtCommentLastPost;
    private TextView txtCountLike;
    private TextView txtCountComment;
    private Button btnTks;
    private Button btnComment;
    @Override
    public int getViewHolderType() {
        int viewHolderType = 1;
        return viewHolderType;
    }

    public ImageView getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(ImageView imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(TextView txtTitle) {
        this.txtTitle = txtTitle;
    }

    public TextView getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(TextView txtContent) {
        this.txtContent = txtContent;
    }

    public ImageView getImgAvatarLastPost() {
        return imgAvatarLastPost;
    }

    public void setImgAvatarLastPost(ImageView imgAvatarLastPost) {
        this.imgAvatarLastPost = imgAvatarLastPost;
    }

    public TextView getTxtNameLastPost() {
        return txtNameLastPost;
    }

    public void setTxtNameLastPost(TextView txtNameLastPost) {
        this.txtNameLastPost = txtNameLastPost;
    }

    public TextView getTxtCommentLastPost() {
        return txtCommentLastPost;
    }

    public void setTxtCommentLastPost(TextView txtCommentLastPost) {
        this.txtCommentLastPost = txtCommentLastPost;
    }

    public TextView getTxtCountLike() {
        return txtCountLike;
    }

    public void setTxtCountLike(TextView txtCountLike) {
        this.txtCountLike = txtCountLike;
    }

    public TextView getTxtCountComment() {
        return txtCountComment;
    }

    public void setTxtCountComment(TextView txtCountComment) {
        this.txtCountComment = txtCountComment;
    }

    public Button getBtnTks() {
        return btnTks;
    }

    public void setBtnTks(Button btnTks) {
        this.btnTks = btnTks;
    }

    public Button getBtnComment() {
        return btnComment;
    }

    public void setBtnComment(Button btnComment) {
        this.btnComment = btnComment;
    }
}
