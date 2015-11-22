package com.hackathon.fries.myclass.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.holder.AbstactHolder;
import com.hackathon.fries.myclass.holder.ItemPostHolder;
import com.hackathon.fries.myclass.holder.ItemWritePostHolder;
import com.hackathon.fries.myclass.models.ItemBase;
import com.hackathon.fries.myclass.models.ItemTimeLine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<AbstactHolder> {
    private static final String TAG = "TimelineAdapter";
    private Context mContext;
    private ArrayList<ItemBase> itemArr = new ArrayList<ItemBase>();

    public TimeLineAdapter(ArrayList<ItemBase> posts, Context ctx){
        this.itemArr = posts;
        this.mContext = ctx;
        itemArr = posts;
    }

    public void updateList(ArrayList<ItemBase> posts) {
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
            Log.i(TAG,"bat dau");

            //itemPostHolder.getImgAvatar();
            ItemPostHolder itemPostHolder = (ItemPostHolder) abstactHolder;
            final ItemTimeLine itemTimeLine = (ItemTimeLine)itemArr.get(position);
            itemPostHolder.setListComment(itemTimeLine.getItemComments());
            itemPostHolder.getTxtAuthor().setText(itemTimeLine.getName());
            try {
                ImageView i = itemPostHolder.getImgAvatar();
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(itemTimeLine.getAva()).getContent());
                i.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "title" + itemTimeLine.getTitle());
            itemPostHolder.getTxtTitle().setText("[" + itemTimeLine.getTitle() + "]");
            itemPostHolder.getTxtContent().setText(itemTimeLine.getContent());
            itemPostHolder.getTxtCountLike().setText(itemTimeLine.getLike() + " cám ơn");//
            itemPostHolder.getTxtCountComment().setText(itemTimeLine.getItemComments().size() + " bình luận");//



        } else {
            Log.i(TAG,"by xong");
               // ItemWritePostHolder itemWritePostHolder = (ItemWritePostHolder) abstactHolder;
        }

    }

    @Override
    public int getItemCount() {
        return itemArr==null?0:itemArr.size();
    }
}
