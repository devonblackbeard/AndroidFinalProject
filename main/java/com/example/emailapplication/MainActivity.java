package com.example.emailapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    static final String TAG = "MainActivity";

    //Declaring EditText
    EditText editTextEmail;
    EditText editTextSubject;
    EditText editTextMessage;

    SharedPreferences prefs;
    View mainView;

    //Send Email button
    Button buttonSend;
    //Automatic activity button
    Button buttonAuto;

    String address;
    String subject;
    String message;
    String date;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        Log.d(TAG, "In OnCreate of MAIN ACTIVITY");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get back the data
        intent = getIntent();

        //Get Data from Main Activity- will return this
        address= intent.getStringExtra("address");
        subject= intent.getStringExtra("subject");
        message= intent.getStringExtra("message");
        date = intent.getStringExtra("sendDate");


        //Return Data from the AutoEmail Class
        if (address!= null)
        {
            //Toast.makeText(this, "not null address", Toast.LENGTH_LONG).show();

//            Toast.makeText(this, "address"+ address, Toast.LENGTH_SHORT).show();
////            Toast.makeText(this, "subject" + subject, Toast.LENGTH_SHORT).show();
////            Toast.makeText(this, "message" + message, Toast.LENGTH_SHORT).show();
////            Toast.makeText(this, "sendate" + date + message, Toast.LENGTH_SHORT).show();
            AutoSend(address, subject, message, date);
        }

        else
        {
            Toast.makeText(this, "The address is null", Toast.LENGTH_SHORT).show();
        }


        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonAuto = (Button) findViewById(R.id.buttonAutomatic);

        //Adding click listener
        buttonSend.setOnClickListener(this);
        buttonAuto.setOnClickListener(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mainView = findViewById(R.id.main_linear_layout);

        String bgColor = prefs.getString("main_bg_color", "#f6f6f6");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            ActionBar bar = this.getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc6666")));

            bar.setDisplayHomeAsUpEnabled(true);

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //top bar
            window.setStatusBarColor(Color.parseColor("#f6f6f6"));
        }
    }


    @Override
    protected void onResume()
    {
        String bgColor = prefs.getString("main_bg_color", "#cc6666");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
        super.onResume();
    }

    private void SendEmail()
    {
        //Getting content for email
        String address = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Ensure data is entered
        if (address.isEmpty() || message.isEmpty())
        {
            Toast.makeText(this, "Please enter a recipient and a message", Toast.LENGTH_LONG).show();
        }

        else
        {
            //Creating SendMail object
            SendEmail send = new SendEmail(this, address, subject, message);
            send.execute();
            //Clear the screen
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d(TAG, "reached onOptionsItems");

        switch (item.getItemId())
        {
            case R.id.menu_item_preferences:
            {
                Intent intent = new Intent(this, PrefsActivity.class);
                this.startActivity(intent);
                break;
            }

            case R.id.menu_item_automaticEmail:
            {
                Intent intent = new Intent(this, AutoEmail.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonSend:

//                Date currentTime = Calendar.getInstance().getTime();
//                Toast.makeText(this, "time:"+currentTime, Toast.LENGTH_SHORT).show();

//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                String currentDateandTime = sdf.format(new Date());
                //Toast.makeText(this, "time: "+ currentDateandTime, Toast.LENGTH_SHORT).show();
                //prints 13:35

//                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//                format.setTimeZone (TimeZone.getDefault());

//                String n = "10:30";
//                ParsePosition pos = new ParsePosition(0);
//
//                Date sample_date = format.parse(n,pos);
//                Toast.makeText(this, "date is now: "+ sample_date, Toast.LENGTH_SHORT).show();
//
//
//                long m = sample_date.getTime();
//                Toast.makeText(this, "m: "+ m, Toast.LENGTH_SHORT).show();
                //SimpleDateFormat curr = new SimpleDateFormat("HH:mm");


                //this prints time as a string.
//                String time = d.format(sample_date);
//                Toast.makeText(this, "time:" + time, Toast.LENGTH_SHORT).show();


                SendEmail();
                break;


            case R.id.buttonAutomatic:
                PrepareEmailForLater();
                break;
        }
    }


    public void PrepareEmailForLater()
    {
        //Getting content for email
        String address = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Ensure data is entered
        if (address.isEmpty() || message.isEmpty())
        {
            Toast.makeText(this, "Please enter a recipient and a message", Toast.LENGTH_LONG).show();
        }

        else
        {
            //give it email data, return data and dateTime

            intent = new Intent(this, AutoEmail.class);
            intent.putExtra("address", address);
            intent.putExtra("subject", subject);
            intent.putExtra("message", message);

            startActivity(intent);
        }
    }

    //This will send the Email when the entered Date for AUTO SEND has been reached.
    // AutoSend(address, subject, message, date);

    public void AutoSend(String add, String sub, String mess, String autoDate)
    {
        //Toast.makeText(this, "BACK HERE!@", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "In AUTO SEND **********    ************    **********");
        //set up date checker to know when to send the email


        //can we format a string into a date object?
        //String sDate1="11/04/2019/14:20";

        Date timestamp = new Date ();

        try {
            Date alertDate = new SimpleDateFormat("dd/MM/yyyy/HH:mm").parse(autoDate);
           // Toast.makeText(this, "date1: "+ alertDate, Toast.LENGTH_SHORT).show();

            //Get difference between our two dates (both Date objects)
            long mills = alertDate.getTime() - timestamp.getTime();
            Toast.makeText(this, "Difference: "+ mills, Toast.LENGTH_LONG).show();


            final ScheduledExecutorService scheduler =
                    Executors.newScheduledThreadPool(1);

            scheduler.schedule(
                    new Runnable()
                    {
                        public void run()
                        {
                            SendEmail send = new SendEmail(getApplicationContext(), address, subject, message);
                            send.execute();
                            DelayedEmail();
                            Toast.makeText(MainActivity.this, "Should have sent", Toast.LENGTH_SHORT).show();
//                            monitorTask.cancel(true);

                        }
                    }, mills, TimeUnit.MILLISECONDS);

            //gives a difference in miliseconds. This is my Delay in Sending the email.
        } 
        
        catch (ParseException e) {
            //Toast.makeText(this, "in Catch", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            
        }
    }


    private void DelayedEmail()
    {
        Log.d(TAG, "IN DELAYED************************");
        Toast.makeText(this, "Did you reach here???", Toast.LENGTH_SHORT).show();
        SendEmail send = new SendEmail(this, address, subject, message);
        send.execute();

        Toast.makeText(this, "EMail should have sent", Toast.LENGTH_SHORT).show();
    }

}