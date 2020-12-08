package com.example.alexandr.repository


import com.example.alexandr.model.entity.DataModel
import com.example.alexandr.model.SearchResult
import com.example.alexandr.repository.room.HistoryDao



class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<com.example.alexandr.model.SearchResult> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(dataModel: DataModel) {
        convertDataModelSuccessToEntity(dataModel)?.let {
            historyDao.insert(it)
        }
    }
}