package com.mansao.moneymanagercapstone.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.databinding.ActivityHomeBinding
import com.mansao.moneymanagercapstone.helper.ViewModelFactory
import com.mansao.moneymanagercapstone.ui.addtask.MoneyAddUpdateActivity

class HomeActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityHomeBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var adapter: MoneyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val mainViewModel = obtainViewModel(this@HomeActivity)
        mainViewModel.getAllNotes().observe(this, { list ->
            adapter.setListNotes(list)
            if (list.isNotEmpty()) {
                binding?.imgNull?.visibility = View.GONE
            } else {
                binding?.imgNull?.visibility = View.VISIBLE
            }
        })

        adapter = MoneyAdapter(this@HomeActivity)

        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@HomeActivity, MoneyAddUpdateActivity::class.java)
                startActivityForResult(intent, MoneyAddUpdateActivity.REQUEST_ADD)
            }
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
}