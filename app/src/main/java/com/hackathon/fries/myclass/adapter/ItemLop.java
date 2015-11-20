package com.hackathon.fries.myclass.adapter;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class ItemLop {
    public String ten;
    public String giangVien;
    public int soNguoi;
    public String id;

    public String vietTat;

    public ItemLop(String ten, String id, String giangVien, int soNguoi) {
        this.ten = ten;
        this.giangVien = giangVien;
        this.soNguoi = soNguoi;
        this.id = id;

        locChuCaiDau();
    }

    void locChuCaiDau() {
        if (ten == null) {
            return;
        }
        vietTat = ten.charAt(0) + "";
    }

    public String getTen() {
        return ten;
    }

    public String getGiangVien() {
        return giangVien;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public String getId() {
        return id;
    }

    public String getVietTat() {
        return vietTat;
    }
}
