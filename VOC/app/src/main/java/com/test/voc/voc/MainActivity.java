package com.test.voc.voc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity implements OnClickListener {
    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private String val;
    private EditText et;
    private TextView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // mDateButton = (Button) findViewById(R.id.date_button);
        ib = (ImageButton) findViewById(R.id.imageButton1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.editText);
        results = (TextView) findViewById(R.id.result);
        ib.setOnClickListener(this);

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(System.currentTimeMillis());
        int days = time.get(Calendar.DAY_OF_WEEK);
        String friday = String.valueOf(days);

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        if (friday.equals(6)) {
            mBuilder.setSmallIcon(R.drawable.v)
                    .setContentTitle("Greetings")
                    .setContentText("Have a Good Day!")
                    .setSound(alarmSound);
        }

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int mId = 0;
        mNotificationManager.notify(mId, mBuilder.build());
    }

    @Override
    public void onClick(View v) {
        showDialog(0);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            et.setText(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
            String dtStart = et.getText().toString();
            String day_name = null;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

            Calendar now = Calendar.getInstance();


            now.set(Calendar.YEAR,selectedYear);
            now.set(Calendar.MONTH,selectedMonth);//0- january ..4-May
            now.set(Calendar.DATE, selectedDay);

            System.out.println("Current week of month is : " +
                    now.get(Calendar.WEEK_OF_YEAR));
            try {
                Date date = format.parse(dtStart);
                SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                day_name = outFormat.format(date);
                if( day_name.equals("Saturday")  ) {
                    if( now.get(Calendar.WEEK_OF_YEAR) % 2 != 0)
                        results.setText("Your selected Saturday is Holiday...!");
                    else
                        results.setText("Oh..Your selected Saturday is Working day...!");
                }else{
                    results.setText("Please Select Saturday");
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    };

}