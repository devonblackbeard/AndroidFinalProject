package com.example.emailapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;


public class PrefsActivity extends PreferenceActivity
{
    static final String TAG = "PrefsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "in Oncreate of PrefsActivity");

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}