package com.example.phase2.browse_trades_activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.highabstract.BundleActivity;

import java.time.LocalDate;
import java.util.Objects;

public class EditMeetingActivity extends BundleActivity {

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private MeetingManager meetingManager;
    private String date;
    private Integer trade;
    private String currentTrader;
    private LocalDate newDate;
    private boolean online;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        currentTrader = (String) getUseCase(USERNAMEKEY);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        trade = (Integer) bundle.getSerializable("Trade");
        online = (boolean) bundle.get("Online");

        EditText location=findViewById(R.id.meetingLocationEdit);
        TextView newLocation = findViewById(R.id.newLocation);
        if(online){
            location.setVisibility(View.GONE);
            newLocation.setVisibility(View.GONE);
        }

        Button button = findViewById(R.id.submit_button);
        final TextView datePickerText = findViewById(R.id.editTextDate);
        setDatePickerText(datePickerText);
        setButtonLister(button, datePickerText);
    }

    /**
     * Submits the edited meeting.
     */
    public void onSubmitClick(){
        replaceUseCase(meetingManager);
        super.onBackPressed();
    }
    //Some of the code here was used from the following source:
    /*
    Author: Coding with Mitch
    Date: August 5th 2020
    Title: Android Beginner Tutorial #25 - DatePicker Dialog
    Web Address:https://www.youtube.com/watch?v=hwe1abDO2Ag
     */
    private void setDatePickerText(final TextView datePickerText){
        datePickerText.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  int day = LocalDate.now().getDayOfMonth();
                  int month = LocalDate.now().getMonthValue()-1;
                  int year = LocalDate.now().getYear();
                  DatePickerDialog datePicker = new DatePickerDialog(
                          EditMeetingActivity.this,
                          android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,year,
                          month,day);
                  Objects.requireNonNull(datePicker.getWindow()).setBackgroundDrawable(new
                          ColorDrawable(Color.TRANSPARENT));
                  datePicker.show();
              }
          }
        );
        onDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                date = year + "-" + month+"-"+day;
                newDate = LocalDate.of(year,month,day);
                datePickerText.setText(date);
            }
        };
    }

    private void setButtonLister(Button button, final TextView datePickerText){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText location=findViewById(R.id.meetingLocationEdit);
                if(online){
                    if(datePickerText.getText().toString().equals("+ Select a new date")){
                        Toast.makeText(EditMeetingActivity.this, R.string.invalid_date_edit, Toast.LENGTH_LONG).show();
                    }
                    else{
                        editMeeting();
                        meetingManager.editLocation(trade, meetingManager.getMeetingLocation(trade));
                        onSubmitClick();
                    }
                }
                else{
                    if(!location.getText().toString().isEmpty() && !location.getText().toString().equals("Please Enter a Location")){
                        if(datePickerText.getText().toString().equals("+ Select a new date")){
                            Toast.makeText(EditMeetingActivity.this, R.string.invalid_date_edit, Toast.LENGTH_LONG).show();
                        }
                        else {
                            meetingManager.editLocation(trade, location.getText().toString());
                            editMeeting();
                            onSubmitClick();
                        }
                    }
                    else{
                        if(datePickerText.getText().toString().equals("Please Select a Date")){
                            Toast.makeText(EditMeetingActivity.this, R.string.invalid_date_location, Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(EditMeetingActivity.this, R.string.invalid_location_edit, Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });
    }
    
    private void editMeeting(){
        meetingManager.editDate(trade, newDate);
        meetingManager.increaseNumEdit(currentTrader, trade);
        meetingManager.setBothDisagree(trade);
    }

}