package es.rafapuig.pmdm.quotesapp

import android.app.Application
import es.rafapuig.pmdm.quotesapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuoteApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@QuoteApp)
            modules(appModule(this@QuoteApp))
        }
    }
}
