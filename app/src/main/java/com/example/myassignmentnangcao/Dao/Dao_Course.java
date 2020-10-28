package com.example.myassignmentnangcao.Dao;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.myassignmentnangcao.Model.Course;
import com.example.myassignmentnangcao.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dao_Course {
    private Context context;
    private View v;


    public Dao_Course(Context context, View view) {
        this.context = context;
        this.v =view;
    }


    public void insert(final Course course){
         DatabaseReference datacourse = FirebaseDatabase.getInstance().getReference().child("Course");
        datacourse.orderByChild("makhoahoc")
                .equalTo(course.getMakhoahoc())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            HashMap<String,Object> map = (HashMap<String, Object>) course.toMap();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            database.getReference().child("Course").push()
                                    .setValue(map);
                            DialogPlus dialog = (DialogPlus) v.getTag();
                            dialog.dismiss();

                        }else{

                            final EditText mkhoahoc = v.findViewById(R.id.makhoahoc);
                            if(v.findViewById(R.id.makhoahoc) == mkhoahoc){
                                mkhoahoc.setError("mã khóa học bị trùng");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void update(Course course,String index){
        final DatabaseReference datacourse = FirebaseDatabase.getInstance().getReference().child("Course").child(index);
        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put("tenkhoahoc", course.getTenkhoahoc());
        updates.put("giangvien", course.getGiangvien());
        updates.put("startday", course.getStartday());
        updates.put("endday", course.getEndday());
        updates.put("mota", course.getMota());
        datacourse.updateChildren(updates);

    }


    // doc thong tin cua vi tri do
    public void read(String index){
        final ArrayList<Course> list = new ArrayList<>();
        final DatabaseReference datacourse = FirebaseDatabase.getInstance().getReference().child("Course");
        datacourse.orderByKey()
                .equalTo(index)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Course course = null;
                            for(DataSnapshot data : snapshot.getChildren()){
                                 course = data.getValue(Course.class);
                            }

                            switch (v.getId()){
                                case R.id.linerupdate:
                                    final EditText tenkhoahoc = v.findViewById(R.id.tenkhoahoc);
                                    final EditText mota = v.findViewById(R.id.mota);
                                    final EditText giangvien = v.findViewById(R.id.giangvien);
                                    final Button startday = v.findViewById(R.id.startday);
                                    final Button endday = v.findViewById(R.id.endday);
                                    try {
                                        tenkhoahoc.setText(course.getTenkhoahoc());
                                        mota.setText(course.getMota());
                                        giangvien.setText(course.getGiangvien());
                                        startday.setText(course.getStartday());
                                        endday.setText(course.getEndday());
                                    }catch (Exception e){

                                    }
                                    break;
                            }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }



    public void delete(final String index){
        final DatabaseReference datacourse = FirebaseDatabase.getInstance().getReference().child("Course");
        final DatabaseReference datastudent = FirebaseDatabase.getInstance().getReference().child("Student");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                    datacourse
                    .orderByKey()
                    .equalTo(index)
                    .addValueEventListener(new ValueEventListener() {
                        Course course;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot data : snapshot.getChildren()) {
                                course = data.getValue(Course.class);
                                Log.d("data", course.getMakhoahoc());
                            }
                            Query query1 = datastudent.orderByChild("makhoahoc")
                                    .equalTo(course.getMakhoahoc());
                                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.d("data", "query");
                                        String makh = null;
                                        for (DataSnapshot data : snapshot.getChildren()) {
                                            data.getRef().removeValue();
                                            makh = data.getValue(Course.class).getMakhoahoc();
                                        }
                                        datastudent.orderByChild("makhoahoc").equalTo(makh)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        snapshot.getRef().removeValue();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        firebaseDatabase.getReference().child("Course")
                .child(index)
                .removeValue();


    }

}
