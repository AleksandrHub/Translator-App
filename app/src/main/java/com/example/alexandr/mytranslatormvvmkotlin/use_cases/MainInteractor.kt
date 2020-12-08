package com.example.alexandr.mytranslatormvvmkotlin.use_cases

import com.example.alexandr.model.entity.DataModel


class MainInteractor(
    private val repositoryRemote: com.example.alexandr.repository.Repository<List<com.example.alexandr.model.SearchResult>>,
    private val repositoryLocal: com.example.alexandr.repository.RepositoryLocal<List<com.example.alexandr.model.SearchResult>>
) : com.example.alexandr.core.viewmodel.Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        val dataModel: DataModel
        if (fromRemoteSource) {
            dataModel = DataModel.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(dataModel)
        } else {
            dataModel = DataModel.Success(repositoryLocal.getData(word))
        }
        return dataModel
    }
}