package com.hackathon.fries.myclass.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.adapter.TableSubjectAdapter;
import com.hackathon.fries.myclass.dialog.PopupComments;
import com.hackathon.fries.myclass.models.ItemLop;
import com.hackathon.fries.myclass.models.ItemLopMonHoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TMQ on 20-Nov-15.
 */
public class ThoiKhoaBieuFragment extends Fragment implements AdapterView.OnItemClickListener{
    private static final String TAG = "ThoiKhoaBieuFragment";
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

        dialogInfo = new Dialog(mContext, R.style.DialogNoActionBar);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemLopMonHoc item = adapter.getItem(position);
        if (item==null) {
            Toast.makeText(mContext, "Trống", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialogInfo(item);
    }

    private void showDialogInfo(ItemLopMonHoc item){
        dialogInfo.setContentView(R.layout.dialog_item_subject_info);

        dialogInfo.setTitle("Thông tin:");

        // InitViews
        TextView ten    = (TextView)    dialogInfo.findViewById(R.id.dialogTenMH);
        TextView maLMH  = (TextView)    dialogInfo.findViewById(R.id.dialogMaMH);
        TextView gv     = (TextView)    dialogInfo.findViewById(R.id.dialogGiangVien);
        TextView tgian  = (TextView)    dialogInfo.findViewById(R.id.dialogThoiGian);
        TextView diaDiem= (TextView)    dialogInfo.findViewById(R.id.dialogDiaDiem);

        // Set for Views
        ten.setText(item.getTen());
        maLMH.setText(item.getMaLMH());
        gv.setText(item.getGiangVien());
        diaDiem.setText(item.getDiaDiem());

        // Convert time:
        int vtri = item.getViTri();
        int soTiet = item.getSoTiet();

        int x = (vtri-1)/10;
        int y = vtri - 10*x;

        tgian.setText("Thứ " + (x+2) + "\t\tTiết " + y + " - " + (y+soTiet-1));

        dialogInfo.show();
    }


}
