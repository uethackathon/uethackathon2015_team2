package com.hackathon.fries.myclass.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hackathon.fries.myclass.MainActivity;
import com.hackathon.fries.myclass.R;
import com.hackathon.fries.myclass.models.ItemLop;
import com.hackathon.fries.myclass.adapter.LopAdapter;

import java.util.ArrayList;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class LopFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View root;
    private ListView lvMain;
    private LopAdapter lopMonHocAdt, lopKhoaHocAdt, nhomAdt;
    private Context mContext;
    private int currentAdapter = 0;

    public static final int LOP_MON_HOC = 1;
    public static final int LOP_KHOA_HOC = 2;
    public static final int NHOM = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.lopmonhoc, null);
        mContext = getActivity();

        initViews();

        switch (currentAdapter) {
            case LOP_MON_HOC:
                showLopMonHoc();
                break;
            case LOP_KHOA_HOC:
                showLopKhoaHoc();
                break;
            case NHOM:
                showNhomAdt();
                break;
            case 0:
                Toast.makeText(mContext, "init adapter", Toast.LENGTH_LONG).show();
                initLopKhoaHoc();
                initLopMonHoc();
                initNhom();
                break;
        }
        return root;
    }

    //    @Override

//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("adapter", currentAdapter);
//    }

    private void initViews() {
        lvMain = (ListView) root.findViewById(R.id.lv_lopmonhoc);

        lvMain.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(mContext, "ok", Toast.LENGTH_LONG).show();
        MainActivity mainActivity = (MainActivity) mContext;

        ItemLop item = (ItemLop) parent.getAdapter().getItem(position);
        mainActivity.goToTimeLine(item.getId());
    }

    private void initLopMonHoc() {
        ArrayList<ItemLop> itemArr = new ArrayList<>();
        itemArr.add(new ItemLop("Tin hoc cơ sở 4", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Cơ nhiệt", "INT2204 1", "Đinh Văn Châu", 90));
        itemArr.add(new ItemLop("Tin học cơ sở 1", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Xác suất thống kê", "INT2204 1", "Lê Phê Đô", 90));
        itemArr.add(new ItemLop("Tin nâng cao", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Lập trình hướng đối tượng", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Thiết kế giao diện người dùng", "INT2204 1", "Nguyễn Thị Nhật Thanh", 90));
        itemArr.add(new ItemLop("Giải tích", "INT2204 1", "Lê Nguyên Khôi", 90));
        itemArr.add(new ItemLop("Tối ưu hoá", "INT2204 1", "Lê Thu Hà", 90));

        lopMonHocAdt = new LopAdapter(mContext);
        lopMonHocAdt.setItemArr(itemArr);
    }

    private void initLopKhoaHoc() {
        ArrayList<ItemLop> itemArr = new ArrayList<>();
        itemArr.add(new ItemLop("K58CLC", "QH-2013-I/CQ", "Phạm Bảo Sơn", 70));

        lopKhoaHocAdt = new LopAdapter(mContext);
        lopKhoaHocAdt.setItemArr(itemArr);
    }

    private void initNhom() {
        ArrayList<ItemLop> itemArr = new ArrayList<>();
        itemArr.add(new ItemLop("Thuc hanh csdl", "234782", "", 12));

        nhomAdt = new LopAdapter(mContext);
        nhomAdt.setItemArr(itemArr);
    }

    public void showLopMonHoc() {
        lvMain.setAdapter(lopMonHocAdt);
        currentAdapter = LOP_MON_HOC;
    }

    public void showLopKhoaHoc() {
        lvMain.setAdapter(lopKhoaHocAdt);
        currentAdapter = LOP_KHOA_HOC;
    }

    public void showNhomAdt() {
        lvMain.setAdapter(nhomAdt);
        currentAdapter = NHOM;
    }
}
