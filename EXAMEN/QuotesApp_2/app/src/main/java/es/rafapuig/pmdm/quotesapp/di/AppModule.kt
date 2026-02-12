package es.rafapuig.pmdm.quotesapp.di
import es.rafapuig.pmdm.quotesapp.data.local.AppDatabase
import es.rafapuig.pmdm.quotesapp.data.local.getDatabase
import es.rafapuig.pmdm.quotesapp.data.local.toDatabase
import es.rafapuig.pmdm.quotesapp.data.repository.QuoteRepositoryImpl
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetFilteredQuotesUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetRandomQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.ToggleFavoriteUseCase
import es.rafapuig.pmdm.quotesapp.presentation.screens.QuoteDetailsViewModel
import es.rafapuig.pmdm.quotesapp.presentation.screens.QuoteListViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Base de datos
    single {
        androidContext().getDatabase()
    }

    single {
        get<AppDatabase>().quoteDao()
    }

    // Repository
    single<QuoteRepository> {
        QuoteRepositoryImpl(get())
    }

    single {
        androidContext().getDatabase().also { db ->
            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                db.quoteDao().insertQuotes(
                    es.rafapuig.pmdm.quotesapp.data.QuoteProvider.programmingQuotes.map { it.toDatabase() }
                )
            }
        }
    }


    // UseCases
    factory { ToggleFavoriteUseCase(get()) }
    factory { GetRandomQuoteUseCase(get()) }
    factory { GetFilteredQuotesUseCase(get()) }
    factory { GetQuoteUseCase(get()) }

    // ViewModels
    viewModel {
        QuoteListViewModel(
            getFilteredQuotesUseCase = get(),
            getRandomQuoteUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }

    viewModel { (quoteId: Int) ->
        QuoteDetailsViewModel(
            quoteId = quoteId,
            getQuoteUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
}
