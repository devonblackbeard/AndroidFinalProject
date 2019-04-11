package com.example.emailapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AutoEmail extends AppCompatActivity implements View.OnClickListener
{
    static final String TAG = "AutoEmail";

    EditText setDate;

    String address,subject,message;
    String autoDate;

    Button buttonLater;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "IN ON CREATE of AUTO ***************************");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_email);

        buttonLater =(Button)findViewById(R.id.buttonSaveAlert);

        buttonLater.setOnClickListener(this);
        setDate = findViewById(R.id.setDate);

        Intent intent= getIntent();

        //Get Data from Main Activity- will return this
        address= intent.getStringExtra("address");
        subject= intent.getStringExtra("subject");
        message= intent.getStringExtra("message");
    }


    @Override
    public void onClick(View v)
    {
        Log.d(TAG,"inOnClick of AUTO EMAIL**************************");
        autoDate = setDate.getText().toString();
        //Toast.makeText(this, "The date in string is "+ autoDate, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);

        //Return the main data
        intent.putExtra("address", address);
        intent.putExtra("subject", subject);
        intent.putExtra("message", message);

        //Also give back the Date user wants to send the email (as string)
        intent.putExtra("sendDate", autoDate);

        startActivity(intent);

    }


}
