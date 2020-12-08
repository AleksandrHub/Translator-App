package com.example.alexandr.repository

import com.example.alexandr.model.entity.DataModel


interface DataSourceLocal<T> : com.example.alexandr.repository.DataSource<T> {

    suspend fun saveToDB(dataModel: DataModel)
}
