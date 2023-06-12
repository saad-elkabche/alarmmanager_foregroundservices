package com.saadev.adhan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Clock_Setting extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, NumberPicker.OnValueChangeListener {
NumberPicker numberPickerHour,numberPickerMinutes;
TextView txtView;
Switch switchButton;
RadioButton _5Minutes,_10Minutes,_15minutes;
private static final int id5min=005;
private static final int id10min=010;
private static final int id15min=015;
RadioGroup container;
    Calendar calCurrent,calSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_setting);

        calCurrent=Calendar.getInstance();
        calSet=Calendar.getInstance();

        numberPickerHour=findViewById(R.id.PickHour);
        numberPickerMinutes=findViewById(R.id.PickMinutes);
        txtView=findViewById(R.id.txtTime);
        switchButton=findViewById(R.id.repeating);
        container=findViewById(R.id.typesRepeating);

        numberPickerHour.setMaxValue(23);
        numberPickerHour.setMinValue(0);

        numberPickerMinutes.setMaxValue(59);
        numberPickerMinutes.setMinValue(0);


        int hour =calCurrent.get(Calendar.HOUR_OF_DAY);
        int minutes =calCurrent.get(Calendar.MINUTE);

        numberPickerHour.setValue(hour);
        numberPickerMinutes.setValue(minutes);

        numberPickerHour.setOnValueChangedListener(this);
        numberPickerMinutes.setOnValueChangedListener(this);
        switchButton.setOnCheckedChangeListener(this);


        onValueChange(null,0,0);


         _5Minutes=new RadioButton(this);
        _5Minutes.setId(id5min);
        _5Minutes.setText("5 Minutes");

        _10Minutes=new RadioButton(this);
        _10Minutes.setId(id10min);
        _10Minutes.setText("10 Minutes");

        _15minutes=new RadioButton(this);
        _15minutes.setId(id15min);
        _15minutes.setText("15 Minutes");

       /* _5Minutes.setOnCheckedChangeListener(this);
        _10Minutes.setOnCheckedChangeListener(this);
        _15minutes.setOnCheckedChangeListener(this);*/



    }

    private void hideOptions() {
       container.removeAllViews();
    }

    private void showOptions() {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin=10;
        container.addView(_5Minutes,params);
        container.addView(_10Minutes,params);
        container.addView(_15minutes,params);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            calCurrent=Calendar.getInstance();
            String timeRemaining="";
            calSet.set(Calendar.HOUR_OF_DAY,numberPickerHour.getValue());
            calSet.set(Calendar.MINUTE,numberPickerMinutes.getValue());
            calSet.set(Calendar.SECOND,0);

            Date d=new Date(calSet.getTime().getTime()-calCurrent.getTime().getTime());
            int hour=d.getHours();
            int minutes=d.getMinutes();

            if(hour==0){
                timeRemaining=minutes+" Minutes";
            }else if(minutes==0){
                timeRemaining=hour+" Hours";
            }else{
                timeRemaining=hour+" Hours and "+minutes+" Minutes";
            }
        txtView.setText(timeRemaining);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (switchButton.isChecked()) {
            showOptions();
            _5Minutes.setChecked(true);
        } else {
            hideOptions();
        }
    }

    public void addClock(View view) {
        String repeting=switchButton.isChecked()?"t":"f";
        String clock=numberPickerHour.getValue()+":"+numberPickerMinutes.getValue()+"/E"+"/"+repeting;
        SharedPreferences mySharedFile=getSharedPreferences(MainActivity.fileName,MODE_PRIVATE);
        int index=mySharedFile.getInt("last",0);
        index++;
        SharedPreferences.Editor editor = mySharedFile.edit();
        editor.putString(String.valueOf(index), clock);
        editor.putInt("last",index);
        editor.apply();
        int nbMinutes=0;
        if(calSet.before(Calendar.getInstance())){
            calSet.set(Calendar.DATE,1);
        }
        if(switchButton.isChecked()){

            for (int i = 0; i < container.getChildCount(); i++) {
                if(((RadioButton)container.getChildAt(i)).isChecked()){
                    nbMinutes=(i+1)*5;
                }
            }
        }
        setAlarm(calSet,nbMinutes);
    }

    private void setAlarm(Calendar calSet, int nbMinutes) {
        Intent intent=new Intent(this,AlarmReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,001,intent,PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        if(nbMinutes==0){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calSet.getTimeInMillis(),pendingIntent);
        }
        else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calSet.getTimeInMillis(),nbMinutes*60*1000,pendingIntent);
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },5000);*/
    }
}