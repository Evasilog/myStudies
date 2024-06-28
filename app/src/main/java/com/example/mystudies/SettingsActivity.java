package com.example.mystudies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ImageButton backIcon = findViewById(R.id.settings_back_icon);

        backIcon.setOnClickListener(v -> {
            Intent intent1 = new Intent();
            intent1.putExtra("fragment","courses");
            setResult(RESULT_OK, intent1);
            finish();
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            ListPreference preferenceTheme = findPreference("preference_theme");

            // check and apply theme
            String theme = sharedPreferences.getString("preference_theme", "default");
            switch (theme) {
                case "default":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    if (preferenceTheme != null) {
                        preferenceTheme.setSummary(preferenceTheme.getEntry());
                    }
                    break;
                case "light":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    if (preferenceTheme != null) {
                        preferenceTheme.setSummary(preferenceTheme.getEntry());
                    }
                    break;
                case "dark":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    if (preferenceTheme != null) {
                        preferenceTheme.setSummary(preferenceTheme.getEntry());
                    }
                    break;
            }

            if (preferenceTheme != null) {
                preferenceTheme.setOnPreferenceChangeListener((preference, newValue) -> {
                    String items = (String) newValue;
                    if (preference.getKey().equals("preference_theme")) {
                        switch (items) {
                            case "default":
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                break;
                            case "light":
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                break;
                            case "dark":
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                break;
                        }
                        ListPreference lP = (ListPreference) preference;
                        lP.setSummary(lP.getEntries()[lP.findIndexOfValue(items)]);
                    }
                    return true;
                });
            }

            ListPreference preferenceLanguage = findPreference("preference_language");

            LocaleListCompat appLocale;
            // check and apply language
            String language = sharedPreferences.getString("preference_language", "en");
            switch (language) {
                case "en":
                    appLocale = LocaleListCompat.forLanguageTags("en");
                    AppCompatDelegate.setApplicationLocales(appLocale);
                    if (preferenceLanguage != null) {
                        preferenceLanguage.setSummary(preferenceLanguage.getEntry());
                    }
                    break;
                case "el":
                    appLocale = LocaleListCompat.forLanguageTags("el");
                    AppCompatDelegate.setApplicationLocales(appLocale);
                    if (preferenceLanguage != null) {
                        preferenceLanguage.setSummary(preferenceLanguage.getEntry());
                    }
                    break;
            }

            if (preferenceLanguage != null) {
                preferenceLanguage.setOnPreferenceChangeListener((preference, newValue) -> {
                    String items = (String) newValue;
                    LocaleListCompat appLocale1;
                    if (preference.getKey().equals("preference_language")) {
                        switch (items) {
                            case "en":
                                appLocale1 = LocaleListCompat.forLanguageTags("en");
                                AppCompatDelegate.setApplicationLocales(appLocale1);
                                break;
                            case "el":
                                appLocale1 = LocaleListCompat.forLanguageTags("el");
                                AppCompatDelegate.setApplicationLocales(appLocale1);
                                break;
                        }
                        ListPreference lP = (ListPreference) preference;
                        lP.setSummary(lP.getEntries()[lP.findIndexOfValue(items)]);
                    }
                    return true;
                });
            }

            EditTextPreference ectsPreference = findPreference("preference_ects");
            assert ectsPreference != null;
            ectsPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));

            String ects = sharedPreferences.getString("preference_ects", "240");
            ectsPreference.setSummary(ects);
            ectsPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String ects1 = (String) newValue;
                preference.setSummary(ects1);
                return true;
            });

            EditTextPreference passingGradePreference = findPreference("preference_passing_grade");
            assert passingGradePreference != null;
            passingGradePreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED));

            String passingGrade = sharedPreferences.getString("preference_passing_grade", "5");
            passingGradePreference.setSummary(passingGrade);
            passingGradePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String passingGrade1 = (String) newValue;
                preference.setSummary(passingGrade1);
                return true;
            });

            EditTextPreference semestersPreference = findPreference("preference_semesters");
            assert semestersPreference != null;
            semestersPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));

            String semesters = sharedPreferences.getString("preference_semesters", "8");
            semestersPreference.setSummary(semesters);
            semestersPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String semesters1 = (String) newValue;
                preference.setSummary(semesters1);
                return true;
            });

            Preference infoPreference = findPreference("preference_info");
            assert infoPreference != null;
            infoPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    openInfo(getString(R.string.info_title), getString(R.string.info_message), getContext());
                    return false;
                }
            });

            Preference contactPreference = findPreference("preference_contact");
            assert contactPreference != null;
            contactPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    Uri uri = Uri.parse("mailto:evasilog@csd.auth.gr");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    return false;
                }
            });
        }
    }

    private static void openInfo(String title, String message, Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}