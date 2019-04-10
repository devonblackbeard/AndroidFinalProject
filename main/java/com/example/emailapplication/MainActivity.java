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

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    static final String TAG = "MainActivity";

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    SharedPreferences prefs;
    View mainView;

    //Send button
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        //Adding click listener
        buttonSend.setOnClickListener(this);

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

    private void sendEmail()
    {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendEmail send = new SendEmail(this, email, subject, message);

        //Executing sendmail to send email
        send.execute();
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
        sendEmail();
    }

}