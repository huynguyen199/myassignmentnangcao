package com.example.myassignmentnangcao.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String masinhvien;
    private String tensinhvien;
    private String ngaysinh;
    private String gioitinh;
    private String makhoahoc;


    public Student() {
    }

    public Student(String masinhvien, String tensinhvien, String ngaysinh, String gioitinh, String makhoahoc) {
        this.masinhvien = masinhvien;
        this.tensinhvien = tensinhvien;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.makhoahoc = makhoahoc;
    }

    public String getMasinhvien() {
        return masinhvien;
    }

    public void setMasinhvien(String masinhvien) {
        this.masinhvien = masinhvien;
    }

    public String getTensinhvien() {
        return tensinhvien;
    }

    public void setTensinhvien(String tensinhvien) {
        this.tensinhvien = tensinhvien;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getMakhoahoc() {
        return makhoahoc;
    }

    public void setMakhoahoc(String makhoahoc) {
        this.makhoahoc = makhoahoc;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("masinhvien", masinhvien);
        result.put("tensinhvien", tensinhvien);
        result.put("ngaysinh", ngaysinh);
        result.put("gioitinh", gioitinh);
        result.put("makhoahoc", makhoahoc);
        return result;
    }
}
