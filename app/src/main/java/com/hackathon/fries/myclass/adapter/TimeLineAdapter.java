package com.hackathon.fries.myclass.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.holder.AbstactHolder;
import com.hackathon.fries.myclass.holder.ItemPostHolder;
import com.hackathon.fries.myclass.holder.ItemWritePostHolder;
import com.hackathon.fries.myclass.models.ItemTimeLine;

import java.util.ArrayList;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<AbstactHolder> {
    private Context mContext;
    private ArrayList<ItemTimeLine> itemArr = new ArrayList<ItemTimeLine>();

    public TimeLineAdapter(ArrayList<ItemTimeLine> posts, Context ctx){
        this.itemArr = posts;
        this.mContext = ctx;
        itemArr = posts;
    }

    public void updateList(ArrayList<ItemTimeLine> posts) {
        this.itemArr = posts;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position){
        if(position == 0){
            return 0;
        }
        else {
            return 1;
        }
    }
    @Override
    public AbstactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false);
            ItemPostHolder itemPostHolder = new ItemPostHolder(view);
            return itemPostHolder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.writepost_layout, parent, false);
            ItemWritePostHolder itemWritePostHolder = new ItemWritePostHolder(view);
            return itemWritePostHolder;
       }
    }

    @Override
    public void onBindViewHolder(AbstactHolder abstactHolder, int position) {
        if(abstactHolder.getViewHolderType() == 1){
            Log.i("Post","abcd");
            final ItemTimeLine itemTimeLine = itemArr.get(position - 1);
            //itemPostHolder.getImgAvatar();
            ItemPostHolder itemPostHolder = (ItemPostHolder) abstactHolder;
            itemPostHolder.getTxtTitle().setText(itemTimeLine.getName());
            itemPostHolder.getTxtContent().setText(itemTimeLine.getContent());
            itemPostHolder.getTxtCountLike().setText(String.valueOf(itemTimeLine.getLike()) + " cám ơn");//
            itemPostHolder.getTxtCountComment().setText(String.valueOf(itemTimeLine.getItemComments().size()) + " bình luận");//
            //itemPostHolder.getImgAvatarLastPost();
            itemPostHolder.getTxtNameLastPost().setText(
                    itemTimeLine.getItemComments().get(itemTimeLine.getItemComments().size() - 1).getName());
            itemPostHolder.getTxtCommentLastPost().setText(
                    itemTimeLine.getItemComments().get(itemTimeLine.getItemComments().size() - 1).getContent());
        } else {
            Log.i("Write","abcd");
               // ItemWritePostHolder itemWritePostHolder = (ItemWritePostHolder) abstactHolder;
        }

    }

    @Override
    public int getItemCount() {
        return itemArr==null?0:itemArr.size();
    }
}
