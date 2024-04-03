package com.lynn.quoteapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lynn.quoteapp.data.model.Quote
import com.lynn.quoteapp.data.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRepository) : ViewModel() {
    //todo : tugas ambil data quotes
    fun getData() = repository.getRandomQuotes().asLiveData(Dispatchers.IO)
    //fun getList() : List<Quote>


}