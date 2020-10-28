package com.example.myassignmentnangcao.Dao;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.example.myassignmentnangcao.Model.Course;
import com.example.myassignmentnangcao.Model.Student;
import com.example.myassignmentnangcao.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dao_Student {

    private Context context;
    private View v;

    public Dao_Student(Context context, View v) {
        this.context = context;
        this.v = v;
    }

    public void insert(final Student student) {
        DatabaseReference datastudent = FirebaseDatabase.getInstance().getReference().child("Student");
        datastudent.orderByChild("masinhvien")
                .equalTo(student.getMasinhvien())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            HashMap<String, Object> map = (HashMap<String, Object>) student.toMap();
                            DatabaseReference datastudent = FirebaseDatabase.getInstance().getReference().child("Student").push();
                            datastudent.setValue(map);
                        } else {
                            switch (v.getId()) {
                                case R.id.linerstudent:
                                    final EditText masv = v.findViewById(R.id.masinhvien);
                                    masv.setError("mã sinh viên đã tồn tại");
                                    break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    //doc list spinner
    public void readlist(){
        DatabaseReference datacourse = FirebaseDatabase.getInstance().getReference().child("Course");
        datacourse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> list = new ArrayList<>();
                if (snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        list.add(data.getValue(Student.class).getMakhoahoc());
                    }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.simple_spinner, list);
                            final Spinner makhoahoc = v.findViewById(R.id.makhoahoc);
                            makhoahoc.setAdapter(arrayAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        //doc gia tri cua vi tri do va set text (button updates)
    public void read(String index){
        final ArrayList<Course> list = new ArrayList<>();
        final DatabaseReference datacourse = FirebaseDatabase.getInstance().getReference().child("Student");
        datacourse.orderByKey()
                .equalTo(index)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Student student = null;
                        for(DataSnapshot data : snapshot.getChildren()){
                            student = data.getValue(Student.class);
                        }

                        switch (v.getId()){
                            case R.id.lineupdate:
                                try {
                                    final EditText tensinhvien = v.findViewById(R.id.tensinhvien);
                                    final EditText gioitinh = v.findViewById(R.id.gioitinh);
                                    final EditText ngaysinh = v.findViewById(R.id.ngaysinh);
                                    final Spinner makhoahoc = v.findViewById(R.id.makhoahoc);
                                    tensinhvien.setText(student.getTensinhvien());
                                    gioitinh.setText(student.getGioitinh());
                                    ngaysinh.setText(student.getNgaysinh());
                                    String mkhoahoc = student.getMakhoahoc();
                                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) makhoahoc.getAdapter();
                                    if (mkhoahoc != null) {
                                        int spinnerPosition = adapter.getPosition(mkhoahoc);
                                        makhoahoc.setSelection(spinnerPosition);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
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
        final DatabaseReference datastudent = FirebaseDatabase.getInstance().getReference().child("Student");
        datastudent
                .child(index)
                .removeValue();
    }




        public void update(Student student, String index){

        final DatabaseReference datastudent = FirebaseDatabase.getInstance().getReference().child("Student").child(index);

        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put("tensinhvien", student.getTensinhvien());
        updates.put("ngaysinh", student.getNgaysinh());
        updates.put("makhoahoc", student.getMakhoahoc());
        updates.put("gioitinh", student.getGioitinh());

        datastudent.updateChildren(updates);

    }
}