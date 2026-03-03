package es.rafapuig.pmdm.quotesapp

//import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
//import es.rafapuig.pmdm.quotesapp.data.local.getInMemoryDatabase
//import es.rafapuig.pmdm.quotesapp.data.local.toDatabase
//import es.rafapuig.pmdm.quotesapp.data.repository.QuoteRepositoryImpl
//import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
//import es.rafapuig.pmdm.quotesapp.domain.usecase.*
//import es.rafapuig.pmdm.quotesapp.presentation.screens.details.QuoteDetailsViewModel
//import es.rafapuig.pmdm.quotesapp.presentation.screens.list.QuoteListViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import org.koin.android.ext.koin.androidContext
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module
//
//val appModule = module {
//    // Definición de la Base de Datos con carga inicial (Pista del examen)
//    single {
//        androidContext().getInMemoryDatabase().also { database ->
//            CoroutineScope(Dispatchers.IO).launch {
//                val dao = database.quoteDao()
//                // Insertamos las citas del QuoteProvider transformadas a Entity
//                dao.insertQuotes(QuoteProvider.programmingQuotes.map { it.toDatabase() }) [cite: 274, 278, 279]
//            }
//        }
//    }
//
//    // Definición del DAO
//    single { get<AppDatabase>().quoteDao() } [cite: 126]
//
//    // Repositorio
//    single<QuoteRepository> { QuoteRepositoryImpl(get()) } [cite: 155]
//
//    // Casos de Uso
//    factory { GetQuoteUseCase(get()) } [cite: 86]
//    factory { GetRandomQuoteUseCase(get()) } [cite: 79]
//    factory { GetFilteredQuotesUseCase(get()) } [cite: 82]
//    factory { ToggleFavoriteUseCase(get()) } [cite: 76]
//
//    // ViewModels
//    viewModel { QuoteListViewModel(get(), get(), get()) } [cite: 165]
//    // El id de la cita se recibe como parámetro dinámico desde la navegación
//    viewModel { params -> QuoteDetailsViewModel(get(), get(), quoteId = params.get()) } [cite: 267]
}