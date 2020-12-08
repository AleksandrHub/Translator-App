package com.example.alexandr.repository

import com.example.alexandr.model.entity.DataModel
import com.example.alexandr.model.SearchResult
import com.example.alexandr.repository.room.HistoryEntity
import geekbrains.ru.translator.model.data.Meanings


fun parseOnlineSearchResults(data: DataModel): DataModel {
    return DataModel.Success(
        mapResult(
            data,
            true
        )
    )
}

fun parseLocalSearchResults(data: DataModel): DataModel {
    return DataModel.Success(
        mapResult(
            data,
            false
        )
    )
}

private fun mapResult(
    data: DataModel,
    isOnline: Boolean
): List<com.example.alexandr.model.SearchResult> {
    val newSearchResults = arrayListOf<com.example.alexandr.model.SearchResult>()
    when (data) {
        is DataModel.Success -> {
            getSuccessResultData(
                data,
                isOnline,
                newSearchResults
            )
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: DataModel.Success,
    isOnline: Boolean,
    newSearchResults: ArrayList<com.example.alexandr.model.SearchResult>
) {
    val searchResults: List<com.example.alexandr.model.SearchResult> = data.data as List<com.example.alexandr.model.SearchResult>
    if (searchResults.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchResults) {
                parseOnlineResult(
                    searchResult,
                    newSearchResults
                )
            }
        } else {
            for (searchResult in searchResults) {
                newSearchResults.add(
                    com.example.alexandr.model.SearchResult(
                        searchResult.text,
                        arrayListOf()
                    )
                )
            }
        }
    }
}

private fun parseOnlineResult(searchResult: com.example.alexandr.model.SearchResult, newSearchResults: ArrayList<com.example.alexandr.model.SearchResult>) {
    if (!searchResult.text.isNullOrBlank() && !searchResult.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in searchResult.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(
                com.example.alexandr.model.SearchResult(
                    searchResult.text,
                    newMeanings
                )
            )
        }
    }
}

fun mapHistoryEntityToSearchResult(list: List<com.example.alexandr.repository.room.HistoryEntity>): List<com.example.alexandr.model.SearchResult> {
    val searchResult = ArrayList<com.example.alexandr.model.SearchResult>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(
                com.example.alexandr.model.SearchResult(
                    entity.word,
                    null
                )
            )
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(dataModel: DataModel): com.example.alexandr.repository.room.HistoryEntity? {
    return when (dataModel) {
        is DataModel.Success -> {
            val searchResult = dataModel.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                com.example.alexandr.repository.room.HistoryEntity(
                    searchResult[0].text!!,
                    null
                )
            }
        }
        else -> null
    }
}


fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComma
}