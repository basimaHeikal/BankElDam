package com.ipda3.bankeldam.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.data.local.SharedPrefManager;

import java.util.Calendar;

import static com.ipda3.bankeldam.helper.Constants.SIGN_BIRTH_DATE;
import static com.ipda3.bankeldam.helper.Constants.SIGN_LAST_DONATE;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        int month1=month+1;
        String month2 =String.valueOf(month1);
        if(month1==1 ||month1==2||month1==3||month1==4||month1==5||month1==6||month1==7||month1==8||month1==9){
            month2 ="0"+month1;
        }


        String dialog = SharedPrefManager.getInstance(getActivity()).getKEY_DIALOG();
        String stringOfDate = year + "-" + month2 + "-" + day;

        if (dialog.equals(SIGN_BIRTH_DATE) ){
            setText(stringOfDate, R.id.SignTvBirthDate);
        }else if (dialog.equals(SIGN_LAST_DONATE) ){
            setText(stringOfDate, R.id.SignTvLasDonate);
        }

    }

    private void setText(String stringOfDate, int textViewId){
        TextView tv = (TextView) getActivity().findViewById(textViewId);
        tv.setText(stringOfDate);
    }
}