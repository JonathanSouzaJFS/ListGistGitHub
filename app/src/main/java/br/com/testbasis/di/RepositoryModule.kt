package br.com.testbasis.di

import br.com.testbasis.data.repository.PersonRepository
import br.com.testbasis.data.repository.PersonRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PersonRepository> { PersonRepositoryImpl() }
}