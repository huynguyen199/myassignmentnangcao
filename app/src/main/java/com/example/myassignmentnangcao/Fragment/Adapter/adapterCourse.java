package com.example.myassignmentnangcao.Fragment.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myassignmentnangcao.Dao.Dao_Course;
import com.example.myassignmentnangcao.MainActivity;
import com.example.myassignmentnangcao.Model.Course;
import com.example.myassignmentnangcao.Other.lichPicker;
import com.example.myassignmentnangcao.R;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class adapterCourse extends FirebaseRecyclerAdapter<Course, adapterCourse.CourseHolder> {
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");


    public adapterCourse(@NonNull FirebaseRecyclerOptions<Course> options,Context context) {
        super(options);
        this.context = context;
    }



    @Override
    protected void onBindViewHolder(@NonNull final CourseHolder holder, final int position, @NonNull Course model) {


        holder.mmakhoahoc.setText(model.getMakhoahoc());
        holder.mtenkhoahoc.setText(model.getTenkhoahoc());
        holder.mgiangvien.setText(model.getGiangvien());
        holder.ngaybatdau.setText(model.getStartday());
        holder.ngayketthuc.setText(model.getEndday());
        holder.mmota.setText(model.getMota());



        holder.btnrepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.dialog_course_update))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                final View holderView = dialog.getHolderView();

                String index = getRef(position).getKey();
                final Dao_Course dao_course = new Dao_Course(context,holderView);
                dao_course.read(index);

                final EditText tenkhoahoc = holderView.findViewById(R.id.tenkhoahoc);
                final EditText mota = holderView.findViewById(R.id.mota);
                final EditText giangvien = holderView.findViewById(R.id.giangvien);
                final Button startday = holderView.findViewById(R.id.startday);
                final Button endday = holderView.findViewById(R.id.endday);
                final Button btnupdate = holderView.findViewById(R.id.btncapnhat);
                final Button btnhuy = holderView.findViewById(R.id.btnclose);

                startday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment newFragment = new lichPicker(view);
                        newFragment.show( ((AppCompatActivity) context).getSupportFragmentManager(), "timePicker4");
                    }
                });

                endday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment newFragment = new lichPicker(view);
                        newFragment.show( ((AppCompatActivity) context).getSupportFragmentManager(), "timePicker5");
                    }
                });


                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            sdf.parse(startday.getText().toString());
                            sdf.parse(endday.getText().toString());

                            String mtenkhoahoc = tenkhoahoc.getText().toString();
                            String mgiangvien = giangvien.getText().toString();
                            String mstartday = startday.getText().toString();
                            Log.d("data", endday.getText().toString());
                            Log.d("data", startday.getText().toString());

                            String mendday = endday.getText().toString();
                            String mmota = mota.getText().toString();
                            String index = getRef(position).getKey();

                            Course course = new Course(null, mtenkhoahoc, mgiangvien, mstartday, mendday, mmota);

                            dao_course.update(course, index);
                            dialog.dismiss();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                notifyDataSetChanged();
                dialog.show();
            }
        });

        final String index = getRef(position).getKey();
        holder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Snackbar.make(((Activity)context).findViewById(R.id.relativecourser),"ban co chac chan xoa",5000)
                            .setActionTextColor(Color.GREEN)
                            .setAction("Yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Dao_Course dao_course = new Dao_Course(context, v);
                                    dao_course.delete(index);
                                    notifyDataSetChanged();
                                }
                            }).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseHolder(view);
    }

    public static class CourseHolder extends RecyclerView.ViewHolder{
        TextView mmakhoahoc,mtenkhoahoc,
        mgiangvien,ngaybatdau,ngayketthuc,mmota;
        Button btnrepair,btndel;
        RelativeLayout relativeLayout;
        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relativecourser);
            mmakhoahoc = itemView.findViewById(R.id.makhoahoc);
            mtenkhoahoc = itemView.findViewById(R.id.tenkhoahoc);
            mgiangvien = itemView.findViewById(R.id.giangvien);
            ngaybatdau = itemView.findViewById(R.id.startday);
            ngayketthuc = itemView.findViewById(R.id.endday);
            mmota = itemView.findViewById(R.id.mota);
            btnrepair = itemView.findViewById(R.id.btnchinhsua);
            btndel = itemView.findViewById(R.id.btnxoa);
        }
    }
}
