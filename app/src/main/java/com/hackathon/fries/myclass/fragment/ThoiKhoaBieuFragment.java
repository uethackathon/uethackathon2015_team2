package com.hackathon.fries.myclass.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.adapter.TableSubjectAdapter;
import com.hackathon.fries.myclass.models.ItemLopMonHoc;

/**
 * Created by TMQ on 20-Nov-15.
 */
public class ThoiKhoaBieuFragment extends Fragment implements AdapterView.OnItemClickListener{
    private Context mContext;
    private View rootView;
    private GridView gridSubject;

    private TableSubjectAdapter adapter;
    private Dialog dialogInfo;

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
        gridSubject.setOnItemClickListener(this);

        dialogInfo = new Dialog(mContext);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemLopMonHoc item = adapter.getItem(position);
        if (item==null) return;
        showDialogInfo(item);
    }

    private void showDialogInfo(ItemLopMonHoc item){
        dialogInfo.setContentView(R.layout.dialog_item_subject_info);

        dialogInfo.setTitle("Thông tin:");

        // InitViews
        TextView ten    = (TextView)    dialogInfo.findViewById(R.id.dialogTenMH);
        TextView maLMH  = (TextView)    dialogInfo.findViewById(R.id.dialogMaMH);
        TextView gv     = (TextView)    dialogInfo.findViewById(R.id.dialogGiangVien);
        TextView diaDiem= (TextView)    dialogInfo.findViewById(R.id.dialogDiaDiem);

        // Set for Views
        ten.setText(item.getTen());
        maLMH.setText(item.getMaLMH());
        gv.setText(item.getGiangVien());
        diaDiem.setText(item.getDiaDiem());

        dialogInfo.show();
    }


}
