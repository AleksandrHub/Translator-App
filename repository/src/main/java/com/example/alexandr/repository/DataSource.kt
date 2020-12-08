package com.example.alexandr.repository


interface DataSource<T> {

    suspend fun getData(word: String): T
}