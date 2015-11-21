package com.hackathon.fries.myclass.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathon.fries.myclass.R;

import org.w3c.dom.Text;

/**
 * Created by Tdh4vn on 11/21/2015.
 */
public class ItemPostHolder extends AbstactHolder {
    public ItemPostHolder(View itemView) {
        super(itemView);
        imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtContent = (TextView) itemView.findViewById(R.id.tv_content);
        imgAvatarLastPost = (ImageView) itemView.findViewById(R.id.imgAvatarLastPost);
        txtNameLastPost = (TextView) itemView.findViewById(R.id.txtNameLastComment);
        txtCommentLastPost = (TextView) itemView.findViewById(R.id.txtContentLastComment);
        txtCountLike = (TextView) itemView.findViewById(R.id.txtCountLike);
        txtCountComment = (TextView) itemView.findViewById(R.id.txtCountComment);
    }
    private ImageView imgAvatar;
    private TextView txtTitle;
    private TextView txtContent;
    private ImageView imgAvatarLastPost;
    private TextView txtNameLastPost;
    private TextView txtCommentLastPost;
    private TextView txtCountLike;
    private TextView txtCountComment;
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
}
