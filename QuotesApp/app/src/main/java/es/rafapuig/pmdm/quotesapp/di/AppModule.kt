package es.rafapuig.pmdm.quotesapp.di

import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.data.local.toEntity
import es.rafapuig.pmdm.quotesapp.data.repository.QuoteRepositoryImpl
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetQuotesUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.ToggleFavoriteUseCase
import es.rafapuig.pmdm.quotesapp.presentation.screens.details.QuoteDetailsViewModel
import es.rafapuig.pmdm.quotesapp.presentation.screens.list.QuoteListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import es.rafapuig.pmdm.quotesapp.data.local.getDatabase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetFilteredQuotesUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetRandomQuoteUseCase

val appModule = module {
    // 1. Base de Datos
    single {
        androidContext().getDatabase().also { database ->
            CoroutineScope(Dispatchers.IO).launch {
                database.quoteDao().insertQuotes(
                    QuoteProvider.programmingQuotes.map { it.toEntity() }
                )
            }
        }
    }

    // 2. DAO y Repositorio
    single { get<es.rafapuig.pmdm.quotesapp.data.local.AppDatabase>().quoteDao() }
    single<QuoteRepository> { QuoteRepositoryImpl(get()) }

    // 3. Casos de Uso
    factory { GetRandomQuoteUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
    factory { GetQuoteUseCase(get()) }
    factory { GetQuotesUseCase(get()) }

    // 4. ViewModels
    viewModel {
        QuoteListViewModel(
            getRandomQuoteUseCase = get(),
            toggleFavoriteUseCase = get(),
            // CAMBIA ESTO para que coincida con el factory de arriba
            getFilteredQuotesUseCase = get<GetFilteredQuotesUseCase>(),
            getQuoteUseCase = get()
        )
    }

    viewModel { parameters ->
        QuoteDetailsViewModel(
            getQuoteUseCase = get(),
            toggleFavoriteUseCase = get(),
            quoteId = parameters.get()
        )
    }
}