package com.hackathon.fries.myclass.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.adapter.TableSubjectAdapter;

/**
 * Created by TMQ on 20-Nov-15.
 */
public class ThoiKhoaBieuFragment extends Fragment{
    private Context mContext;
    private View rootView;
    private GridView gridSubject;

    private TableSubjectAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.table_subject, null);
        mContext = getActivity();

        initViews();

        return rootView;
    }

    private void initViews(){
        adapter = new TableSubjectAdapter(mContext);
        gridSubject = (GridView)    rootView.findViewById(R.id.gridSubject);
        gridSubject.setAdapter(adapter);
    }
}
