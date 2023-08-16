package br.com.testbasis.ui

import android.app.Application
import br.com.testbasis.di.appComponent
import br.com.testbasis.utils.DATABASE_NAME
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name(DATABASE_NAME)
            .deleteRealmIfMigrationNeeded()
            .allowWritesOnUiThread(true)
            .schemaVersion(1)
            .build()

        Realm.setDefaultConfiguration(config)
        configureDI()
    }

    private fun configureDI() = startKoin {
        androidContext(this@BaseApp)
        modules(appComponent)
    }
}