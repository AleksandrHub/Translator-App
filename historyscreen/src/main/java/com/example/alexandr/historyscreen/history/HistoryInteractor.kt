package com.example.alexandr.historyscreen.history

import com.example.alexandr.core.viewmodel.Interactor
import com.example.alexandr.model.entity.DataModel
import com.example.alexandr.model.SearchResult
import com.example.alexandr.repository.Repository
import com.example.alexandr.repository.RepositoryLocal


class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResult>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResult>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        return DataModel.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
