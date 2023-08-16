package br.com.testbasis.di

import DetailsContract
import HomeContract
import br.com.testbasis.ui.details.DetailsPresenter
import br.com.testbasis.ui.home.HomePresenter
import org.koin.dsl.module

val featuresModule = module {
    factory { (view: DetailsContract.View) -> DetailsPresenter(view) as DetailsContract.Presenter }
    factory { (view: HomeContract.View) -> HomePresenter(view) as HomeContract.Presenter }
}