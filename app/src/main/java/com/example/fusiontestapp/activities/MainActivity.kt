package com.example.fusiontestapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fusiontestapp.R
import com.example.fusiontestapp.adapters.MainRecyclerAdapter
import com.example.fusiontestapp.adapters.RecyclerItemClickListener
import com.example.fusiontestapp.repository.Repository
import com.example.fusiontestapp.retrofit.RetrofitService
import com.example.fusiontestapp.utils.checkForInternet
import com.example.fusiontestapp.viewModel.MainActivityViewModel
import com.example.fusiontestapp.viewModel.MainActivityViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

const val ENTRIES_KEY = "entries"

class MainActivity : AppCompatActivity(), RecyclerItemClickListener {

    private lateinit var retrofitService: RetrofitService
    private lateinit var repository: Repository
    private lateinit var viewModel: MainActivityViewModel

    private var adapter: MainRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitService = RetrofitService.getInstance()
        repository = Repository(retrofitService = retrofitService)
        viewModel =
            ViewModelProvider(this, MainActivityViewModelFactory(repository = repository)).get(
                MainActivityViewModel::class.java
            )

        setUpRecyclerView()
        observeLiveData()
        makeApiCall()

        swipe_refresh_layout.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    swipe_refresh_layout?.let {
                        it.isRefreshing = false
                    }
                    makeApiCall()
                }, 2000
            )
        }
    }

    private fun makeApiCall() {
        //check for internet connection
        if (checkForInternet(this)) {
            viewModel.getAllEntriesDataList()
        } else {
            showSnackBar("Please check your internet connection. Pull down to refresh")
        }
    }

    private fun setUpRecyclerView() {
        val recyclerView = recyclerView
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager

        adapter = MainRecyclerAdapter(listener = this)
        recyclerView.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.isLoading.observe(this, Observer { isLoadingNullable ->
            isLoadingNullable?.let { isLoading ->
                if (isLoading) {
                    progress.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
            }
        })

        viewModel.errorMessage.observe(this, Observer { errorMsgNullable ->
            errorMsgNullable?.let { errorMsg ->
                recyclerView.visibility = View.GONE
                showSnackBar(errorMsg)
            }

        })

        viewModel.entriesList.observe(this, Observer { entriesListNullable ->
            entriesListNullable?.let { list ->
                if (list.isNotEmpty()) {
                    viewModel.setEntriesList(list)
                    adapter?.setDataList(dataList = list)
                }
            }
        })
    }

    override fun itemClicked(position: Int) {
        val entries = viewModel.getEntriesByPos(position)
        val intent = Intent(this, EntriesDetailActivity::class.java)
        intent.putExtra(ENTRIES_KEY, entries)
        startActivity(intent)
    }

    private fun showSnackBar(errorMsg: String) {
        val view = this.findViewById<View>(android.R.id.content).rootView
        Snackbar.make(view, errorMsg, Snackbar.LENGTH_SHORT).show()
    }

}