package com.example.fusiontestapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.fusiontestapp.R
import com.example.fusiontestapp.databinding.ActivityEntriesDetailBinding
import com.example.fusiontestapp.model.EntriesModel
import com.example.fusiontestapp.viewModel.EntriesDetailViewModel
import com.example.fusiontestapp.viewModel.EntriesDetailViewModelFactory

class EntriesDetailActivity : AppCompatActivity() {
    lateinit var viewModel: EntriesDetailViewModel
    lateinit var binding: ActivityEntriesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entries_detail)

        binding = ActivityEntriesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel =
            ViewModelProvider(this, EntriesDetailViewModelFactory()).get(
                EntriesDetailViewModel::class.java
            )

        val entries = intent.getSerializableExtra(ENTRIES_KEY) as? EntriesModel
        entries?.let { entry ->
            viewModel.setEntries(entry)

            val apiName = entry.api
            apiName?.let { name ->
                supportActionBar?.title = name
            }
        }

        setDetailView()
    }

    private fun setDetailView() {
        val currentEntry = viewModel.getEntries()
        currentEntry?.let { entry ->

            val apiName = entry.api
            showOrHideView(apiName, binding.nameHolder, binding.nameText)

            val desc = entry.description
            showOrHideView(desc, binding.descHolder, binding.descText)

            val auth = entry.auth
            showOrHideView(auth, binding.authHolder, binding.authText)

            val https = entry.isHttps?.toString()
            showOrHideView(https, binding.httpsHolder, binding.httpsText)

            val cors = entry.isCors
            showOrHideView(cors, binding.corsHolder, binding.corsText)

            val link = entry.link
            showOrHideView(link, binding.linkHolder, binding.linkText)

            val category = entry.category
            showOrHideView(category, binding.categoryHolder, binding.categoryText)

        }
    }

    private fun showOrHideView(value: String?, holder: LinearLayout, textView: TextView) {
        if (value == null || value == "")
            holder.visibility = View.GONE
        else {
            holder.visibility = View.VISIBLE
            textView.text = value
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}