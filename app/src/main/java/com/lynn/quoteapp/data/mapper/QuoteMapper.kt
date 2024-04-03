package com.lynn.quoteapp.data.mapper

import com.lynn.quoteapp.data.model.Quote
import com.lynn.quoteapp.data.source.network.model.QuoteResponse


fun QuoteResponse.toQuote() = Quote(
    id = this.id.orEmpty(),
    quote = this.quote.orEmpty(),
    anime = this.anime.orEmpty(),
    character = this.character.orEmpty()
)

fun Collection<QuoteResponse>.toQuotes() = this.map {
    it.toQuote()
}