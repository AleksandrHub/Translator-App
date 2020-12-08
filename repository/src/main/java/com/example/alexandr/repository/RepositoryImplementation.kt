package com.example.alexandr.repository



class RepositoryImplementation(private val dataSource: com.example.alexandr.repository.DataSource<List<com.example.alexandr.model.SearchResult>>) :
    Repository<List<com.example.alexandr.model.SearchResult>> {

    override suspend fun getData(word: String): List<com.example.alexandr.model.SearchResult> {
        return dataSource.getData(word)
    }
}