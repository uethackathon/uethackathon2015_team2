package com.hackathon.fries.myclass.models;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class ItemLop {
    public String ten;
    public String giangVien;
    public int soNguoi;
    public String id;
    public String idData;

    public String vietTat;

    public ItemLop(String ten,String idData, String id, String giangVien, int soNguoi) {
        this.ten = ten;
        this.idData = idData;
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

    public String getIdData() {
        return idData;
    }
}
