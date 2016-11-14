package com.example.admin.imagepresentation;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class PrefActivity extends PreferenceActivity {

    CheckBoxPreference chbPrefService;
    CheckBoxPreference chbPrefServiceAutoload;
    CheckBoxPreference chbPrefProgramAutoload;
    CheckBoxPreference chbPrefStartWhenCharging;
    ListPreference listPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PreferenceScreen rootScreen = getPreferenceManager().createPreferenceScreen(this);
        setPreferenceScreen(rootScreen);

        chbPrefService = new CheckBoxPreference(this);
        chbPrefService.setTitle("Сервис");
        chbPrefService.setSummaryOff("Выключено");
        chbPrefService.setSummaryOn("Включено");
        chbPrefService.setKey("chbPref_service");
        chbPrefService.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                chbPrefServiceAutoload.setEnabled(chbPrefService.isChecked());
                chbPrefProgramAutoload.setEnabled(chbPrefService.isChecked());
                chbPrefStartWhenCharging.setEnabled(chbPrefService.isChecked());
                return false;
            }
        });
        rootScreen.addPreference(chbPrefService);

        chbPrefServiceAutoload = new CheckBoxPreference(this);
        chbPrefServiceAutoload.setTitle("Запуск сервиса при запуске устройства");
        chbPrefServiceAutoload.setEnabled(chbPrefService.isChecked());
        chbPrefServiceAutoload.setKey("chbPref_serviceAutoload");
        rootScreen.addPreference(chbPrefServiceAutoload);

        chbPrefProgramAutoload = new CheckBoxPreference(this);
        chbPrefProgramAutoload.setTitle("Запуск программы при запуске устройства");
        chbPrefProgramAutoload.setEnabled(chbPrefService.isChecked());
        chbPrefProgramAutoload.setKey("chbPref_programAutoload");
        rootScreen.addPreference(chbPrefProgramAutoload);

        chbPrefStartWhenCharging = new CheckBoxPreference(this);
        chbPrefStartWhenCharging.setTitle("Показывать при зарядке");
        chbPrefStartWhenCharging.setEnabled(chbPrefService.isChecked());
        chbPrefStartWhenCharging.setKey("chbPref_startWhenCharging");
        rootScreen.addPreference(chbPrefStartWhenCharging);

        listPref = new ListPreference(this);
        listPref.setTitle("Интервал между слайдами");
        listPref.setSummary("интервал в секундах");
        listPref.setEntries(R.array.seconds);
        listPref.setEntryValues(R.array.seconds);
        listPref.setKey("listPref_seconds");
        rootScreen.addPreference(listPref);
    }
}
