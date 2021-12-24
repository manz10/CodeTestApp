package com.example.fusiontestapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fusiontestapp.repository.Repository
import java.lang.IllegalArgumentException

class MainActivityViewModelFactory constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java))
            MainActivityViewModel(this.repository) as T
        else
            throw IllegalArgumentException("ViewModel Not Found")
    }
}