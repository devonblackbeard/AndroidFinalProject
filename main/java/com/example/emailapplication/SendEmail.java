package com.example.emailapplication;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.util.Properties;

//Use Javamail API to create my own email sender
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail extends AsyncTask<Void,Void,Void>
{

    //Declaring Variables
    Context context;
    private Session session;

    //Information to send email
    private String message;
    private String email;
    private String subject;


    //Progress dialog to show while sending email
    ProgressDialog progressDialog;

    //Class Constructor
    public SendEmail(Context context, String email, String subject, String message)
    {
        //Initializing variables

        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute()
    {

        super.onPreExecute();

        //2 new lines
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending message","just a few seconds...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Configuration.EMAIL, Configuration.PASSWORD);
                    }
                });

        try {

            //Creating MimeMessage object

            //this uses the JAR files
            MimeMessage emailMessage = new MimeMessage(session);
            //Setting sender address
            emailMessage .setFrom(new InternetAddress(Configuration.EMAIL));
            //Adding receiver
            emailMessage .addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            emailMessage .setSubject(subject);
            //Adding message
            emailMessage .setText(message);

            //Sending email
            Transport.send(emailMessage );

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
