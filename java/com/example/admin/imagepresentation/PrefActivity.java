package com.example.admin.imagepresentation;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class PrefActivity extends PreferenceActivity {

    CheckBoxPreference chbPref;
    ListPreference listPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PreferenceScreen rootScreen = getPreferenceManager().createPreferenceScreen(this);
        setPreferenceScreen(rootScreen);

        chbPref = new CheckBoxPreference(this);
        chbPref.setTitle("Автозагрузка");
        chbPref.setKey("chbPref_programAutorun");
        rootScreen.addPreference(chbPref);

        chbPref = new CheckBoxPreference(this);
        chbPref.setTitle("Показывать при зарядке");
        chbPref.setKey("chbPref_startWhenCharging");
        rootScreen.addPreference(chbPref);

        listPref = new ListPreference(this);
        listPref.setTitle("Интервал между слайдами");
        listPref.setSummary("интервал в секундах");
        listPref.setEntries(R.array.seconds);
        listPref.setEntryValues(R.array.seconds);
        listPref.setKey("listPref_seconds");
        rootScreen.addPreference(listPref);
    }
}
