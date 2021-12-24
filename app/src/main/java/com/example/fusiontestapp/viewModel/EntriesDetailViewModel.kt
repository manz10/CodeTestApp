package com.example.fusiontestapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.fusiontestapp.model.EntriesModel

class EntriesDetailViewModel : ViewModel() {

    private var entries: EntriesModel? = null

    fun setEntries(entry: EntriesModel) {
        this.entries = entry
    }

    fun getEntries(): EntriesModel? {
        return this.entries
    }
}