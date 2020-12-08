package com.example.alexandr.repository

import com.example.alexandr.model.entity.DataModel


interface RepositoryLocal<T> : com.example.alexandr.repository.Repository<T> {

    suspend fun saveToDB(dataModel: DataModel)
}
