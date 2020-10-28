package com.example.myassignmentnangcao.Fragment.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myassignmentnangcao.Dao.Dao_Course;
import com.example.myassignmentnangcao.Dao.Dao_Student;
import com.example.myassignmentnangcao.MainActivity;
import com.example.myassignmentnangcao.Model.Course;
import com.example.myassignmentnangcao.Model.Student;
import com.example.myassignmentnangcao.Other.lichPicker;
import com.example.myassignmentnangcao.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class studentAdapter extends FirebaseRecyclerAdapter<Student, studentAdapter.StudentHolder> {
    private Context context;
     SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    studentAdapter adapter;

    public studentAdapter(@NonNull FirebaseRecyclerOptions<Student> options,Context context) {
        super(options);
        this.context = context;

    }


    @Override
    protected void onBindViewHolder(@NonNull StudentHolder holder, final int position, @NonNull Student model) {
        holder.mmasv.setText(model.getMasinhvien());
        holder.mtensv.setText(model.getTensinhvien());
        holder.mngaysinh.setText(model.getNgaysinh());
        holder.mgioitinh.setText(model.getGioitinh());
        holder.mmakhoahoc.setText(model.getMakhoahoc());
        holder.btnrepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repairstudent(position);

            }
        });

        final String index = getRef(position).getKey();

        holder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Snackbar.make(((Activity)context).findViewById(R.id.relativestudent),"ban co chac chan xoa",5000)
                            .setActionTextColor(Color.GREEN)
                            .setAction("Yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Dao_Student dao_student = new Dao_Student(context, v);
                                    dao_student.delete(index);
                                    notifyDataSetChanged();
                                }
                            }).show();

                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        });

    }



    private void repairstudent(final int position){
        final DialogPlus dialog = DialogPlus.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.dialog_student_update))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();

        final View holderView = dialog.getHolderView();


        final EditText tensinhvien = holderView.findViewById(R.id.tensinhvien);
        final EditText gioitinh = holderView.findViewById(R.id.gioitinh);
        final EditText ngaysinh = holderView.findViewById(R.id.ngaysinh);
        final Spinner makhoahoc = holderView.findViewById(R.id.makhoahoc);
        final Button btnrepair = holderView.findViewById(R.id.chinhsuasv);
        final Button huy = holderView.findViewById(R.id.huy);
        final Button btnngaysinh = holderView.findViewById(R.id.btnngaysinh);


        btnngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new lichPicker(view);
                newFragment.show(((FragmentActivity)context).getSupportFragmentManager(), "timePicker3");

            }
        });

        //doc list spinner
        String index = getRef(position).getKey();
        Dao_Student dao = new Dao_Student(context,holderView);
        dao.readlist();
        dao.read(index);



        btnrepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String mtensinhvien = tensinhvien.getText().toString();
                    String mgioitinh = gioitinh.getText().toString();
                    sdf.parse(ngaysinh.getText().toString());
                    String mmakhoahoc = makhoahoc.getSelectedItem().toString();
                    String date = ngaysinh.getText().toString();
                    Log.d("dataaaa",ngaysinh.getText().toString());


                    Student student = new Student(null,mtensinhvien,date,mgioitinh,mmakhoahoc);
                    String index = getRef(position).getKey();

                    Dao_Student dao = new Dao_Student(context,view);
                    dao.update(student,index);


                    dialog.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                    ngaysinh.setError("sai ngay sinh");
                }
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        notifyDataSetChanged();

        dialog.show();
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentHolder(view);
    }

    public static class StudentHolder extends RecyclerView.ViewHolder{
        TextView mmasv,mtensv,
                mngaysinh,mgioitinh,mmakhoahoc;
        Button btnrepair,btndel;
        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            mmasv = itemView.findViewById(R.id.masv);
            mtensv = itemView.findViewById(R.id.tensv);
            mngaysinh = itemView.findViewById(R.id.ngaysinh);
            mgioitinh = itemView.findViewById(R.id.gioitinh);
            mmakhoahoc = itemView.findViewById(R.id.makhoahoc);
            btnrepair = itemView.findViewById(R.id.btnchinhsua);
            btndel = itemView.findViewById(R.id.btnxoa);
        }
    }
}
