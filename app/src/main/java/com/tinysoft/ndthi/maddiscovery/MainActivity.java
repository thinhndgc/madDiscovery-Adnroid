package com.tinysoft.ndthi.maddiscovery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText eventName;
    private EditText location;
    private EditText eventDate;
    private EditText startTime;
    private EditText orgName;
    private Button btnCreate;
    private Events ev;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventName = (EditText) findViewById(R.id.txtEventName);
        location = (EditText) findViewById(R.id.txtLocation);
        eventDate = (EditText) findViewById(R.id.txtDate);
        startTime = (EditText) findViewById(R.id.txttime);
        orgName = (EditText) findViewById(R.id.txtOrgName);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        eventDate.setOnClickListener(this);
        startTime.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == eventDate) {

            // Get Current Date
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            eventDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == startTime) {

            // Get Current Time
            Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            startTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnCreate) {
            String txtEventNameVal = eventName.getText().toString();
            String txtLocationVal = location.getText().toString();
            String txtEventDateVal = eventDate.getText().toString();
            String txtTimeVal = startTime.getText().toString();
            String txtOrgNameVal = orgName.getText().toString();


            if (txtEventNameVal.equals("") || txtLocationVal.equals("") || txtEventDateVal.equals("") || txtOrgNameVal.equals("") || txtTimeVal.equals("")) {
                openDialog("Error", "All field are require!");
            } else {
                String[] dateArray = txtEventDateVal.split("-");
                int evD = Integer.parseInt(dateArray[0]);
                int evM = Integer.parseInt(dateArray[1]);
                int evY = Integer.parseInt(dateArray[2]);
                String[] timeArray = txtTimeVal.split(":");
                int evH = Integer.parseInt(timeArray[0]);
                int evMn = Integer.parseInt(timeArray[1]);

                if (checkDate(evD, evM, evY)) {
                    if (checkDateForTime(evD, evM, evY)) {
                        if (checkTime(evH, evMn)){
                            ev = new Events(txtEventNameVal, txtLocationVal, txtEventDateVal, txtTimeVal, txtOrgNameVal);
                            openConfirmDialog("Success", "All field are validated!");

                        }else {
                            openDialog("Error", "Start time is not valid!");
                        }
                    }else {

                        ev = new Events(txtEventNameVal, txtLocationVal, txtEventDateVal, txtTimeVal, txtOrgNameVal);
                        openConfirmDialog("Success", "All field are validated!");
                    }
                } else {
                    openDialog("Error", "Event date is invalid!");
                }
            }
        }
    }

    private void navicationEvent(){
        Intent intent = new Intent(MainActivity.this,Details.class).putExtra("eventObj",ev);
        startActivity(intent);
    }

    private int[] getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df3 = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df3.format(c.getTime());
        String[] fomattedDateArray = formattedDate.split("-");
        int[] currentDate = {Integer.parseInt(fomattedDateArray[0]), Integer.parseInt(fomattedDateArray[1]), Integer.parseInt(fomattedDateArray[2])};
        return currentDate;
    }

    private boolean checkDate(int evD, int evM, int evY) {
        // Get Current Date
        int[] currentDate = getCurrentDate();
        int cYear = currentDate[2];
        int cMonth = currentDate[1];
        int cDay = currentDate[0];

        if (evY < cYear || (evY == cYear && evM < cMonth) || (evY == cYear && evM == cMonth && evD < cDay)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkTime(int evH, int evMn) {
        // Get Current Time
        Calendar c = Calendar.getInstance();
        int cHour = c.get(Calendar.HOUR_OF_DAY);
        int cMinute = c.get(Calendar.MINUTE);
        if (evH < cHour || (evH == cHour && evMn < cMinute)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDateForTime(int evD, int evM, int evY){
        // Get Current Date
        int[] currentDate = getCurrentDate();
        int cYear = currentDate[2];
        int cMonth = currentDate[1];
        int cDay = currentDate[0];
        if (evY == cYear && evM == cMonth && evD  == cDay){
            return true;
        }else {
            return false;
        }
    }

    private void openDialog(String tittle, String messages) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(tittle);

        // set dialog message
        alertDialogBuilder
                .setMessage(messages)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        return;
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void openConfirmDialog(String tittle, String messages) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(tittle);
        alertDialogBuilder
                .setMessage(messages)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        navicationEvent();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
