package es.rafapuig.pmdm.quotesapp.di

import android.app.Application
import es.rafapuig.pmdm.quotesapp.data.local.getInMemoryDatabase
import es.rafapuig.pmdm.quotesapp.data.repository.QuoteRepositoryImpl
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetFilteredQuotesUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetRandomQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.ToggleFavoriteUseCase
import es.rafapuig.pmdm.quotesapp.presentation.details.QuoteDetailsViewModel
import es.rafapuig.pmdm.quotesapp.presentation.quotes.QuoteListViewModel
import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.data.local.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

fun appModule(app: Application) = module {
    single {
        val db = app.getInMemoryDatabase()
        CoroutineScope(Dispatchers.IO).launch {
            db.quoteDao().insertQuotes(QuoteProvider.programmingQuotes.map { it.toEntity() })
        }
        db
    }

    single { get<es.rafapuig.pmdm.quotesapp.data.local.AppDatabase>().quoteDao() }

    single<QuoteRepository> { QuoteRepositoryImpl(get()) }

    single { ToggleFavoriteUseCase(get()) }
    single { GetRandomQuoteUseCase(get()) }
    single { GetFilteredQuotesUseCase(get()) }
    single { GetQuoteUseCase(get()) }

    viewModel { QuoteListViewModel(get(), get(), get()) }

    factory { (quoteId: Int) -> QuoteDetailsViewModel(get(), get(), quoteId) }
}
