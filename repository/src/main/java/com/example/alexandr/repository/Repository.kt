package com.example.alexandr.repository


interface Repository<T> {

    suspend fun getData(word: String): T
}