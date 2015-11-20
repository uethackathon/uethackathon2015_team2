package com.hackathon.fries.myclass.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Tdh4vn on 11/21/2015.
 */
public abstract class AbstactHolder extends RecyclerView.ViewHolder {
    private int viewHolderType;
    public AbstactHolder(View itemView) {
        super(itemView);
    }
    abstract public int getViewHolderType();
}
