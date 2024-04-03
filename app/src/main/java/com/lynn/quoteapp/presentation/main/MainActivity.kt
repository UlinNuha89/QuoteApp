package com.lynn.quoteapp.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.lynn.quoteapp.R
import com.lynn.quoteapp.data.datasource.QuoteApiDataSource
import com.lynn.quoteapp.data.datasource.QuoteDataSource
import com.lynn.quoteapp.data.repository.QuoteRepository
import com.lynn.quoteapp.data.repository.QuoteRepositoryImpl
import com.lynn.quoteapp.data.source.network.services.QuoteApiServices
import com.lynn.quoteapp.databinding.ActivityMainBinding
import com.lynn.quoteapp.presentation.main.adapter.MainAdapter
import com.lynn.quoteapp.utils.GenericViewModelFactory
import com.lynn.quoteapp.utils.proceedWhen

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter = MainAdapter()

    private val viewModel: MainViewModel by viewModels {
        val s = QuoteApiServices.invoke()
        val ds: QuoteDataSource = QuoteApiDataSource(s)
        val rp: QuoteRepository = QuoteRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(rp))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setOnClick()
    }

    private fun setOnClick() {
        binding.btnRandom.setOnClickListener {
            randomize()
        }
    }

    private fun randomize() {
        observeData()
    }

    private fun observeData() {
        viewModel.getData().observe(this) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = true
                    binding.btnRandom.isEnabled = false
                    binding.rvListQuote.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.btnRandom.isEnabled = true
                    binding.rvListQuote.isVisible = true
                    result.payload?.let {
                        adapter.submitData(it)
                    }

                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = "quote kosong"
                    binding.layoutState.pbLoading.isVisible = false
                    binding.btnRandom.isEnabled = false
                    binding.rvListQuote.isVisible = false

                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.layoutState.pbLoading.isVisible = false
                    binding.btnRandom.isEnabled = false
                    binding.rvListQuote.isVisible = false

                }

            )
        }
    }

    private fun setupList() {
        binding.rvListQuote.adapter=this.adapter
    }
}