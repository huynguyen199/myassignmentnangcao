package com.example.myassignmentnangcao.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private String makhoahoc;
    private String tenkhoahoc;
    private String giangvien;
    private String startday;
    private String endday;
    private String mota;



    public Course(String makhoahoc, String tenkhoahoc, String giangvien, String startday, String endday, String mota) {
        this.makhoahoc = makhoahoc;
        this.tenkhoahoc = tenkhoahoc;
        this.giangvien = giangvien;
        this.startday = startday;
        this.endday = endday;
        this.mota = mota;
    }

    public Course() {
    }

    public String getMakhoahoc() {
        return makhoahoc;
    }

    public void setMakhoahoc(String makhoahoc) {
        this.makhoahoc = makhoahoc;
    }

    public String getTenkhoahoc() {
        return tenkhoahoc;
    }

    public void setTenkhoahoc(String tenkhoahoc) {
        this.tenkhoahoc = tenkhoahoc;
    }

    public String getGiangvien() {
        return giangvien;
    }

    public void setGiangvien(String giangvien) {
        this.giangvien = giangvien;
    }

    public String getStartday() {
        return startday;
    }

    public void setStartday(String startday) {
        this.startday = startday;
    }

    public String getEndday() {
        return endday;
    }

    public void setEndday(String endday) {
        this.endday = endday;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("makhoahoc", makhoahoc);
        result.put("tenkhoahoc", tenkhoahoc);
        result.put("giangvien", giangvien);
        result.put("startday", startday);
        result.put("endday", endday);
        result.put("mota", mota);

        return result;
    }
}
