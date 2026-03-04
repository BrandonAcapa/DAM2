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
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetRandomQuoteUseCase

val appModule = module {
    single {
        // Usa getDatabase() en lugar de getInMemoryDatabase()
        androidContext().getDatabase().also { database ->
            CoroutineScope(Dispatchers.IO).launch {
                // Esto insertará las citas cada vez que se cree la instancia
                // (Si quieres evitar duplicados, asegúrate de que el DAO use OnConflictStrategy.REPLACE)
                database.quoteDao().insertQuotes(
                    QuoteProvider.programmingQuotes.map { it.toEntity() }
                )
            }
        }
    }

    // 2. DAO y Repositorio
    single { get<es.rafapuig.pmdm.quotesapp.data.local.AppDatabase>().quoteDao() }
    single<QuoteRepository> { QuoteRepositoryImpl(get()) }

    // 3. Casos de Uso (Asegúrate de que estas clases existan en tu proyecto)
        factory { GetRandomQuoteUseCase(get()) }
        factory { ToggleFavoriteUseCase(get()) }
        factory { GetQuoteUseCase(get()) } // El que ya tenías en singular

    // 4. ViewModels
        viewModel {
            // Koin usará la función get() para buscar automáticamente
            // las dependencias definidas arriba por su tipo de clase.
            QuoteListViewModel(
                getRandomQuoteUseCase = get(),
                toggleFavoriteUseCase = get(),
                getFilteredQuotesUseCase = get(),
                getQuoteUseCase = get()
            )
        }

    // El id se recibe como parámetro dinámico desde el NavigationRoot
    viewModel { parameters ->
        QuoteDetailsViewModel(
            getQuoteUseCase = get(),
            toggleFavoriteUseCase = get(),
            quoteId = parameters.get()
        )
    }
}