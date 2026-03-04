package es.rafapuig.pmdm.quotesapp

import android.app.Application
import es.rafapuig.pmdm.quotesapp.di.appModule // Asegúrate de que apunte a tu módulo de Koin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuoteApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Esto es lo que falta para que la app no explote
        startKoin {
            androidContext(this@QuoteApp)
            modules(appModule)
        }
    }
}