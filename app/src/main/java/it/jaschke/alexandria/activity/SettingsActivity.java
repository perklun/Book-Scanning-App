package it.jaschke.alexandria.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import it.jaschke.alexandria.R;

/**
 * Created by saj on 27/01/15.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


    }
}
