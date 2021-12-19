package com.mansao.moneymanagercapstone.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.mansao.moneymanagercapstone.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.title = resources.getString(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preference, rootKey)

            val preference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            preference?.setOnPreferenceChangeListener { _, newValue ->
                when (newValue) {
                    resources.getStringArray(R.array.dark_mode_value)[0] -> {
                        updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    resources.getStringArray(R.array.dark_mode_value)[1] -> {
                        updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    else -> {
                        updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}