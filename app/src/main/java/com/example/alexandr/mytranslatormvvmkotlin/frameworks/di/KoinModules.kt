package com.example.alexandr.mytranslatormvvmkotlin.frameworks.di

import androidx.room.Room
import com.example.alexandr.historyscreen.history.HistoryInteractor
import com.example.alexandr.historyscreen.history.HistoryViewModel
import com.example.alexandr.mytranslatormvvmkotlin.frameworks.view.MainViewModel
import com.example.alexandr.mytranslatormvvmkotlin.use_cases.MainInteractor
import com.example.alexandr.repository.room.HistoryDataBase
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<com.example.alexandr.repository.room.HistoryDataBase>().historyDao() }
    single<com.example.alexandr.repository.Repository<List<com.example.alexandr.model.SearchResult>>> {
        com.example.alexandr.repository.RepositoryImplementation(
            com.example.alexandr.repository.RetrofitImplementation()
        )
    }
    single<com.example.alexandr.repository.RepositoryLocal<List<com.example.alexandr.model.SearchResult>>> {
        com.example.alexandr.repository.RepositoryImplementationLocal(
            com.example.alexandr.repository.RoomDataBaseImplementation(get())
        )
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}

