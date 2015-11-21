package com.hackathon.fries.myclass.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.models.ItemComment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Le Tuan on 21-Nov-15.
 */
public class CommentAdapter extends BaseAdapter {
    private static final String TAG = "CommentAdapter";
    private ArrayList<ItemComment> listComments;
    private Context mContext;
    private LayoutInflater lf;

    public CommentAdapter(Context context, ArrayList<ItemComment> arr){
        listComments = arr;
        mContext = context;
        lf = LayoutInflater.from(mContext);

//        getDataComments();
    }

    private void getDataComments(){
        listComments = new ArrayList<>();

        listComments.add(new ItemComment("Nguyễn Tiến Minh", "http://www2.uet.vnu.edu.vn/coltech/sites/uet_logo.png",
                "Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. ", false));
        listComments.add(new ItemComment("Nguyễn Tiến Minh", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/VNU.logo.jpg/100px-VNU.logo.jpg",
                "Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. ", true));
        listComments.add(new ItemComment("Nguyễn Tiến Minh", "http://www2.uet.vnu.edu.vn/coltech/sites/uet_logo.png",
                "Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. ", false));
        listComments.add(new ItemComment("Nguyễn Tiến Minh", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/VNU.logo.jpg/100px-VNU.logo.jpg",
                "Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. Minh chó. ", true));
    }

    @Override
    public int getCount() {
        return listComments.size();
    }

    @Override
    public ItemComment getItem(int position) {
        return listComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view==null){
            view = lf.inflate(R.layout.item_comment_in_popup, null);
        }

        ItemComment item = listComments.get(position);

        ImageView img       = (ImageView)   view.findViewById(R.id.imgAvaComment);
        TextView txtUser    = (TextView)    view.findViewById(R.id.txtUserName);
        TextView txtContent = (TextView)    view.findViewById(R.id.txtContentComment);
        CheckBox checkBox   = (CheckBox)    view.findViewById(R.id.check_comment);

        txtUser.setText(item.getName());
        txtContent.setText(item.getContent());
        checkBox.setChecked(item.isVote());

        Bitmap bitmap = getImageBitmapFromUrl(item.getAvaUrl());
        if (bitmap!= null) img.setImageBitmap(bitmap);

        return view;
    }

    public Bitmap getImageBitmapFromUrl(String path) {
        Bitmap bm = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn.getResponseCode() != 200){
                return bm;
            }
            conn.connect();
            InputStream is = conn.getInputStream();

            BufferedInputStream bis = new BufferedInputStream(is);
            try {
                bm = BitmapFactory.decodeStream(bis);
            }catch(OutOfMemoryError ex) {
                bm = null;
            }
            bis.close();
            is.close();
        } catch (Exception e) {}

        return bm;
    }
}
