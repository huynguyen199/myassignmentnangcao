package com.example.myassignmentnangcao.Other;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myassignmentnangcao.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class lichPicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {



    private View view;
    public lichPicker(View view) {
        this.view = view;
    }

    @Override
    public void onDateSet(DatePicker view2, int year, int month, int dayOfMonth) {
        String date = null;
        if(month < 10 && dayOfMonth < 10){
            date = year+"-0"+month+"-0"+dayOfMonth;
        }
        else if(month < 10){
            date = year+"-0"+month+"-"+dayOfMonth;
        }
        else if(dayOfMonth < 10){
            date = year+"-"+month+"-0"+dayOfMonth;
        }
        else{
            date = year+"-"+month+"-"+dayOfMonth;
        }
        final Button mstartday = view.findViewById(R.id.startday);
        final Button mendday = view.findViewById(R.id.endday);
        EditText edtngaysinh = view.findViewById(R.id.ngaysinh);


        if(view == mstartday) {
            mstartday.setText(date);

        }
        if(view == mendday) {
            mendday.setText(date);
        }
        if(view == edtngaysinh){
            Log.d("date","vao day chua");
            edtngaysinh.setText(date);
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

}
