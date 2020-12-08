package com.example.alexandr.model

import com.google.gson.annotations.SerializedName
import geekbrains.ru.translator.model.data.Meanings

class SearchResult(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)