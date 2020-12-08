package com.example.alexandr.repository

import com.example.alexandr.model.entity.DataModel


class RepositoryImplementationLocal(private val dataSource: com.example.alexandr.repository.DataSourceLocal<List<com.example.alexandr.model.SearchResult>>) :
    RepositoryLocal<List<com.example.alexandr.model.SearchResult>> {

    override suspend fun getData(word: String): List<com.example.alexandr.model.SearchResult> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(dataModel: DataModel) {
        dataSource.saveToDB(dataModel)
    }
}
