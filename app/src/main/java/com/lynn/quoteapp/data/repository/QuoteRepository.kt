package com.lynn.quoteapp.data.repository

import com.lynn.quoteapp.data.datasource.QuoteDataSource
import com.lynn.quoteapp.data.mapper.toQuotes
import com.lynn.quoteapp.data.model.Quote
import com.lynn.quoteapp.utils.ResultWrapper
import com.lynn.quoteapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getRandomQuotes() : Flow<ResultWrapper<List<Quote>>>
}
class QuoteRepositoryImpl(private val dataSource: QuoteDataSource) : QuoteRepository{
    override fun getRandomQuotes(): Flow<ResultWrapper<List<Quote>>> {
        return proceedFlow { dataSource.getRandomQuotes().toQuotes()  }
    }
}