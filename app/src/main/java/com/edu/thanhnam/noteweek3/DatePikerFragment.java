package com.edu.thanhnam.noteweek3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by ThanhNam on 03/04/2017.
 */

public class DatePikerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return new DatePickerDialog(getActivity(), this, day, month, year);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        EditText edtBirthday = (EditText) getActivity().findViewById(R.id.edt_time);
        String birthday = dayOfMonth + "/" + (month + 1) + "/" + year + " ";
        edtBirthday.setText(birthday);

    }

}
