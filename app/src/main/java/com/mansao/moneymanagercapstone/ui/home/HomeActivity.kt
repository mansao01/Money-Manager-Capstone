package com.mansao.moneymanagercapstone.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mansao.capstonedraft.helper.ViewModelFactory
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.databinding.ActivityHomeBinding
import com.mansao.moneymanagercapstone.helper.DarkMode
import com.mansao.moneymanagercapstone.helper.SortUtils
import com.mansao.moneymanagercapstone.ui.addtask.money.MoneyAddUpdateActivity
import com.mansao.moneymanagercapstone.ui.setting.SettingActivity
import java.util.*

class HomeActivity : AppCompatActivity() {
    private var _activityMainBinding: ActivityHomeBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var adapter: MoneyAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        homeViewModel = obtainViewModel(this@HomeActivity)
        homeViewModel.getAllMoney(SortUtils.NEWEST).observe(this, moneyObserver)

        adapter = MoneyAdapter(this@HomeActivity)

        binding?.rvList?.layoutManager = LinearLayoutManager(this)
        binding?.rvList?.setHasFixedSize(true)
        binding?.rvList?.adapter = adapter

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@HomeActivity, MoneyAddUpdateActivity::class.java)
                startActivityForResult(intent, MoneyAddUpdateActivity.REQUEST_ADD)
            }
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_dark),
            getString(R.string.pref_dark_follow_system)
        )?.apply {
            val mode = DarkMode.valueOf(this.uppercase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == MoneyAddUpdateActivity.REQUEST_ADD) {
                if (resultCode == MoneyAddUpdateActivity.RESULT_ADD) {
                    showSnackbarMessage(getString(R.string.added))
                }
            } else if (requestCode == MoneyAddUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == MoneyAddUpdateActivity.RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed))
                } else if (resultCode == MoneyAddUpdateActivity.RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(HomeViewModel::class.java)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding?.root as View, message, Snackbar.LENGTH_SHORT).show()
    }

    private val moneyObserver = Observer<PagedList<Money>> { list ->
        adapter.submitList(list)
        if (list.isNotEmpty()) {
            binding?.imgNull?.visibility = View.GONE
        } else {
            binding?.imgNull?.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_newest -> sort = SortUtils.NEWEST
            R.id.action_oldest -> sort = SortUtils.OLDEST
            R.id.action_random -> sort = SortUtils.RANDOM
            R.id.action_setting -> {
                val intentToSetting = Intent(this, SettingActivity::class.java)
                startActivity(intentToSetting)
            }
        }
        homeViewModel.getAllMoney(sort).observe(this, moneyObserver)
        item.setChecked(true)
        return super.onOptionsItemSelected(item)
    }
}