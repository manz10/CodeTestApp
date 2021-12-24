package com.example.fusiontestapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fusiontestapp.model.EntriesAllDataModel
import com.example.fusiontestapp.model.EntriesModel
import com.example.fusiontestapp.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(private val repository: Repository) : ViewModel() {

    private var _entriesList = MutableLiveData<List<EntriesModel>>()
    val entriesList: LiveData<List<EntriesModel>>
        get() = _entriesList

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var entriesListNotEmpty: MutableList<EntriesModel> = mutableListOf()


    fun setResponseEntriesListFromAPI(data: EntriesAllDataModel) {
        val entries = data.entries
        entries?.let {
            this._entriesList.postValue(it)
        }
    }

    fun setErrorMessage(msg: String) {
        this._errorMessage.postValue(msg)
    }

    fun setIsLoading(isLoading: Boolean) {
        this._isLoading.postValue(isLoading)
    }

    fun setEntriesList(list: List<EntriesModel>){
        entriesListNotEmpty.clear()
        entriesListNotEmpty.addAll(list)
    }

    fun getEntriesByPos(pos: Int): EntriesModel{

        return if(entriesListNotEmpty.isNotEmpty())
            entriesListNotEmpty[pos]
        else
            EntriesModel()
    }


    fun getAllEntriesDataList() {

        val response = repository.getEntriesDataList()
        setIsLoading(isLoading = true)
        response.enqueue(object : Callback<EntriesAllDataModel> {
            override fun onResponse(
                call: Call<EntriesAllDataModel>,
                response: Response<EntriesAllDataModel>
            ) {
                setIsLoading(isLoading = false)
                val allData = response.body()
                allData?.let { data ->
                    setResponseEntriesListFromAPI(data = data)
                }
            }

            override fun onFailure(call: Call<EntriesAllDataModel>, t: Throwable) {
                setIsLoading(isLoading = false)
                val errorMsg = t.message
                errorMsg?.let { msg ->
                    setErrorMessage(msg = msg)
                }
            }

        })
    }
}