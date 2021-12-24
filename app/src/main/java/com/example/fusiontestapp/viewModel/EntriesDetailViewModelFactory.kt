package com.example.fusiontestapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EntriesDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EntriesDetailViewModel::class.java))
            EntriesDetailViewModel() as T
        else
            throw IllegalArgumentException("ViewModel Not Found")
    }
}